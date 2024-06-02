package com.example.wealthsecret.API.model

data class topstockItem(
    val `52_week_high`: Double,//
    val `52_week_low`: Double,//
    val beta: Double,//
    val current_price: Double,
    val dividend_yield: Double,
    val eps: Any?,//
    val forward_PE: Double,//
    val high: Double,
    val industry: String,
    val logo_url: Any,
    val long_business_summary: String,
    val low: Double,
    val market_cap: Long,//
    val name: String,
    val `open`: Double,
    val pe_ratio: Any?,//
    val previous_close: Double,
    val sector: String,//
    val short_name: String,
    val ticker: String,
    val volume: Int,//
    val website: String//
)