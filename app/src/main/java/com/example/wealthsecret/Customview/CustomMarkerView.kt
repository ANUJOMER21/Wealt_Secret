package com.example.wealthsecret.Customview

import android.content.Context
import android.widget.TextView
import com.example.wealthsecret.R
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CustomMarkerView(context: Context, layoutResource: Int) : MarkerView(context, layoutResource) {

    private val tvContent: TextView = findViewById(R.id.tvContent)

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        if (e == null) return
        val date = Date(e.x.toLong())
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val formattedDate = sdf.format(date)
    // Format your date according to your requirements
        val price = e.y // Assuming y represents the price

        tvContent.text = "Date: $formattedDate\nPrice: $price"
        super.refreshContent(e, highlight)
    }

    private fun formatDate(value: Float): String {
        val date = Date(value.toLong())
        val format = SimpleDateFormat("MM/dd", Locale.getDefault())
        return format.format(date)
    }

    override fun getOffset(): MPPointF {
        return MPPointF(-(width / 2).toFloat(), -height.toFloat())
    }
}
