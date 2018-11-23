package com.example.alunos.batalhanavalandroid

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import android.widget.ToggleButton
import kotlinx.android.synthetic.main.activity_navios_humano.*

class NaviosHumanoActivity : TabuleiroActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navios_humano)

        g = this.application as Global
        tabuleiro = g!!.humano!!.tabuleiro

        setImagensNavios()

        btn_comecar.setOnClickListener {
            val intent = Intent(this, JogadaHumanoActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun posicionarNavio(view: View){
        val tamanho = navioSelecionado()

        if(tamanho == -1){
            runOnUiThread {
                val t = Toast.makeText(this,
                        "Selecione um navio.", Toast.LENGTH_SHORT)
                t.show()
            }
        }
        else{
            val vertical = !findViewById<ToggleButton>(R.id.btn_alinhamento).isChecked
            val navio = tabuleiro!!.navios[tamanho]!!

            val posicoesAntes = navio.posicoes
            val verticalAntes = navio.vertical

            var nome = resources.getResourceEntryName(view.id)
            var xi = nome[4].toInt() - 48
            var yi = nome[6].toInt() - 48

            navio.vertical = vertical
            navio.gerarPosicoes(xi, yi, tabuleiro!!.linhas-1, tabuleiro!!.colunas-1)

            if(tabuleiro!!.temOverlapNoTabuleiro(tamanho)){
                navio.posicoes = posicoesAntes
                navio.vertical = verticalAntes
                tabuleiro!!.navios[tamanho] = navio

                runOnUiThread {
                    val t = Toast.makeText(this,
                            "Os navios não podem se cruzar.", Toast.LENGTH_SHORT)
                    t.show()
                }
            }
            else{
                tabuleiro!!.navios[tamanho] = navio
                tabuleiro!!.gerarTabuleiroAux()
                setImagensAgua()
                setImagensNavios()
            }
        }
    }

    fun navioSelecionado(): Int{
        val cruzador = findViewById<RadioButton>(R.id.radioBtn_cruzador).isChecked
        val encouracado = findViewById<RadioButton>(R.id.radioBtn_encouracado).isChecked
        val portaAvioes = findViewById<RadioButton>(R.id.radioBtn_portaAvioes).isChecked

        var tamanho: Int
        if(cruzador){
            tamanho = 2
        }
        else if(encouracado){
            tamanho = 3
        }
        else if(portaAvioes){
            tamanho = 4
        }
        else{
            tamanho = -1
        }

        return tamanho
    }
}
