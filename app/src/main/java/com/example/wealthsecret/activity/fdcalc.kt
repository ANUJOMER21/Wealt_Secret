package com.example.wealthsecret.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import com.example.wealthsecret.R
import com.google.android.material.slider.Slider
import com.google.android.material.textfield.TextInputEditText
import kotlin.math.pow

class fdcalc : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fdcalc)
        val etPrincipalAmount = findViewById<TextInputEditText>(R.id.etPrincipalAmount)
        val etAnnualRate = findViewById<TextInputEditText>(R.id.etAnnualRate)
        val sliderDuration = findViewById<Slider>(R.id.sliderDuration)
        val tvResult = findViewById<TextView>(R.id.tvResult)
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                calculateFDValue(etPrincipalAmount, etAnnualRate, sliderDuration, tvResult)
            }
            override fun afterTextChanged(s: Editable?) {}
        }

        etPrincipalAmount.addTextChangedListener(textWatcher)
        etAnnualRate.addTextChangedListener(textWatcher)

        sliderDuration.addOnChangeListener { _, _, _ ->
            calculateFDValue(etPrincipalAmount, etAnnualRate, sliderDuration, tvResult)
        }
    }
    private fun calculateFDValue(
        etPrincipalAmount: TextInputEditText,
        etAnnualRate: TextInputEditText,
        sliderDuration: Slider,
        tvResult: TextView
    ) {
        val principalAmount = etPrincipalAmount.text.toString().toDoubleOrNull()
        val annualRate = etAnnualRate.text.toString().toDoubleOrNull()
        val durationYears = sliderDuration.value.toInt()

        if (principalAmount != null && annualRate != null) {
            val ratePerPeriod = annualRate / 100
            val maturityValue = principalAmount * (1 + ratePerPeriod).pow(durationYears)

            tvResult.text = "Principal Amount: Rs. $principalAmount\n" +
                    "Annual Interest Rate: $annualRate%\n" +
                    "Investment Duration: $durationYears years\n" +
                    "Maturity Value: Rs. ${String.format("%.2f", maturityValue)}"
        } else {
            tvResult.text = "Please enter valid values."
        }
    }
}