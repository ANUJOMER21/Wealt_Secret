package com.example.wealthsecret.model

data class User(
    val name: String = "",
    val email: String = "",
    val mobile: String = "",
    val uniquekey:String=""

){
    override fun toString(): String {
        return "$name\n$email\n$mobile"
    }
}
