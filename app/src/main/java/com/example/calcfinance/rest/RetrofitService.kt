package com.example.calcfinance.rest

import com.example.calcfinance.model.CurrencyResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetrofitService {

    @GET("last/USD-BRL")
    suspend fun getCoins(): CurrencyResponse
//    Estes é um exemplo feito com Call mais não é mais recomendado usar
//    fun getCoins(): Call<CurrencyResponse>

    companion object{

        private val retrofitService : RetrofitService by lazy {

            val retrofit = Retrofit.Builder()
                .baseUrl("https://economia.awesomeapi.com.br/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            retrofit.create(RetrofitService::class.java)

        }

        fun getInstance() : RetrofitService{
            return retrofitService
        }

    }

}