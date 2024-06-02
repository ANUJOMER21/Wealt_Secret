package com.example.wealthsecret.API.model

data class Large(
    val `data`: List<Data>,
    val meta: Meta,
    val returns: Returns,
    val status: String
)