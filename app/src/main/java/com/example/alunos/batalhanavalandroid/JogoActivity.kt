package com.example.alunos.batalhanavalandroid

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

abstract class JogoActivity: AppCompatActivity() {

    val g = Global.getInstance()

    fun menssagemErroLoad(){

        runOnUiThread {
            val text = Toast.makeText(this, "O jogo ainda não começou.",
                    Toast.LENGTH_SHORT)
            text.show()
        }

    }

    fun loadArquivo(){
        try {
            val fileName = "humano.ser"
            val fi = openFileInput(fileName)
            val oi = ObjectInputStream(fi)
            val saveHumano = oi.readObject() as Jogador
            oi.close()
            fi.close()

            val fileNameBot = "bot.ser"
            val fiBot = openFileInput(fileNameBot)
            val oiBot = ObjectInputStream(fiBot)
            val saveBot = oiBot.readObject() as Bot
            oiBot.close()
            fiBot.close()

            g.humano = saveHumano
            g.bot = saveBot

            val derrotaHumano = g.humano.tabuleiro.todosNaviosDestruidos()
            val derrotaBot = g.bot.tabuleiro.todosNaviosDestruidos()

            if(derrotaHumano || derrotaBot){
                menssagemErroLoad()
            }
            else{
                val intent = Intent(this, JogadaHumanoActivity::class.java)
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
        try {
            val fileName = "humano.ser"
            val f = openFileOutput(fileName, Context.MODE_PRIVATE)
            val o = ObjectOutputStream(f)
            o.writeObject(g.humano)
            f.close()
            o.close()

            val fileNameBot = "bot.ser"
            val fBot = openFileOutput(fileNameBot, Context.MODE_PRIVATE)
            val oBot = ObjectOutputStream(fBot)
            oBot.writeObject(g.bot)
            fBot.close()
            oBot.close()
        }
        catch (e: Exception) {
            menssagemErroSave()
        }
    }

}