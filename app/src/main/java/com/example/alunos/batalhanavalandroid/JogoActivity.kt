package com.example.alunos.batalhanavalandroid

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import java.io.*
import android.content.Context.MODE_PRIVATE







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
            val fis = applicationContext.openFileInput(file)
            val `is` = ObjectInputStream(fis)
            val save = `is`.readObject() as Global
            `is`.close()
            fis.close()

            Global.setInstance(save)
            g = save

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
            val fos = applicationContext.openFileOutput(file, Context.MODE_PRIVATE)
            val os = ObjectOutputStream(fos)
            os.writeObject(g)
            os.close()
            fos.close()
        }
        catch (e: Exception) {
            menssagemErroSave()
        }
    }

}