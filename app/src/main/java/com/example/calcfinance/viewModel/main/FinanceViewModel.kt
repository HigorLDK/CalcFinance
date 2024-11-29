package com.example.calcfinance.viewModel.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.calcfinance.model.Result
import com.example.calcfinance.repositories.MainRepository
import com.example.calcfinance.rest.FinanceService
import com.example.calcfinance.rest.RetrofitService
import kotlinx.coroutines.launch

class FinanceViewModel constructor(private val repository: MainRepository) : ViewModel() {

    val selicRate = MutableLiveData<List<Result>>()
    val errorMessage = MutableLiveData<String>()

    fun fetchSelicRate(apiKey: String) {
        viewModelScope.launch {
            try {
                // Aqui o repository vai retornar a resposta com a lista de "results"
                val data = repository.getSelicRate(apiKey)
                val results = data.results  // Pega a lista completa de resultados
                if (results.isNotEmpty()) {
                    selicRate.postValue(results)  // Atualiza o LiveData com a lista completa
                } else {
                    errorMessage.postValue("Nenhum dado encontrado.")
                }
            } catch (e: Exception) {
                errorMessage.postValue(e.message)
            }
        }
    }

    //A factory foi feita dentro desse companion e é chamada de um jeito mais pratico na Activity
    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            val repo = MainRepository(RetrofitService.getInstance(), FinanceService.getInstance())

            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return FinanceViewModel(repo) as T
            }
        }
    }
}