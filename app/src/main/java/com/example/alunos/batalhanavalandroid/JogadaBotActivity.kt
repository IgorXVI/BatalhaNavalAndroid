package com.example.alunos.batalhanavalandroid

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import java.util.*
import kotlin.concurrent.schedule

class JogadaBotActivity : Jogada() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jogada_bot)

        setImagensTabuleiro(g.humano.tabuleiro)
        travarTudo()
        Timer().schedule(1000){
            ataque()
        }
    }

    fun ataque() {
        g.bot.realizarJogada()
        salvarArquivo()
        setImagensTabuleiro(g.humano.tabuleiro)

        var s = ""
        for(i in 0..6){
            for(j in 0..6){
                s += g.bot.cerebro.tabuleiroProb[i][j].toString()
                if(j != 6){
                    s += " "
                }
            }
            s += "\n"
        }

        runOnUiThread{
            var t = Toast.makeText(this, s, Toast.LENGTH_SHORT)
            t.show()
        }

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
