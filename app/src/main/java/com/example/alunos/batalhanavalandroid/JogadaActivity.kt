package com.example.alunos.batalhanavalandroid

import android.content.Intent
import android.media.MediaPlayer
import android.view.View
import android.widget.TextView
import java.util.*
import kotlin.concurrent.schedule

abstract class JogadaActivity: TabuleiroActivity() {

    var mp: MediaPlayer? = null

    fun setNumErroAcerto(tabuleiro: Tabuleiro){
        val lbl_acertos = findViewById<TextView>(R.id.lbl_acertos)
        val lbl_erros = findViewById<TextView>(R.id.lbl_erros)

        val textErros = "Erros: " + tabuleiro.erros.toString()
        val textAcertos = "Acertos: " + tabuleiro.acertos.toString()

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

    fun mudarActivity(intent: Intent){
        travarMenu()

        val delay: Long
        if(somItem?.isChecked!!){
            delay = 3100
        }
        else{
            delay = 310
        }

        Timer().schedule(delay){
            startActivity(intent)
            finish()
        }
    }

    fun setErroAcerto(tabuleiro: Tabuleiro){
        setImagensErroAcerto(tabuleiro)
        setNumErroAcerto(tabuleiro)
    }

    open fun ataque(x: Int, y: Int, tabuleiro: Tabuleiro): Boolean{
        som(x,y, tabuleiro)
        setErroAcerto(tabuleiro)
        return tabuleiro.todosNaviosDestruidos()
    }

}