package com.example.alunos.batalhanavalandroid

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast

class JogadaHumanoActivity : JogadaActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jogada_humano)

        setImagensTabuleiro(g.bot.tabuleiro)
        setErrosAcertos(g.bot.tabuleiro)
    }

    fun ataque(view: View){
        travarTudo()

        var nome = resources.getResourceEntryName(view.id)
        var x = nome[4].toInt() - 48
        var y = nome[6].toInt() - 48

        g.humano.realizarJogada(x, y, g.bot)
        setImagensTabuleiro(g.bot.tabuleiro)
        setErrosAcertos(g.bot.tabuleiro)
        som(x, y, g.bot.tabuleiro)

        val ganhou = g.bot.tabuleiro.todosNaviosDestruidos()

        if(ganhou){
            runOnUiThread {
                var t = Toast.makeText(this, "Você Ganhou!", Toast.LENGTH_SHORT)
                t.show()
            }
        }
        else{
            travarMenu()

            val intent = Intent(this, JogadaBotActivity::class.java)
            mudarActivity(intent)
        }
    }

}
