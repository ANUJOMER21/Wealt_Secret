package com.example.wealthsecret.API.RETROFIT

import com.example.wealthsecret.API.model.indexmodel
import com.example.wealthsecret.API.model.mfmodel
import com.example.wealthsecret.API.model.stockdata
import com.example.wealthsecret.API.model.topstock
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("indices")
    suspend fun getindex(): Response<indexmodel>
    @GET("stock")
    suspend fun gettopstock():Response<topstock>
    @GET("/stock/{symbol}/{type}")
    suspend fun getStockData(
        @Path("symbol") symbol: String,
        @Path("type") type: String
    ): Response<stockdata>
    @GET("mf")
    suspend fun getmf():Response<mfmodel>



}
