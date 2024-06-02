package com.example.wealthsecret.misc

import android.content.Context
import android.content.SharedPreferences

class Commonsharedpref(context: Context) {
    companion object {
        private const val PREF_NAME = "MyPrefs"
        private const val KEY_TOKEN = "token"
    }

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    var token: String?
        get() = sharedPreferences.getString(KEY_TOKEN, null)
        set(value) {
            sharedPreferences.edit().putString(KEY_TOKEN, value).apply()
        }

    fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}