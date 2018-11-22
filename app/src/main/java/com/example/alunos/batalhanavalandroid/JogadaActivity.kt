package com.example.alunos.batalhanavalandroid

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
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
        if(g!!.som){
            if(tabuleiro.posJaAcertada(x, y)){
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

    fun setErroAcerto(tabuleiro: Tabuleiro){
        setImagensErroAcerto(tabuleiro)
        setNumErroAcerto(tabuleiro)
    }

    open fun ataque(x: Int, y: Int, tabuleiro: Tabuleiro): Boolean{
        som(x,y, tabuleiro)
        setErroAcerto(tabuleiro)
        return tabuleiro.todosNaviosDestruidos()
    }

    fun mudarVez(deQuem: String){
        var intent: Intent
        if(deQuem == "Humano"){
            intent = Intent(this, JogadaHumanoActivity::class.java)
        }
        else{
            intent = Intent(this, JogadaBotActivity::class.java)
        }

        Timer().schedule(1000){
            startActivity(intent)
            finish()
        }
    }

    fun mensagemFim(mensagem: String){
        desTravarMenu()
        runOnUiThread{
            salvarItem?.setEnabled(false)
            var t = Toast.makeText(this, mensagem, Toast.LENGTH_SHORT)
            t.show()
        }
    }

}