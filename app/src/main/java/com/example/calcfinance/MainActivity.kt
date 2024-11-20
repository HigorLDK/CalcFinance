package com.example.calcfinance

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calcfinance.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

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

        Picasso.get()
            .load(R.drawable.logo01)
            .resize(1000, 1000)  // Redimensiona a imagem
            .centerInside()      // Ajusta a imagem dentro do ImageView
            .into(binding.imageViewLogo)

    }

    private fun eventosClique() {

        binding.btnInvestimentos.setOnClickListener {

            startActivity(Intent(this, InvestimentoActivity::class.java))

        }

        binding.button2.setOnClickListener {

            startActivity(Intent(this, ConversaoActivity::class.java))

        }

    }
}