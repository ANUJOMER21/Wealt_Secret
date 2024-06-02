package com.example.wealthsecret.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.wealthsecret.R
import com.example.wealthsecret.Viewmodel.UserViewModel

import com.example.wealthsecret.databinding.ActivityMainBinding
import com.example.wealthsecret.misc.Commonsharedpref
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 9001
    private lateinit var userViewModel:UserViewModel
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth=FirebaseAuth.getInstance()
        userViewModel=ViewModelProvider(this).get(UserViewModel::class.java)

        if(!Commonsharedpref(this).token.isNullOrEmpty()){
            startActivity(Intent(this,MainPage::class.java))
            finish()
        }
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

binding.sigingoogle.setOnClickListener {  val signInIntent = googleSignInClient.signInIntent
    startActivityForResult(signInIntent, RC_SIGN_IN)
}
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign-In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign-In failed, update UI accordingly
                Log.w("Login", "Google sign in failed", e)
            }
        }
    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    lifecycleScope.launch {
                        val user = FirebaseAuth.getInstance().currentUser
                        val userexit=userViewModel.checkUserExists(user!!.uid)
                        if(userexit){
                           mainpageIntent()
                            Commonsharedpref(this@MainActivity).token=user!!.uid
                        }
                        else{
                          signuppageIntent(user!!.uid)

                        }

                    }




                }
                else {
                    // If sign in fails, display a message to the user.
                    Log.w("Login", "signInWithCredential:failure", task.exception)
                    // Update UI
                }
            }
    }
    private suspend fun mainpageIntent() {
        withContext(Dispatchers.Main) {
            val intent = Intent(this@MainActivity, MainPage::class.java)
            startActivity(intent)
            finish()
        }
    } private suspend fun signuppageIntent(uid:String) {
        withContext(Dispatchers.Main) {
            val intent = Intent(this@MainActivity, SignupActivity2::class.java)
            intent.putExtra("uid",uid)
            startActivity(intent)
            finish()
        }
    }
}