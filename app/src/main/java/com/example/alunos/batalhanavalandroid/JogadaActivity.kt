package com.example.alunos.batalhanavalandroid

import android.content.Intent
import android.media.MediaPlayer
import android.view.View
import android.widget.TextView
import java.util.*
import kotlin.concurrent.schedule

abstract class JogadaActivity: TabuleiroActivity() {

    var mp: MediaPlayer? = null

    fun setImagensTabuleiro(tabuleiro: Tabuleiro){
        var c: Char

        for(i in 0..6){
            for(j in 0..6){
                c = tabuleiro.tabuleiroPublico[i][j]
                if(c == 'X'){
                    setAcerto(i, j)
                }
                if(c == '*'){
                    setErro(i, j)
                }
            }
        }
    }

    fun setErrosAcertos(tabuleiro: Tabuleiro){
        val lbl_acertos = findViewById<TextView>(R.id.lbl_acertos)
        val lbl_erros = findViewById<TextView>(R.id.lbl_erros)

        val textErros = "Erros: " + tabuleiro.numeroErros().toString()
        val textAcertos = "Acertos: " + tabuleiro.numeroAcertos().toString()

        runOnUiThread {
            lbl_erros.text = textErros
            lbl_acertos.text = textAcertos
        }

    }

    fun som(x: Int, y: Int, tabuleiro: Tabuleiro){
        if(g.som){
            var acertou = !(x == -1 && y == -1)
            if(acertou){
                acertou = tabuleiro.tabuleiroPublico[x][y] == 'X'
            }

            if(acertou){
                mp = MediaPlayer.create(this, R.raw.explosao_som)
            }
            else{
                mp = MediaPlayer.create(this, R.raw.espuma_som)
            }
            mp?.start()
            mp?.setOnCompletionListener {
                mp?.release()
            }
        }
    }

    open fun mudarActivity(intent: Intent){
        Timer().schedule(3100){
            startActivity(intent)
            finish()
        }
    }
}