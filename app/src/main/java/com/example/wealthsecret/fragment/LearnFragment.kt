package com.example.wealthsecret.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wealthsecret.R
import com.example.wealthsecret.activity.fdcalc
import com.example.wealthsecret.activity.goldactivity
import com.example.wealthsecret.activity.mfcalc
import com.example.wealthsecret.activity.strat
import com.example.wealthsecret.activity.wealthpdf
import com.example.wealthsecret.databinding.FragmentLearnBinding
import com.example.wealthsecret.pfcalculator

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LearnFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LearnFragment : Fragment() {
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
private lateinit var binding:FragmentLearnBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentLearnBinding.inflate(inflater,container,false)
        binding.pfcalc.setOnClickListener { startActivity(Intent(requireActivity(),pfcalculator::class.java)) }
        binding.mfcalc.setOnClickListener { startActivity(Intent(requireActivity(),mfcalc::class.java)) }
        binding.fdcalc.setOnClickListener { startActivity(Intent(requireActivity(),fdcalc::class.java)) } // Inflate the layout for this fragment
        binding.gcalc.setOnClickListener { startActivity(Intent(requireActivity(),goldactivity::class.java)) } // Inflate the layout for this fragment
        binding.strat.setOnClickListener { startActivity(Intent(requireActivity(),strat::class.java)) } // Inflate the layout for this fragment
       binding.wealth.setOnClickListener{
           startActivity(Intent(requireActivity(),wealthpdf::class.java))
       }
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LearnFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LearnFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}