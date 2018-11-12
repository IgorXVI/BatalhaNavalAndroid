package com.example.alunos.batalhanavalandroid

import android.content.Intent
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import java.util.*
import kotlin.concurrent.schedule

open class JogadaHumanoActivity : Jogada() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jogada_humano)

        if(g.humano.jaAtacou){
            val intent = Intent(this, JogadaBotActivity::class.java)
            startActivity(intent)
            finish()
        }
        else{
            setImagensTabuleiro(g.bot.tabuleiro)
        }
    }

    fun ataque(view: View) {
        travarTudo()
        var nome = resources.getResourceEntryName(view.id)
        var x = nome[4].toInt() - 48
        var y = nome[6].toInt() - 48

        g.humano.realizarJogada(x, y, g.bot)
        setImagensTabuleiro(g.humano.tabuleiro)
        som(x, y)

        salvar()

        val ganhou = g.bot.tabuleiro.todosNaviosDestruidos()

        if(ganhou){

            runOnUiThread {
                var t = Toast.makeText(this, "Você Ganhou!", Toast.LENGTH_SHORT)
                t.show()
            }

            val intent =  Intent(this, MainActivity::class.java)
            mudarActivity(intent)
        }
        else{
            val intent = Intent(this, JogadaBotActivity::class.java)
            mudarActivity(intent)
        }
    }

}
