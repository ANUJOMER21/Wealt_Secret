package com.example.wealthsecret

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import com.google.android.material.slider.Slider
import com.google.android.material.textfield.TextInputEditText

class pfcalculator : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pfcalculator)
        val etBasicSalary = findViewById<TextInputEditText>(R.id.etBasicSalary)
        val etEmployerContribution = findViewById<TextInputEditText>(R.id.etEmployerContribution)
        val etEmployeeContribution = findViewById<TextInputEditText>(R.id.etEmployeeContribution)
        val sliderInterestRate = findViewById<Slider>(R.id.sliderInterestRate)
        val tvResult = findViewById<TextView>(R.id.tvResult)
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                calculateEpf(etBasicSalary, etEmployerContribution, etEmployeeContribution, sliderInterestRate, tvResult)
            }
            override fun afterTextChanged(s: Editable?) {}
        }

        etBasicSalary.addTextChangedListener(textWatcher)
        etEmployerContribution.addTextChangedListener(textWatcher)
        etEmployeeContribution.addTextChangedListener(textWatcher)

        sliderInterestRate.addOnChangeListener { _, _, _ ->
            calculateEpf(etBasicSalary, etEmployerContribution, etEmployeeContribution, sliderInterestRate, tvResult)
        }
    }
    private fun calculateEpf(
        etBasicSalary: TextInputEditText,
        etEmployerContribution: TextInputEditText,
        etEmployeeContribution: TextInputEditText,
        sliderInterestRate: Slider,
        tvResult: TextView
    ) {
        val basicSalary = etBasicSalary.text.toString().toDoubleOrNull()
        val employerContributionPercent = etEmployerContribution.text.toString().toDoubleOrNull()
        val employeeContributionPercent = etEmployeeContribution.text.toString().toDoubleOrNull()
        val interestRate = sliderInterestRate.value.toDouble()

        if (basicSalary != null && employerContributionPercent != null && employeeContributionPercent != null) {
            val employerContribution = (basicSalary * employerContributionPercent) / 100
            val employeeContribution = (basicSalary * employeeContributionPercent) / 100
            val totalContribution = employerContribution + employeeContribution
            val annualInterest = totalContribution * interestRate / 100
            val maturityAmount = totalContribution + annualInterest


            tvResult.text = "Employer Contribution: Rs. $employerContribution\n" +
                    "Employee Contribution: Rs. $employeeContribution\n" +
                    "Total EPF Contribution: Rs. $totalContribution\n" +
                    "Annual Interest: Rs. ${String.format("%.2f", annualInterest)}\n" +
                    "Maturity Amount: Rs. ${String.format("%.2f", maturityAmount)}"
        } else {
            tvResult.text = "Please enter valid values."
        }
    }
}