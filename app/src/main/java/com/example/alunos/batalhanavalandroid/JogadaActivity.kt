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

    fun som(x: Int, y: Int){
        if(g!!.som){
            mp = MediaPlayer.create(this, R.raw.ataque_som)
            mp?.start()

            if(tabuleiro!!.posJaAcertada(x, y)){
                mp?.setOnCompletionListener {
                    if(tabuleiro!!.posJaAcertada(x, y)){
                        mp?.release()
                        mp = MediaPlayer.create(this, R.raw.explosao_som)
                        mp?.start()
                    }
                }
            }

        }
    }

    open fun ataque(x: Int, y: Int): Boolean{
        som(x,y)
        setErrosAcertosTabuleiro()
        setNumErroAcerto()
        return tabuleiro!!.todosNaviosDestruidos()
    }

    fun mudarVez(deQuem: String){
        var intent: Intent
        if(deQuem == "Humano"){
            intent = Intent(this, JogadaHumanoActivity::class.java)
        }
        else{
            intent = Intent(this, JogadaBotActivity::class.java)
        }

        mp?.setOnCompletionListener {
            mp?.release()

            Timer().schedule(500){
                startActivity(intent)
                finish()
            }

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