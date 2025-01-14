package com.example.calcfinance.rest

import com.example.calcfinance.model.CurrencyResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {

    @GET("last/{moedaOrigem}-{moedaDestino}")
    suspend fun getCoins(
        @Path("moedaOrigem") moedaOrigem : String,
        @Path("moedaDestino") moedaDestino : String,
    ): CurrencyResponse
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