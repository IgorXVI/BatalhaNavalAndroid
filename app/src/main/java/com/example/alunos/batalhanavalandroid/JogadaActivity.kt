package com.example.alunos.batalhanavalandroid

import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import java.util.*
import kotlin.concurrent.schedule

abstract class JogadaActivity: TabuleiroActivity() {

    fun setNumErroAcerto(){
        val lbl_acertos = findViewById<TextView>(R.id.lbl_acertos)
        val lbl_erros = findViewById<TextView>(R.id.lbl_erros)

        val textErros = "Erros: " + tabuleiro!!.erros.toString()
        val textAcertos = "Acertos: " + tabuleiro!!.acertos.toString()

        runOnUiThread {
            lbl_erros.text = textErros
            lbl_acertos.text = textAcertos
        }

    }

    fun fim(mensagem: String){
        runOnUiThread{
            var t = Toast.makeText(this, mensagem, Toast.LENGTH_SHORT)
            t.show()
        }

        val intent = Intent(this, MainActivity::class.java)
        Timer().schedule(5000){
            startActivity(intent)
            finish()
        }

    }

    fun mudarVez(intent: Intent){
        Timer().schedule(500){
            startActivity(intent)
            finish()
        }
    }

}