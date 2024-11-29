package com.example.calcfinance.repositories

import com.example.calcfinance.model.Result
import com.example.calcfinance.rest.FinanceService
import com.example.calcfinance.rest.RetrofitService

class MainRepository constructor(private val retrofitService: RetrofitService, private val financeService: FinanceService) {

   suspend fun getCoins(moedaOrigem : String, moedaDestino : String) = retrofitService.getCoins(moedaOrigem, moedaDestino)

   suspend fun getSelicRate(apiKey : String) = financeService.getSelicRate(apiKey)

}