package com.example.calcfinance.viewModel.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.calcfinance.model.CurrencyResponse
import com.example.calcfinance.repositories.MainRepository
import com.example.calcfinance.rest.RetrofitService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


//Este é a mesma rquisição só que feita com callback
//@Suppress("UNCHECKED_CAST")
//class MainViewModel constructor(private val repository : MainRepository) : ViewModel() {
//
//    val coinList = MutableLiveData<CurrencyResponse>()
//    val errorMessage = MutableLiveData<String>()
//
//    fun getCoins() {
//
//        val request = repository.getCoins()
//        request.enqueue(object : Callback<CurrencyResponse> {
//            override fun onResponse(p0: Call<CurrencyResponse>, p1: Response<CurrencyResponse>) {
//                coinList.postValue(p1.body())
//            }
//
//            override fun onFailure(p0: Call<CurrencyResponse>, p1: Throwable) {
//                errorMessage.postValue(p1.message)
//            }
//        })
//    }
//
//    companion object{
//
//        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
//            val repo = MainRepository(RetrofitService.getInstance())
//            override fun <T : ViewModel> create(modelClass: Class<T>): T {
//                return MainViewModel(repo) as T
//
//            }
//        }
//
//    }
//
//}


//Este é a requesição feita com corritinas
@Suppress("UNCHECKED_CAST")
class MainViewModel constructor(private val repository: MainRepository) : ViewModel() {

    // LiveData para armazenar a resposta e a mensagem de erro
    val coinList = MutableLiveData<CurrencyResponse>()
    val errorMessage = MutableLiveData<String>()

    // Função para obter as moedas de forma assíncrona usando corrotinas
    fun getCoins() {
        // Lançamos a corrotina no escopo da ViewModel
        viewModelScope.launch {
            try {
                // Chamada suspensa para obter as moedas
                val response = repository.getCoins()

                // Se a resposta for bem-sucedida, atualizamos o LiveData
                coinList.postValue(response)

            } catch (e: Exception) {
                // Em caso de erro, atualizamos a LiveData com a mensagem de erro
                errorMessage.postValue(e.message)
            }
        }
    }

    //A factory foi feita dentro desse companion e é chamada de um jeito mais pratico na Activity
    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            val repo = MainRepository(RetrofitService.getInstance())

            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel(repo) as T
            }
        }
    }
}