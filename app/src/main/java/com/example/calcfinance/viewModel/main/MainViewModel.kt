package com.example.calcfinance.viewModel.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.calcfinance.model.CurrencyData
import com.example.calcfinance.model.CurrencyResponse
import com.example.calcfinance.repositories.MainRepository
import com.example.calcfinance.rest.FinanceService
import com.example.calcfinance.rest.RetrofitService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//Este é a requesição feita com corritinas
@Suppress("UNCHECKED_CAST")
class MainViewModel constructor(private val repository: MainRepository) : ViewModel() {

    // LiveData para armazenar a resposta e a mensagem de erro
    val coinList = MutableLiveData<Map<String, CurrencyData>>()
    val errorMessage = MutableLiveData<String>()

    val valorConvertido = MutableLiveData<String>()

    val listaDestino = MutableLiveData<List<String>>()

    var ultimaPosicaoDestino: Int = 0

    // Função para obter as moedas de forma assíncrona usando corrotinas
    fun getCoins(moedaOrigem : String, moedaDestino : String) {
        // Lançamos a corrotina no escopo da ViewModel
        viewModelScope.launch {
            try {
                // Chamada suspensa para obter as moedas
                val response = repository.getCoins(moedaOrigem, moedaDestino)

                val moedasMap = mapOf(
                    "USDBRL" to response.USDBRL,
                    "EURBRL" to response.EURBRL,
                    "BRLUSD" to response.BRLUSD,
                    "BRLEUR" to response.BRLEUR,
                    "EURUSD" to response.EURUSD,
                    "USDEUR" to response.USDEUR
                )

                // Atualizar o LiveData
                coinList.postValue(moedasMap)

            } catch (e: Exception) {
                // Em caso de erro, atualizamos a LiveData com a mensagem de erro
                errorMessage.postValue(e.message)
            }
        }
    }

    fun calcularConversao(valor: String, taxa : Double?){

        if (valor.isNotEmpty() && taxa != null){

            val valorDouble = valor.replace(",",".").toDouble()
            valorConvertido.value = "%.2f".format(valorDouble * taxa)

        }else {
            valorConvertido.value = ""
        }

    }

    fun atualizarMoedasDestino(moedaOrigem: String?) {
        val moedas = listOf("BRL", "USD", "EUR")
        listaDestino.value = moedas.filter { it != moedaOrigem }

        // Garante que a posição anterior seja válida
        if (ultimaPosicaoDestino >= moedas.size) {
            ultimaPosicaoDestino = 0 // Define como 0 se a posição anterior for inválida
        }
    }

    fun atualizarConversao(moedaOrigem: String?, moedaDestino: String?) {
        if (!moedaOrigem.isNullOrEmpty() && !moedaDestino.isNullOrEmpty()) {
            getCoins(moedaOrigem, moedaDestino)
        }
    }



    //A factory foi feita dentro desse companion e é chamada de um jeito mais pratico na Activity
    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            val repo = MainRepository(RetrofitService.getInstance(), FinanceService.getInstance())

            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel(repo) as T
            }
        }
    }
}

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