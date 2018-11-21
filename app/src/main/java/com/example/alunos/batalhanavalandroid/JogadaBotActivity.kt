package com.example.alunos.batalhanavalandroid

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import java.util.*
import kotlin.concurrent.schedule

class JogadaBotActivity : JogadaActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jogada_bot)

        travarTudo()
        setImagensNavios(g.humano.tabuleiro)
        setErroAcerto(g.humano.tabuleiro)
        Timer().schedule(1000){
            ataque()
        }
    }

    fun ataque() {
        val arr = g.bot.realizarJogada()
        val ganhou = super.ataque(arr[0], arr[1], g.humano.tabuleiro)

        if(ganhou){
            runOnUiThread{
                var t = Toast.makeText(this, "Você Perdeu!", Toast.LENGTH_SHORT)
                t.show()
            }
        }
        else{
            val intent = Intent(this, JogadaHumanoActivity::class.java)
            mudarActivity(intent)
        }
    }

}
