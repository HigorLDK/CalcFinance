package com.example.calcfinance.viewModel.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.calcfinance.repositories.MainRepository

@Suppress("UNCHECKED_CAST")
//Ester é um modo de fazer o Factory outro exemplo e masi pratico e fazer o o Factory dentro de um companion dentro da propia ViewModel
class MainViewModelFactory constructor(private val repository: MainRepository) : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return  if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            MainViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}