package com.example.wealthsecret.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wealthsecret.R

class LearnActivity : AppCompatActivity() {
    private lateinit var binding:LearnActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn)
    }
}