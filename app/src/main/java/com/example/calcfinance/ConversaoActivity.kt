package com.example.calcfinance

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.calcfinance.databinding.ActivityConversaoBinding
import com.example.calcfinance.rest.RetrofitService
import com.example.calcfinance.viewModel.main.MainViewModel
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener

class ConversaoActivity : AppCompatActivity() {

    private lateinit var binding : ActivityConversaoBinding

    //lateinit var viewModel : MainViewModel
    private val vm : MainViewModel by viewModels { MainViewModel.Factory }

    private val retrofitService = RetrofitService.getInstance()

    var moedaOrigem: String? = null
    var moedaDestino: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConversaoBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        viewModel = ViewModelProvider(this, MainViewModelFactory(MainRepository(retrofitService))).get(
//            MainViewModel::class.java
//        )

        exibirSpinner()

        val textOrigem = binding.textOrigem

        binding.btn09.setOnClickListener {
            textOrigem.append("9")
        }
        binding.btn08.setOnClickListener {
            textOrigem.append("8")
        }
        binding.btn07.setOnClickListener {
            textOrigem.append("7")
        }
        binding.btn06.setOnClickListener {
            textOrigem.append("6")
        }
        binding.btn05.setOnClickListener {
            textOrigem.append("5")
        }
        binding.btn04.setOnClickListener {
            textOrigem.append("4")
        }
        binding.btn03.setOnClickListener {
            textOrigem.append("3")
        }
        binding.btn02.setOnClickListener {
            textOrigem.append("2")
        }
        binding.btn01.setOnClickListener {
            textOrigem.append("1")
        }
        binding.btn00.setOnClickListener {
            textOrigem.append("0")
        }
        binding.btnVirgula.setOnClickListener {
            textOrigem.append(",")
        }

        //Limpa o textView
        binding.btnLimpar.setOnClickListener {
            textOrigem.text = ""
        }

        //Apaga apenas 1 caracter do Display textOrigem
        binding.btnApagar.setOnClickListener {

            val display = textOrigem.text.toString()

            if (display.isNotBlank()){
                textOrigem.text = display.substring(0,display.length-1)
            }
        }
    }

    private fun exibirSpinner() {

        val moedas = listOf("BRL", "USD", "EUR")

        binding.spMoedaOrigem.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            moedas
        )
        binding.spMoedaOrigem.setSelection(0)

       /* binding.spMoedaDestino.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            moedas
            )
        binding.spMoedaDestino.setSelection(0)*/

    }

    override fun onStart() {
        super.onStart()
        vm.coinList.observe(this, Observer { moedas ->

            // Gerar a chave da conversão
            val chaveConversao = "$moedaOrigem$moedaDestino"

            // Obter o response de moedas
            val response = moedas?.get(chaveConversao)
            val taxa = response?.bid?.toDouble()
            Log.i("higor", "$taxa")



            binding.textOrigem.addTextChangedListener(object : TextWatcher {

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    val valor = binding.textOrigem.text.toString()
                    vm.calcularConversao(valor, taxa)
                }

                override fun afterTextChanged(p0: Editable?) {

                }
            })
        })

        vm.valorConvertido.observe(this, Observer { valor ->
            binding.textMoedaDestino.text = valor
        })


        vm.errorMessage.observe(this, Observer { message ->
            //Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            Log.i("app_teste", message)
        })
    }

    override fun onResume() {
        super.onResume()

        vm.listaDestino.observe(this, Observer { lista ->
            binding.spMoedaDestino.adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                lista
            )

            // Restaura a última posição selecionada
            binding.spMoedaDestino.setSelection(vm.ultimaPosicaoDestino)
        })


        binding.spMoedaOrigem.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                moedaOrigem = p0?.selectedItem.toString()
                vm.atualizarMoedasDestino(moedaOrigem)
                vm.atualizarConversao(moedaOrigem,moedaDestino)
                binding.textMoedaDestino.text = ""
                binding.textOrigem.text = ""

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
        binding.spMoedaDestino.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                moedaDestino = p0?.getItemAtPosition(p2).toString()
                vm.ultimaPosicaoDestino = p2 // Salva a nova posição
                vm.atualizarConversao(moedaOrigem,moedaDestino)
                binding.textMoedaDestino.text = ""
                binding.textOrigem.text = ""

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

    }
}