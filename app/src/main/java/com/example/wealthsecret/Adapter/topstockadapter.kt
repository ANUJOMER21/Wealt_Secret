package com.example.wealthsecret.Adapter


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wealthsecret.API.model.topstockItem
import com.example.wealthsecret.R
import com.example.wealthsecret.activity.StockActivity
import com.google.gson.Gson

import java.lang.String
import kotlin.math.round


class StockAdapter(stockList: ArrayList<topstockItem>, context: Context) :
    RecyclerView.Adapter<StockAdapter.StockViewHolder>() {
    private val stockList: ArrayList<topstockItem>
    private val context: Context

    init {
        this.stockList = stockList
        this.context = context
    }



    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        val stock: topstockItem = stockList[position]
        holder.textViewCat.text="Industry: "+stock.industry
        holder.textViewName.text=stock.name
        val cp= round(stock.current_price)
        holder.textViewPrice.text ="₹ ${cp}"
        holder.itemView.setOnClickListener {
            val intent=Intent(context,StockActivity::class.java)
            intent.putExtra("ticker",stock.ticker)
            val data=Gson().toJson(stock)
            intent.putExtra("data",data)

            context.startActivity(intent) }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val view =LayoutInflater.from(context).inflate(R.layout.stockbox,parent,false)
        return StockViewHolder(view)
    }

    override fun getItemCount(): Int {
        return stockList.size
    }

    class StockViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        var textViewName: TextView
        var textViewPrice: TextView
        var textViewCat:TextView

        init {
     textViewName=itemView.findViewById(R.id.stockname)
     textViewPrice=itemView.findViewById(R.id.price)
     textViewCat=itemView.findViewById(R.id.industry)

        }

    }
}


