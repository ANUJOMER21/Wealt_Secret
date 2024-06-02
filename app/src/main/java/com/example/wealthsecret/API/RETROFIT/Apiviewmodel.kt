package com.example.wealthsecret.API.RETROFIT

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wealthsecret.API.model.indexmodel
import com.example.wealthsecret.API.model.mfmodel
import com.example.wealthsecret.API.model.stockdata
import com.example.wealthsecret.API.model.topstock
import kotlinx.coroutines.launch
import retrofit2.Response

class Apiviewmodel :ViewModel(){
    private val _index = MutableLiveData<indexmodel>()
    val index: LiveData<indexmodel> get() = _index

    private val _topstock=MutableLiveData<topstock>()
    val topstock:LiveData<topstock> get()=_topstock
    private val _stock=MutableLiveData<stockdata>()
    val stockdata:LiveData<stockdata> get()=_stock
    private  val _mfstock=MutableLiveData<mfmodel>()
    val mfstock:LiveData<mfmodel> get()=_mfstock

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error
    fun fetchstock(ticker:String,type:String){
        viewModelScope.launch {
            try {
                val Response=RetrofitInstance.api.getStockData(ticker,type)
                if(Response.isSuccessful){
                    _stock.value=Response.body()
                }
                else{
                    _error.value=" c: ${Response.code()}"
                }
            }
            catch (e:Exception){
                _error.value="E: ${e.message}"
            }
        }
    }
    fun  fetchTopstock(){
        viewModelScope.launch {
            try {
                val Response=RetrofitInstance.api.gettopstock()
                if(Response.isSuccessful){
                    _topstock.value=Response.body()
                }
                else{
                    _error.value=" c: ${Response.code()}"
                }
            }
            catch (e:Exception){
                _error.value="E: ${e.message}"
            }
        }
    }
    fun  fetchindex(){
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getindex()
                if (response.isSuccessful) {
                    _index.value = response.body()
                } else {
                    _error.value = " c: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Exception: ${e.message}"
            }
        }
    }
    fun mfstock(){
        viewModelScope.launch {
            try {
                val response=RetrofitInstance.api.getmf()
                if(response.isSuccessful){
                    _mfstock.value=response.body()
                }
                else{
                    _error.value=" c: ${response.code()}"
                }
            }
            catch (e:Exception){
                _error.value="Exception: ${e.message}"
            }
        }
    }
    }
