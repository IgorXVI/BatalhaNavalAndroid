package com.example.alunos.batalhanavalandroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.Toast
import android.widget.ToggleButton

class NaviosHumanoActivity : AppCompatActivity() {

    val g = Global.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navios_humano)

        zerarPos()
    }

    fun pegarPos(x: Int, y:Int): ImageButton {
        var id = resources.getIdentifier("pos_"+x.toString()+"_"+y.toString(),
                "id", this.packageName)
        var btn: ImageButton = findViewById(id)
        return btn
    }

    fun zerarPos(){
        var navio: Navio

        for(i in 2..4){
            navio = g.humano.tabuleiro.getNavio(i)
            navio.posInicial = navio.posFinal
            g.humano.tabuleiro.setNavio(navio)
        }
    }

    fun setImagensTabuleiro(){
        var c: Char
        var tamanho: Int
        var navio: Navio

        for(i in 0..6){
            for(j in 0..6){
                c = g.humano.tabuleiro.tabuleiroDoJogador[i][j]
                tamanho = (c - 'a') + 2
                navio = g.humano.tabuleiro.getNavio(tamanho)

                if(navio.posInicial != navio.posFinal){
                    setImagemNavio(i, j, tamanho)
                }
            }
        }
    }

    fun setImagemNavio(x: Int, y: Int, tamanho: Int){
        val pos = pegarPos(x, y)
        if(tamanho == 2){
            pos.setImageResource(R.mipmap.cruzador)
        }
        if(tamanho == 3){
            pos.setImageResource(R.mipmap.encouracado)
        }
        if(tamanho == 4){
            pos.setImageResource(R.mipmap.porta_avioes)
        }
    }

    fun posicionarNavio(view: View){
        val tamanho = navioSelecionado()

        if(tamanho == -1){
            runOnUiThread {
                val t = Toast.makeText(this, "Selecione um navio.", Toast.LENGTH_SHORT)
                t.show()
            }
        }
        else{
            var nome = resources.getResourceEntryName(view.id)
            var xi = nome[4].toInt() - 48
            var yi = nome[6].toInt() - 48
            var xf: Int
            var yf: Int

            val vertical = findViewById<ToggleButton>(R.id.btn_alinhamento).isChecked
            val navio = g.humano.tabuleiro.getNavio(tamanho)

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

            navio.posInicial[0] = xi
            navio.posInicial[1] = yi

            navio.posFinal[0] = xf
            navio.posFinal[1] = yf

            g.humano.tabuleiro.setNavio(navio)
            g.humano.tabuleiro.gerarTabuleiroAux()

            setImagensTabuleiro()
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
