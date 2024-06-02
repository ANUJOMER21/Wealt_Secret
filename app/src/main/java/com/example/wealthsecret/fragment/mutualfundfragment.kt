package com.example.wealthsecret.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wealthsecret.API.RETROFIT.Apiviewmodel
import com.example.wealthsecret.Adapter.mfadapter
import com.example.wealthsecret.R
import com.example.wealthsecret.databinding.FragmentMutualfundfragmentBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [mutualfundfragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class mutualfundfragment : Fragment() {
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
private lateinit var binding:FragmentMutualfundfragmentBinding
    lateinit var apiviewmodel: Apiviewmodel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
     binding=FragmentMutualfundfragmentBinding.inflate(inflater,container,false)
        apiviewmodel= ViewModelProvider(this).get(Apiviewmodel::class.java)
        apiviewmodel.mfstock()
        apiviewmodel.mfstock.observe(requireActivity()){
            binding.lrv.layoutManager=LinearLayoutManager(requireActivity())
            binding.mrv.layoutManager=LinearLayoutManager(requireActivity())
            binding.srv.layoutManager=LinearLayoutManager(requireActivity())
            val lrvadapter=mfadapter(it.large,requireActivity())
            binding.lrv.adapter=lrvadapter
            lrvadapter.notifyDataSetChanged()

            val mrvadapter=mfadapter(it.midcap,requireActivity())
            binding.mrv.adapter=mrvadapter
            mrvadapter.notifyDataSetChanged()

            val srvadpater=mfadapter(it.smallcap,requireActivity())
            binding.srv.adapter=srvadpater
            srvadpater.notifyDataSetChanged()
        }
apiviewmodel.error.observe(requireActivity()){
    Log.d("MF ERROR",it)
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
         * @return A new instance of fragment mutualfundfragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            mutualfundfragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}