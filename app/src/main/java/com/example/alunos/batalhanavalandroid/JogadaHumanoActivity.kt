package com.example.alunos.batalhanavalandroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class JogadaHumanoActivity : JogadaActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jogada_humano)

        g = this.application as Global

        setErroAcerto(g!!.bot.tabuleiro)
    }

    fun ataqueHumano(view: View){
        travarTudo()
        travarMenu()

        val nome = resources.getResourceEntryName(view.id)
        val x = nome[4].toInt() - 48
        val y = nome[6].toInt() - 48
        g!!.humano.realizarJogada(x, y, g!!.bot)
        val ganhou = ataque(x, y, g!!.bot.tabuleiro)

        if(ganhou){
            mensagemFim("Você Ganhou!")
        }
        else{
            mudarVez("Bot")
        }
    }

}
