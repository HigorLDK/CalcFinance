package com.example.calcfinance.model

data class CurrencyResponse(
    val USDBRL: CurrencyData


)

data class CurrencyData(
    val ask: String,
    val bid: String,
    val code: String,
    val codein: String,
    val create_date: String,
    val high: String,
    val low: String,
    val name: String,
    val pctChange: String,
    val timestamp: String,
    val varBid: String
)