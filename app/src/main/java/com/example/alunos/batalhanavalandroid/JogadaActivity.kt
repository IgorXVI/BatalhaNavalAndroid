package com.example.alunos.batalhanavalandroid

import android.content.Intent
import android.media.MediaPlayer
import android.view.View
import java.util.*
import kotlin.concurrent.schedule

abstract class JogadaActivity: TabuleiroActivity() {

    var mp: MediaPlayer? = null

    fun som(x: Int, y: Int, tabuleiro: Tabuleiro){
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

    open fun mudarActivity(intent: Intent){
        Timer().schedule(3100){
            startActivity(intent)
            finish()
        }
    }
}