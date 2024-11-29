package com.example.calcfinance.model

data class ApiResponse(
    val by: String,
    val valid_key: Boolean,
    val results: List<Result>,
    val execution_time: Int,
    val from_cache: Boolean
)

data class Result(
    val date: String,
    val cdi: Double,
    val selic: Double,
    val daily_factor: Double,
    val selic_daily: Double,
    val cdi_daily: Double
)
