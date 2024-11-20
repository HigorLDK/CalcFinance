package com.example.calcfinance

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calcfinance.databinding.ActivityInvestimentoBinding

class InvestimentoActivity : AppCompatActivity() {

    private lateinit var binding : ActivityInvestimentoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInvestimentoBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        spinnerExibicao()
    }

    private fun spinnerExibicao() {

        val taxaPeriodo = listOf(
            "Anual", "Mensal"
        )
        val periodo = listOf(
            "Ano(s)", "Mes(es)"
        )
        binding.spTaxa.adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            taxaPeriodo
        )

        binding.spPeriodo.adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            periodo
        )

    }
}