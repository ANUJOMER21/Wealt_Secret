package com.example.wealthsecret.API.model

data class Smallcap(
    val `data`: List<Data>,
    val meta: Meta,
    val returns: Returns,
    val status: String
)