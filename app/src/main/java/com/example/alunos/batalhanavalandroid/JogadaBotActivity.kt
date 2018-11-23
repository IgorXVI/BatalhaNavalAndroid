package com.example.alunos.batalhanavalandroid

import android.os.Bundle
import java.util.*
import kotlin.concurrent.schedule

class JogadaBotActivity : JogadaActivity() {

    var bot: Bot? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jogada_bot)

        g = this.application as Global
        tabuleiro = g!!.humano!!.tabuleiro
        bot = g!!.bot!!

        travarMenu()
        setImagensNavios()
        setNumErroAcerto()
        setErrosAcertosTabuleiro()
        Timer().schedule(500){
            ataqueBot()
        }
    }

    fun ataqueBot() {
        bot!!.realizarJogada(tabuleiro!!)

        val ganhou = ataque(bot!!.x, bot!!.y)

        if(ganhou){
            mensagemFim("Você Perdeu!")
        }
        else{
            mudarVez("Humano")
        }
    }
}
