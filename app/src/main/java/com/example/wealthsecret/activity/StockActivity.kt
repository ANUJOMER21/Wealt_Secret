package com.example.wealthsecret.activity

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.wealthsecret.API.RETROFIT.Apiviewmodel
import com.example.wealthsecret.API.model.topstockItem
import com.example.wealthsecret.R
import com.example.wealthsecret.databinding.ActivityStockBinding
import com.example.wealthsecret.fragment.ChartType
import com.example.wealthsecret.fragment.CustomChartView


import org.json.JSONArray
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.Locale
data class StockData(
    @SerializedName("Date") val date: String,
    @SerializedName("Close") val close: Double
)
private lateinit var binding: ActivityStockBinding
class StockActivity : AppCompatActivity() {

    private lateinit var apiviewmodel: Apiviewmodel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityStockBinding.inflate(layoutInflater)

        setContentView(binding.root)
        val data=intent.getStringExtra("data")
        val stockItem=Gson().fromJson(data,topstockItem::class.java)
        val ticker=intent.getStringExtra("ticker")
        apiviewmodel=ViewModelProvider(this).get(Apiviewmodel::class.java)
        apiviewmodel.fetchstock(ticker!!,"1mo")
        binding.month1.isSelected=true
        apiviewmodel.stockdata.observe(this) { data ->
            val requiredata = ArrayList<StockData>()
            for (i in data) {
                requiredata.add(StockData(i.Date, i.Close))
            }
            val jsonrequire = Gson().toJson(requiredata)
            Log.d("jsonrequire", jsonrequire)
           binding.lineChart.setData(jsonrequire, ChartType.YEAR)
        }
        binding.month1.setOnClickListener { apiviewmodel.fetchstock(ticker,"1mo") }
        binding.month6.setOnClickListener { apiviewmodel.fetchstock(ticker,"6mo") }
        binding.year.setOnClickListener { apiviewmodel.fetchstock(ticker,"1y") }

        binding.stockName.text = stockItem.name
        binding.highValue.text = "Rs. ${stockItem.`52_week_high`}"
        binding.lowValue.text = "Rs. ${stockItem.`52_week_low`}"
        binding.betaValue.text = stockItem.beta.toString()
        binding.epsValue.text =if(stockItem.eps==null) "0" else stockItem.eps.toString()
        binding.forwardPeValue.text = stockItem.forward_PE.toString()
        binding.peRatioValue.text = if(stockItem.pe_ratio==null) "0" else stockItem.pe_ratio.toString()
        binding.marketCapValue.text = "Rs. "+stockItem.market_cap.toString()
        binding.volumeValue.text = stockItem.volume.toString()
        binding.sectorValue.text = stockItem.sector
        binding.websiteValue.text = stockItem.website

        // Optionally, set up an onClickListener for the website to open it in a browser
        binding.websiteValue.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(stockItem.website))
            startActivity(browserIntent)
        }
       binding.tradingview.setOnClickListener {
           val url="https://finance.yahoo.com/quote/$ticker/"
           val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
           startActivity(browserIntent)
       }




    }
}