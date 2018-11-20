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

        inicio(g.bot.tabuleiro)
    }

    fun ataque(view: View){
        travarTudo()

        val nome = resources.getResourceEntryName(view.id)
        val x = nome[4].toInt() - 48
        val y = nome[6].toInt() - 48
        g.humano.realizarJogada(x, y, g.bot)
        val ganhou = super.ataque(x, y, g.bot.tabuleiro)

        if(ganhou){
            runOnUiThread {
                var t = Toast.makeText(this, "Você Ganhou!", Toast.LENGTH_SHORT)
                t.show()
            }
        }
        else{
            val intent = Intent(this, JogadaBotActivity::class.java)
            mudarActivity(intent)
        }
    }

}
