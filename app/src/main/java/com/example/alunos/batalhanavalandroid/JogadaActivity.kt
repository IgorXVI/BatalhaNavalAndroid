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

    fun somTorpedo():Long{
        mp?.release()
        if(g!!.som){
            mp = MediaPlayer.create(this, R.raw.ataque_som)
            mp?.start()
            return 5500
        }
        return 0
    }

    fun somAcerto(tabuleiroAntes: Array<CharArray>): Long{
        mp?.release()
        if(g!!.som){
            val tabuleiroDepois = tabuleiro!!.tabuleiroPublico
            for(i in 0..tabuleiro!!.linhas-1){
                for(j in 0..tabuleiro!!.colunas-1){
                    if(tabuleiroDepois[i][j] == 'X' && tabuleiroAntes[i][j] != 'X'){
                        mp = MediaPlayer.create(this, R.raw.explosao_som)
                        mp?.start()
                        return 3500
                    }
                }
            }
        }
        return 0
    }

    fun fimAtaque(tabuleiroAntes: Array<CharArray>, deQuem: String){

        Timer().schedule(somTorpedo()){

            setNumErroAcerto()
            setErrosAcertosTabuleiro()

            Timer().schedule(somAcerto(tabuleiroAntes)){
                fim(deQuem)
            }

        }

    }

    fun fim(deQuem: String){
        val intent: Intent
        val mensagem: String
        if(deQuem == "Bot"){
            mensagem = "Você Ganhou!"
            intent = Intent(this, JogadaHumanoActivity::class.java)
        }
        else{
            mensagem = "Você Perdeu!"
            intent = Intent(this, JogadaBotActivity::class.java)
        }

        val ganhou = tabuleiro!!.todosNaviosDestruidos()
        if(ganhou){
            desTravarMenu()
            runOnUiThread{
                salvarItem?.setEnabled(false)
                var t = Toast.makeText(this, mensagem, Toast.LENGTH_SHORT)
                t.show()
            }
        }
        else{

            Timer().schedule(500){
                startActivity(intent)
                finish()
            }

        }
    }

}