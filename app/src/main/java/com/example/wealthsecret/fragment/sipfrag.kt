package com.example.wealthsecret.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.slider.Slider
import com.google.android.material.textfield.TextInputEditText
import kotlin.math.pow

import com.example.wealthsecret.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [sipfrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class sipfrag : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_sipfrag, container, false)
        val etMonthlyInvestment = view.findViewById<TextInputEditText>(R.id.etMonthlyInvestment)
        val etAnnualRate = view.findViewById<TextInputEditText>(R.id.etAnnualRate)
        val sliderDuration = view.findViewById<Slider>(R.id.sliderDuration)
        val tvResult = view.findViewById<TextView>(R.id.tvResult)
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                calculateSIP(etMonthlyInvestment, etAnnualRate, sliderDuration, tvResult)
            }
            override fun afterTextChanged(s: Editable?) {}
        }

        etMonthlyInvestment.addTextChangedListener(textWatcher)
        etAnnualRate.addTextChangedListener(textWatcher)

        sliderDuration.addOnChangeListener { _, _, _ ->
            calculateSIP(etMonthlyInvestment, etAnnualRate, sliderDuration, tvResult)
        }


        return view;
    }
    private fun calculateSIP(
        etMonthlyInvestment: TextInputEditText,
        etAnnualRate: TextInputEditText,
        sliderDuration: Slider,
        tvResult: TextView
    ) {
        val monthlyInvestment = etMonthlyInvestment.text.toString().toDoubleOrNull()
        val annualRate = etAnnualRate.text.toString().toDoubleOrNull()
        val durationYears = sliderDuration.value.toInt()

        if (monthlyInvestment != null && annualRate != null) {
            val ratePerPeriod = (annualRate / 100) / 12
            val numberOfMonths = durationYears * 12
            val futureValue = monthlyInvestment * (((1 + ratePerPeriod).pow(numberOfMonths) - 1) / ratePerPeriod) * (1 + ratePerPeriod)

            tvResult.text = "Monthly Investment: Rs. $monthlyInvestment\n" +
                    "Annual Interest Rate: $annualRate%\n" +
                    "Investment Duration: $durationYears years\n" +
                    "Future Value: Rs. ${String.format("%.2f", futureValue)}"
        } else {
            tvResult.text = "Please enter valid values."
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment sipfrag.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            sipfrag().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}