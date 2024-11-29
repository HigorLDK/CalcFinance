package com.example.calcfinance

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.example.calcfinance.databinding.ActivityMainBinding
import com.example.calcfinance.viewModel.main.FinanceViewModel
import com.example.calcfinance.viewModel.main.MainViewModel
import com.squareup.picasso.BuildConfig
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private val vm : FinanceViewModel by viewModels { FinanceViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        eventosClique()

    }


    override fun onStart() {
        super.onStart()

        val apiKey = "8b3ce626"

        Picasso.get()
            .load(R.drawable.logo01)
            .resize(1000, 1000)  // Redimensiona a imagem
            .centerInside()      // Ajusta a imagem dentro do ImageView
            .into(binding.imageViewLogo)


        vm.selicRate.observe(this, Observer { rates ->
            rates?.forEach { rate ->
                // Aqui, você pode acessar os dados de cada "rate"
                Log.i("SELIC", "Data: ${rate.date}, Selic: ${rate.selic}")
                binding.textSelic.text = "${rate.selic}%"
                binding.textCdi.text = "${rate.cdi}%"
            }
        })

        vm.errorMessage.observe(this, Observer { error ->
            binding.textSelic.text = error
            binding.textCdi.text= error
            // Exiba erros, se houver
            Log.i("SELIC", error)
        })

        vm.fetchSelicRate(apiKey)

    }

    private fun eventosClique() {

        binding.btnInvestimentos.setOnClickListener {

            startActivity(Intent(this, InvestimentoActivity::class.java))

        }

        binding.button2.setOnClickListener {

            startActivity(Intent(this, ConversaoActivity::class.java))

        }

    }

    override fun onResume() {
        super.onResume()



    }
}