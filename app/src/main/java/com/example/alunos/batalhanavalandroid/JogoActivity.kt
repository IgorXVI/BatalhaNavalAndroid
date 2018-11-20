package com.example.alunos.batalhanavalandroid

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import java.io.*
import android.view.Menu
import android.view.MenuItem


abstract class JogoActivity: AppCompatActivity() {

    var g = Global.getInstance()
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
                g.som =!(antigo)
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

    fun menssagemErroLoad(){

        runOnUiThread {
            val text = Toast.makeText(this, "Não existe nenhum jogo salvo.",
                    Toast.LENGTH_SHORT)
            text.show()
        }

    }

    fun loadArquivo(){
        try {
            val file = "save_batalha_naval.ser"
            val fis = openFileInput(file)
            val oi = ObjectInputStream(fis)
            val save = oi.readObject() as Global
            oi.close()
            fis.close()

            if(save.humano == null || save.bot == null){
                menssagemErroLoad()
            }
            else{
                g.humano = save.humano
                g.bot = save.bot
                g.som = save.som

                val vitoriaBot = g.humano.tabuleiro.todosNaviosDestruidos()
                val vitoriaHumano = g.bot.tabuleiro.todosNaviosDestruidos()

                if(vitoriaBot || vitoriaHumano){
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
            val file = "save_batalha_naval.ser"
            val fos = openFileOutput(file, Context.MODE_PRIVATE)
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