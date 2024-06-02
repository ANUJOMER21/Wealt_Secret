package com.example.wealthsecret.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.wealthsecret.R
import com.example.wealthsecret.Viewmodel.UserDataState
import com.example.wealthsecret.Viewmodel.UserViewModel
import com.example.wealthsecret.databinding.ActivitySignup2Binding
import com.example.wealthsecret.misc.Commonsharedpref
import com.example.wealthsecret.model.User
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.collect

class SignupActivity2 : AppCompatActivity() {
    private lateinit var binding:ActivitySignup2Binding
    private lateinit var viewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignup2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        val uid=intent.getStringExtra("uid")!!
        if (!FirebaseApp.getApps(this).isNotEmpty()) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        }
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.submit.setOnClickListener{
            val name=binding.nameet.text.toString()
            val email=binding.emailet.text.toString()
            val number=binding.numberet.text.toString()

            if(name.isNullOrEmpty()|| email.isNullOrEmpty()|| number.isNullOrEmpty()){
                Toast.makeText(this,"Please Fill All Field",Toast.LENGTH_SHORT).show()
            }
            else{
                val user=User(
                    name = name,
                    email=email,
                    mobile = number,
                    uniquekey = uid
                )

                viewModel.addUser(uid, user)
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.userDataStateFlow.collect{
                state->
                when(state){
                    is UserDataState.Success->{
                        Commonsharedpref(this@SignupActivity2).token=uid
                        startActivity(Intent(this@SignupActivity2,MainPage::class.java))
                        finish()
                    }
                    UserDataState.Failure->{
                        Log.d("Failed signup","failed")
                    }
                }
            }
        }
    }
}