package com.example.alunos.batalhanavalandroid

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import java.util.*
import kotlin.concurrent.schedule

class JogadaBotActivity : JogadaActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jogada_bot)

        g = this.application as Global
        tabuleiro = g!!.humano!!.tabuleiro
        jogador = g!!.bot
        proximaVez = Intent(this, JogadaHumanoActivity::class.java)
        mensagemFim = "Você Perdeu!"

        setImagensNavios()
        setNumErroAcerto()
        setErrosAcertosTabuleiro()
        ataqueBot()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return false
    }

    fun ataqueBot() {
        Timer().schedule(500){
            g?.bot?.realizarJogada(tabuleiro!!)
            ataque()
        }
    }

    override fun fimAtaque() {
        val ganhou = tabuleiro!!.todosNaviosDestruidos()
        if(ganhou){
            mensagemFim()
        }
        else if(!jogador!!.acertou){
            mudarVez()
        }
        else{
            ataqueBot()
        }
    }

}
