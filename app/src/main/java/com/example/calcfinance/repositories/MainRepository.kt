package com.example.calcfinance.repositories

import com.example.calcfinance.rest.RetrofitService

class MainRepository constructor(private val retrofitService: RetrofitService) {

   suspend fun getCoins() = retrofitService.getCoins()

}