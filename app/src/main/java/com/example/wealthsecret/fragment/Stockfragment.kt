package com.example.wealthsecret.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wealthsecret.API.RETROFIT.Apiviewmodel
import com.example.wealthsecret.Adapter.StockAdapter

import com.example.wealthsecret.databinding.FragmentStockfragmentBinding
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass.
 * Use the [stockfragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class stockfragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val ARG_PARAM1 = "param1"
    private val ARG_PARAM2 = "param2"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    private fun showSnackbar(view: View, message: String) {
        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        snackbar.setAction("Dismiss") {
            snackbar.dismiss()
        }
        snackbar.show()
    }
   lateinit var apiviewmodel: Apiviewmodel
   lateinit var binding: FragmentStockfragmentBinding
   lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStockfragmentBinding.inflate(inflater, container, false)
        recyclerView=binding.rvstock;
        recyclerView.layoutManager=LinearLayoutManager(requireActivity())
        apiviewmodel= ViewModelProvider(this).get(Apiviewmodel::class.java)
        apiviewmodel.fetchindex()
        apiviewmodel.fetchTopstock()
        apiviewmodel.error.observe(requireActivity(), {
            data->
            Log.d("ERROR", data)
            showSnackbar(binding.root,data)
        })
        apiviewmodel.index.observe(requireActivity(), { data->
            binding.sensextext.text=data.Sensex.current_price.toString()
            binding.niftytext.text=data.Nifty.current_price.toString()
        })
        apiviewmodel.topstock.observe(requireActivity(), {
             data->
             val stockAdapter=StockAdapter(data,requireActivity())
             recyclerView.adapter=stockAdapter
             stockAdapter.notifyDataSetChanged()
         })
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment stockfragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            stockfragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}