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

    fun somTorpedo(){
        mp = MediaPlayer.create(this, R.raw.ataque_som)
        mp?.start()
    }

    fun somAcerto(){
        mp = MediaPlayer.create(this, R.raw.explosao_som)
        mp?.start()
    }


    fun fim(intent: Intent, mensagem: String){
        var delay: Long

        if(g!!.som){
            somTorpedo()
            delay = 4500
        }
        else{
            delay = 0
        }

        Timer().schedule(delay){
            if(mp != null){
                mp?.stop()
                mp?.release()
                mp = null
            }

            if(g!!.som && teveAcerto()){
                somAcerto()
                delay = 3000
            }
            else{
                delay = 0
            }

            setNumErroAcerto()
            setErrosAcertosTabuleiro()

            Timer().schedule(delay){
                if(mp != null){
                    mp?.stop()
                    mp?.release()
                }

                val ganhou = tabuleiro!!.todosNaviosDestruidos()
                if(!ganhou){

                    Timer().schedule(500){
                        startActivity(intent)
                        finish()
                    }

                }
                else{
                    mensagemFim(mensagem)
                }
            }

        }
    }

    fun mensagemFim(mensagem: String){
        runOnUiThread{
            var t = Toast.makeText(this, mensagem, Toast.LENGTH_SHORT)
            t.show()
        }

        val intent = Intent(this, MainActivity::class.java)
        Timer().schedule(3000){
            startActivity(intent)
            finish()
        }

    }

    fun teveAcerto(): Boolean{
        val acertosAntes = findViewById<TextView>(R.id.lbl_acertos).text.toString()
        val acertosDepois = "Acertos: " + tabuleiro!!.acertos.toString()
        return !(acertosAntes.equals(acertosDepois))
    }

}