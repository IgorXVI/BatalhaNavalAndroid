package com.example.alunos.batalhanavalandroid

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import java.io.*

abstract class JogoActivity: AppCompatActivity() {

    var g = Global.getInstance()

    fun menssagemErroLoad(){

        runOnUiThread {
            val text = Toast.makeText(this, "Não existe nenhum jogo salvo.",
                    Toast.LENGTH_SHORT)
            text.show()
        }

    }

    fun loadArquivo(){
        try {
            if(g.humano == null && g.bot == null){
                val file = "save_batalha_naval"
                ObjectInputStream(FileInputStream(file)).use { it -> g = it.readObject() as Global }
            }

            val derrotaHumano = g.humano.tabuleiro.todosNaviosDestruidos()
            val derrotaBot = g.bot.tabuleiro.todosNaviosDestruidos()

            if(derrotaHumano || derrotaBot){
                menssagemErroLoad()
            }
            else{
                val intent: Intent
                if(g.ultimaActivity == "NaviosHumanoActivity"){
                    intent = Intent(this, NaviosHumanoActivity::class.java)
                }
                else{
                    intent = Intent(this, JogadaHumanoActivity::class.java)
                }
                startActivity(intent)
                finish()
            }
        }
        catch (e: Exception) {
            menssagemErroLoad()
        }
    }

    fun menssagemErroSave(){

        runOnUiThread {
            val text = Toast.makeText(this, "Não foi possível salvar o jogo.",
                    Toast.LENGTH_SHORT)
            text.show()
        }

    }

    fun salvarArquivo(){
        g.ultimaActivity = this.localClassName

        try {
            val file = "save_batalha_naval"
            ObjectOutputStream(FileOutputStream(file)).use{ it -> it.writeObject(g)}
        }
        catch (e: Exception) {
            menssagemErroSave()
        }
    }

}