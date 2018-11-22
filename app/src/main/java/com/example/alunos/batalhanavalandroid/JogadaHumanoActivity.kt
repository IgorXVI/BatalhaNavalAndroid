package com.example.alunos.batalhanavalandroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Switch
import kotlinx.android.synthetic.main.activity_jogada_humano.*

class JogadaHumanoActivity : JogadaActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jogada_humano)

        g = this.application as Global

        runOnUiThread {
            findViewById<Switch>(R.id.switch_bomba).isChecked = g?.humano!!.temBomba
        }

        setErroAcerto(g?.bot!!.tabuleiro)
    }

    fun ataqueHumano(view: View){
        travarTudo()
        travarMenu()

        val nome = resources.getResourceEntryName(view.id)
        val x = nome[4].toInt() - 48
        val y = nome[6].toInt() - 48

        val bomba = findViewById<Switch>(R.id.switch_bomba).isChecked

        g?.humano!!.realizarJogada(x, y, g?.bot!!, bomba)
        val ganhou = ataque(x, y, g?.bot!!.tabuleiro)

        if(ganhou){
            mensagemFim("Você Ganhou!")
        }
        else{
            mudarVez("Bot")
        }
    }

}
