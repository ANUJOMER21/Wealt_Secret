package com.example.wealthsecret.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.wealthsecret.API.RETROFIT.Apiviewmodel
import com.example.wealthsecret.R
import com.example.wealthsecret.Viewmodel.UserViewModel
import com.example.wealthsecret.databinding.ActivityMainPageBinding
import com.example.wealthsecret.fragment.mutualfundfragment
import com.example.wealthsecret.misc.Commonsharedpref
import com.example.wealthsecret.fragment.stockfragment

class MainPage : AppCompatActivity() {
    private lateinit var binding:ActivityMainPageBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var apiViewmodel:Apiviewmodel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userViewModel= ViewModelProvider(this).get(UserViewModel::class.java)
        apiViewmodel=ViewModelProvider(this).get(Apiviewmodel::class.java)
        userViewModel.getUser(Commonsharedpref(this).token!!)
        replacefrag(stockfragment())
         binding.bmv.setOnItemSelectedListener {  menuitem->
             when(menuitem.itemId){
                 R.id.menu_stock-> replacefrag(stockfragment())
                 R.id.menu_mutual_funds->replacefrag(mutualfundfragment())

             }
             true
         }






    }
    fun replacefrag(fragment:Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.framelayout, fragment)
            .commit()
    }
}