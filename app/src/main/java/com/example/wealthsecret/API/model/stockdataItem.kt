package com.example.wealthsecret.API.model

import com.google.gson.annotations.SerializedName

data class stockdataItem(
    val Close: Double,
    val Date: String,
    val Dividends: Double,
    val High: Double,
    val Low: Double,
    val Open: Double,
    @SerializedName("Stock Splits")
    val StockSplits: Double,
    val Volume: Int
)