package com.example.alunos.batalhanavalandroid

import android.os.Bundle
import java.util.*
import kotlin.concurrent.schedule

class JogadaBotActivity : JogadaActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jogada_bot)

        g = this.application as Global

        travarMenu()
        travarTudo()
        setImagensNavios(g?.humano!!.tabuleiro)
        setErroAcerto(g?.humano!!.tabuleiro)
        Timer().schedule(500){
            ataqueBot()
        }
    }

    fun ataqueBot() {
        val arr = g?.bot!!.realizarJogada()
        val ganhou = ataque(arr[0], arr[1], g?.humano!!.tabuleiro)

        if(ganhou){
            mensagemFim("Você Perdeu!")
        }
        else{
            mudarVez("Humano")
        }
    }
}
