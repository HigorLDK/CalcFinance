package com.example.calcfinance.rest

import com.example.calcfinance.model.ApiResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface FinanceService {

    @GET("finance/taxes")
    suspend fun getSelicRate(@Query("key") apiKey: String) : ApiResponse

    companion object {

        private val financeService : FinanceService by lazy {

            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.hgbrasil.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            retrofit.create(FinanceService::class.java)
        }

        fun getInstance() : FinanceService {
            return financeService
        }

    }

}