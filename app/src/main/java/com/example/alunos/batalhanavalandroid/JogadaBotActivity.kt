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

        salvarArquivo()
        travarTudo()
        setImagensNavios()
        setImagensTabuleiro(g.humano.tabuleiro)
        setErrosAcertos(g.humano.tabuleiro)
        Timer().schedule(1000){
            ataque()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return false
    }

    fun ataque() {
        g.bot.realizarJogada()
        setImagensTabuleiro(g.humano.tabuleiro)
        setErrosAcertos(g.humano.tabuleiro)

        val x = g.bot.cerebro.posUltimo[0]
        val y = g.bot.cerebro.posUltimo[1]
        som(x,y, g.humano.tabuleiro)

        val ganhou = g.humano.tabuleiro.todosNaviosDestruidos()

        if(ganhou){
            runOnUiThread{
                var t = Toast.makeText(this, "Você Perdeu!", Toast.LENGTH_SHORT)
                t.show()
            }

            val intent =  Intent(this, MainActivity::class.java)
            mudarActivity(intent)
        }
        else{
            val intent = Intent(this, JogadaHumanoActivity::class.java)
            mudarActivity(intent)
        }
    }

}
