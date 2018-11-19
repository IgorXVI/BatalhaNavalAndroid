package com.example.alunos.batalhanavalandroid

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
            val file = "save_batalha_naval"
            val input = ObjectInputStream(FileInputStream(File(File(filesDir, "").toString() + File.separator + file)))
            g = input.readObject() as Global

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
                else if(g.ultimaActivity == "JogadaBotActivity"){
                    intent = Intent(this, JogadaBotActivity::class.java)
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
            val out = ObjectOutputStream(FileOutputStream(File(filesDir, "").toString() + File.separator + file))
            out.writeObject(g);
            out.close();
        }
        catch (e: Exception) {
            menssagemErroSave()
        }
    }

}