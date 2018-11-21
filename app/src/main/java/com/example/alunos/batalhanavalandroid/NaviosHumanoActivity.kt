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

        setImagensNavios(g.humano.tabuleiro)

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
            val navio = g.humano.tabuleiro.getNavio(tamanho)

            val posInicialAntes = IntArray(2)
            val posFinalAntes = IntArray(2)
            val verticalAntes = navio.vertical
            posInicialAntes[0] = navio.posInicial[0]
            posInicialAntes[1] = navio.posInicial[1]
            posFinalAntes[0] = navio.posFinal[0]
            posFinalAntes[1] = navio.posFinal[1]

            var nome = resources.getResourceEntryName(view.id)
            var xi = nome[4].toInt() - 48
            var yi = nome[6].toInt() - 48
            var xf: Int
            var yf: Int

            if (!vertical) {
                yf = yi
                xf = xi + tamanho - 1
                if (xf >= 7) {
                    xf = xi - tamanho + 1
                }
            } else {
                xf = xi
                yf = yi + tamanho - 1
                if (yf >= 7) {
                    yf = yi - tamanho + 1
                }
            }
            val posInicio = IntArray(2)
            val posFim = IntArray(2)

            posInicio[0] = xi
            posInicio[1] = yi

            posFim[0] = xf
            posFim[1] = yf

            navio.posInicial = posInicio
            navio.posFinal = posFim
            navio.vertical = vertical

            var overlap = false
            for(i in 2..4){
                if(i != tamanho){
                    if(g.humano.tabuleiro.overlap(tamanho, i)){
                        overlap = true
                        break
                    }
                }
            }
            if(overlap){
                navio.posInicial = posInicialAntes
                navio.posFinal = posFinalAntes
                navio.vertical = verticalAntes
                g.humano.tabuleiro.setNavio(navio)

                runOnUiThread {
                    val t = Toast.makeText(this,
                            "Os navios não podem se cruzar.", Toast.LENGTH_SHORT)
                    t.show()
                }
            }
            else{
                navio.gerarTodasPosicoes()
                g.humano.tabuleiro.setNavio(navio)
                g.humano.tabuleiro.gerarTabuleiroAux()
                setImagensAgua()
                setImagensNavios(g.humano.tabuleiro)
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
