package com.example.alunos.batalhanavalandroid

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

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
            val fileName = "save_batalha_naval"
            val fi = openFileInput(fileName)
            val oi = ObjectInputStream(fi)
            val save = oi.readObject() as Global
            oi.close()
            fi.close()

            g = save

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
            val fileName = "save_batalha_naval"
            val f = openFileOutput(fileName, Context.MODE_PRIVATE)
            val o = ObjectOutputStream(f)
            o.writeObject(g)
            f.close()
            o.close()
        }
        catch (e: Exception) {
            menssagemErroSave()
        }
    }

}