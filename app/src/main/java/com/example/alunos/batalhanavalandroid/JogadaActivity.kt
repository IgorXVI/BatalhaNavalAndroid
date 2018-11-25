package com.example.alunos.batalhanavalandroid

import android.content.Intent
import android.media.MediaPlayer
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import java.util.*
import kotlin.concurrent.schedule

abstract class JogadaActivity: TabuleiroActivity() {

    var jogador: Jogador? = null
    var mensagemFim: String? = null
    var proximaVez: Intent? = null

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

    fun ataque(){
        if(g!!.som){
            somTorpedo(0.5F)
            mp?.setOnCompletionListener {
                setAtaque()
            }
        }
        else{
            setAtaque()
        }
    }

    fun setAtaque(){
        setNumErroAcerto()
        setErrosAcertosTabuleiro()
        if(g!!.som && jogador!!.acertou){
            somAcerto(10F)
            mp?.setOnCompletionListener {
                fimAtaque()
            }
        }
        else{
            fimAtaque()
        }
    }

    abstract fun fimAtaque()

    fun mensagemFim(){
        runOnUiThread{
            var t = Toast.makeText(this, mensagemFim!!, Toast.LENGTH_SHORT)
            t.show()
        }
        val intent = Intent(this, MainActivity::class.java)
        Timer().schedule(5000){
            startActivity(intent)
            finish()
        }
    }

    fun mudarVez(){
        Timer().schedule(500){
            startActivity(proximaVez!!)
            finish()
        }
    }

}