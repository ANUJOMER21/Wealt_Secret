package com.example.wealthsecret.API.model

data class Nifty(
    val current_price: Double,
    val high: Double,
    val low: Double,
    val `open`: Double,
    val volume: Double
)