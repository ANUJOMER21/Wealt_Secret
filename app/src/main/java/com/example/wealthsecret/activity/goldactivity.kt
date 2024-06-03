package com.example.wealthsecret.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.wealthsecret.R
import com.google.android.material.slider.Slider

class goldactivity : AppCompatActivity() {
    private lateinit var etInvestmentAmount: EditText

    private lateinit var sliderDuration: Slider

    private lateinit var tvResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goldactivity)
        etInvestmentAmount = findViewById(R.id.etInvestmentAmount)
        sliderDuration = findViewById(R.id.sliderDuration)
        tvResult = findViewById(R.id.textViewResult)

        etInvestmentAmount.addTextChangedListener(investmentAmountWatcher)
        sliderDuration.addOnChangeListener { _, _, _ ->
            calculateReturnAmount()
        }
    }




    private val investmentAmountWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            calculateReturnAmount()
        }
    }

    private fun calculateReturnAmount() {
        val investmentAmount = etInvestmentAmount.text.toString().toDoubleOrNull() ?: 0.0
        val goldPrice = sliderDuration.value.toDouble() // Assuming slider value represents gold price
        val duration = sliderDuration.value.toDouble()
     //   Toast.makeText(this@goldactivity,duration.toString(),Toast.LENGTH_SHORT).show()
        val intrest=15
        val returnAmount = investmentAmount * intrest*duration/100
        tvResult.text = "Return Amount: $returnAmount"
    }
}

