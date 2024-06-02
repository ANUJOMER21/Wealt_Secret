package com.example.wealthsecret.fragment

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.ToggleButton
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.wealthsecret.Customview.CustomMarkerView
import com.example.wealthsecret.R
import com.example.wealthsecret.databinding.CustomChartViewBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.*

enum class ChartType {
    WEEK, MONTH, MONTH6, YEAR
}

private lateinit var binding: CustomChartViewBinding

class CustomChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        val inflater = LayoutInflater.from(context)
        binding = CustomChartViewBinding.inflate(inflater, this)
        setupChart()
    }

    private fun setupChart() {
        binding.lineChart.setBackgroundColor(Color.WHITE)
        binding.lineChart.description.isEnabled = false
        binding.lineChart.setDrawGridBackground(false)
        binding.lineChart.setDrawBorders(false)
        binding.lineChart.animateX(1500)
        binding.lineChart.animateY(1500)
        binding.lineChart.axisRight.isEnabled = false

        // XAxis customization
        val xAxis = binding.lineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.textColor = Color.TRANSPARENT
        xAxis.isEnabled = true
        xAxis.textSize = 10f
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false) // Don't show axis line

        // Custom formatter for X-axis dates
        xAxis.valueFormatter = object : ValueFormatter() {
            private val format = SimpleDateFormat("MM/dd", Locale.getDefault())

            override fun getFormattedValue(value: Float): String {
                return format.format(Date(value.toLong()))
            }
        }

        // Left YAxis customization
        val leftAxis = binding.lineChart.axisLeft
        leftAxis.textColor = Color.TRANSPARENT
        leftAxis.textSize = 10f
        leftAxis.setDrawGridLines(false)
        leftAxis.gridColor = Color.LTGRAY
        leftAxis.setDrawAxisLine(false) // Don't show axis line

        binding.lineChart.axisRight.isEnabled = false

        // Custom marker view
        val mv = CustomMarkerView(context, R.layout.custom_marker_view)
        binding.lineChart.marker = mv

        // Disable legend if not needed
        val legend = binding.lineChart.legend
        legend.isEnabled = false
    }

    fun setData(jsonData: String, type: ChartType) {
        try {
            val jsonArray = JSONArray(jsonData)
            val entries = ArrayList<Entry>()
            val labels = ArrayList<String>()
            Log.d("JSONDATA", jsonData)
            val sdf = SimpleDateFormat("E, dd MMM yyyy HH:mm:ss 'GMT'", Locale.ENGLISH)
            val sdfLabel = SimpleDateFormat("MM/dd", Locale.US)
            val calendar = Calendar.getInstance()

            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val dateStr = jsonObject.getString("Date")
                val date = sdf.parse(dateStr)
                val close = jsonObject.getDouble("Close").toFloat()

                calendar.time = date
                val dateLabel = sdfLabel.format(date)
                entries.add(Entry(date.time.toFloat(), close))
                labels.add(dateLabel)
            }
            Log.d("JSONDATA", labels.size.toString())

            // Log each entry
            for (entry in entries) {
                Log.d("ENTRYDATA", "x: ${entry.x}, y: ${entry.y}")
            }

            // Filter entries based on the type
            val filteredEntries = filterEntries(entries, type)
            Log.d("FILTEREDDATA", filteredEntries.size.toString())
            val lineDataSet = LineDataSet(filteredEntries, "Close Prices")

            // Style customizations for LineDataSet
            lineDataSet.color = Color.GREEN
            lineDataSet.valueTextColor = Color.BLACK
            lineDataSet.valueTextSize = 10f
            lineDataSet.lineWidth = 2f
            lineDataSet.setDrawCircles(false)
            lineDataSet.setDrawValues(false)
            lineDataSet.setDrawFilled(false) // Don't use gradient fill
            lineDataSet.fillFormatter = IFillFormatter { _, _ -> binding.lineChart.axisLeft.axisMinimum }
            lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER

            val lineData = LineData(lineDataSet)
            binding.lineChart.data = lineData

            // XAxis value formatter
            val xAxis = binding.lineChart.xAxis
            xAxis.valueFormatter = IndexAxisValueFormatter(labels)
            xAxis.labelCount = labels.size
            xAxis.isGranularityEnabled = true

            // Refresh the chart
            binding.lineChart.invalidate()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun filterEntries(entries: List<Entry>, type: ChartType): List<Entry> {
        val calendar = Calendar.getInstance()
        Log.d("charttype",type.name)

        val currentTime = calendar.timeInMillis
        val filteredEntries = when (type) {
            ChartType.WEEK -> {
                calendar.add(Calendar.DAY_OF_YEAR, -7)
                val weekAgo = calendar.timeInMillis

                entries.filter { it.x >= weekAgo }
            }
            ChartType.MONTH -> {
                calendar.add(Calendar.MONTH, -1)
                val monthAgo = calendar.timeInMillis
                entries.filter { it.x >= monthAgo }
            }
            ChartType.MONTH6 -> {
                calendar.add(Calendar.MONTH, -6)
                val sixMonthsAgo = calendar.timeInMillis
                entries.filter { it.x >= sixMonthsAgo }
            }
            ChartType.YEAR -> {
                calendar.add(Calendar.YEAR, -1)
                val yearAgo = calendar.timeInMillis
                entries.filter { it.x >= yearAgo }
            }
            else -> entries
        }
        return filteredEntries
    }
}
