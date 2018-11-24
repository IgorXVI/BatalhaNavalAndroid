package com.example.alunos.batalhanavalandroid

import android.content.Intent
import android.os.Bundle
import android.view.Menu
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

        setImagensNavios()
        setNumErroAcerto()
        setErrosAcertosTabuleiro()

        Timer().schedule(500){
            ataqueBot()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return false
    }

    fun ataqueBot() {
        bot!!.realizarJogada(tabuleiro!!)

        val intent = Intent(this, JogadaHumanoActivity::class.java)

        Timer().schedule(somTorpedo(true)){
            setNumErroAcerto()
            setErrosAcertosTabuleiro(bot!!.tiros!!)

            Timer().schedule(somTorpedo(bot!!.acertou)){
                val ganhou = tabuleiro!!.todosNaviosDestruidos()
                if(!ganhou){
                    mudarVez(intent)
                }
                else{
                    fim("Você Perdeu!")
                }
            }

        }
    }
}
