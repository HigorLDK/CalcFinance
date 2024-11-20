package com.example.calcfinance

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.calcfinance.databinding.ActivityConversaoBinding
import com.example.calcfinance.repositories.MainRepository
import com.example.calcfinance.rest.RetrofitService
import com.example.calcfinance.viewModel.main.MainViewModel
import com.example.calcfinance.viewModel.main.MainViewModelFactory

class ConversaoActivity : AppCompatActivity() {

    private lateinit var binding : ActivityConversaoBinding

    //lateinit var viewModel : MainViewModel
    private val vm : MainViewModel by viewModels { MainViewModel.Factory }

    private val retrofitService = RetrofitService.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConversaoBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        viewModel = ViewModelProvider(this, MainViewModelFactory(MainRepository(retrofitService))).get(
//            MainViewModel::class.java
//        )
        Log.i("higor", "onCreate")

    }

    override fun onStart() {
        super.onStart()

        vm.coinList.observe(this, Observer { moedas ->
        Log.i("higor", "taxa: ${moedas.USDBRL.bid}")
            Log.i("higor", "onStart")
        })

        vm.errorMessage.observe(this, Observer { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            Log.i("higor", message)
        })

    }

    override fun onResume() {
        super.onResume()
        Log.i("higor", "onResume")
        vm.getCoins()

    }
}