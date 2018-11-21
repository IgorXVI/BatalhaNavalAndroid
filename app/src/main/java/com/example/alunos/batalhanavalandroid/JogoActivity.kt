package com.example.alunos.batalhanavalandroid

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import java.io.*
import android.view.Menu
import android.view.MenuItem


abstract class JogoActivity: AppCompatActivity() {

    var g = this.application as Global
    var somItem: MenuItem? = null
    var salvarItem: MenuItem? = null
    var menuPrincipalItem: MenuItem? = null
    var sobreItem: MenuItem? = null

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        somItem = menu?.getItem(0)
        salvarItem = menu?.getItem(1)
        menuPrincipalItem = menu?.getItem(2)
        sobreItem = menu?.getItem(3)

        somItem?.isChecked = g.som

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.Som -> {
                val antigo = somItem?.isChecked!!
                somItem?.isChecked = !(antigo)
                g.som = !(antigo)
            }
            R.id.Salvar -> {
                salvarArquivo()
            }
            R.id.MenuPrincipal -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.Sobre -> {
                val intent = Intent(this, SobreActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun travarMenu(){
        runOnUiThread {
            somItem?.setEnabled(false)
            salvarItem?.setEnabled(false)
            menuPrincipalItem?.setEnabled(false)
            sobreItem?.setEnabled(false)
        }
    }

    fun desTravarMenu(){
        runOnUiThread {
            somItem?.setEnabled(true)
            salvarItem?.setEnabled(true)
            menuPrincipalItem?.setEnabled(true)
            sobreItem?.setEnabled(true)
        }
    }

    fun menssagemErroLoad(){

        runOnUiThread {
            val text = Toast.makeText(this, "Não existe nenhum jogo salvo.",
                    Toast.LENGTH_SHORT)
            text.show()
        }

    }

    fun loadArquivo(){
        try {
            val fileHumano = "save_humano.ser"
            val fisHumano = openFileInput(fileHumano)
            val oiHumano = ObjectInputStream(fisHumano)
            val saveHumano = oiHumano.readObject() as Jogador
            oiHumano.close()
            fisHumano.close()

            val fileBot = "save_bot.ser"
            val fisBot = openFileInput(fileBot)
            val oiBot = ObjectInputStream(fisBot)
            val saveBot = oiBot.readObject() as Bot
            oiHumano.close()
            fisHumano.close()

            g.humano = saveHumano
            g.bot = saveBot

            val intent: Intent
            if(g.ultimaActivity == "NaviosHumanoActivity"){
                intent = Intent(this, NaviosHumanoActivity::class.java)
            }
            else{
                intent = Intent(this, JogadaActivity::class.java)
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
            val fileHumano = "save_humano.ser"
            val fosHumano = openFileOutput(fileHumano, Context.MODE_PRIVATE)
            val osHumano = ObjectOutputStream(fosHumano)
            osHumano.writeObject(g.humano)
            osHumano.close()
            fosHumano.close()

            val fileBot = "save_bot.ser"
            val fosBot = openFileOutput(fileBot, Context.MODE_PRIVATE)
            val osBot = ObjectOutputStream(fosBot)
            osBot.writeObject(g.bot)
            osBot.close()
            fosBot.close()
        }
        catch (e: Exception) {
            menssagemErroSave()
        }
    }

}