package com.example.alunos.batalhanavalandroid

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import java.util.*
import kotlin.concurrent.schedule

class JogadaHumanoActivity : Jogada() {

    var menuPrincipalItem: MenuItem? = null
    var sobreItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jogada_humano)

        setImagensTabuleiro(g.bot.tabuleiro)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        menuPrincipalItem = menu?.getItem(0)
        sobreItem = menu?.getItem(1)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
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

    override fun mudarActivity(intent: Intent){
        menuPrincipalItem?.setEnabled(false)
        sobreItem?.setEnabled(false)

        Timer().schedule(3100){
            startActivity(intent)
            finish()
        }
    }

    fun ataque(view: View){
        travarTudo()
        var nome = resources.getResourceEntryName(view.id)
        var x = nome[4].toInt() - 48
        var y = nome[6].toInt() - 48

        g.humano.realizarJogada(x, y, g.bot)
        salvarArquivo()
        setImagensTabuleiro(g.bot.tabuleiro)
        som(x, y, g.bot.tabuleiro)

        val ganhou = g.bot.tabuleiro.todosNaviosDestruidos()

        if(ganhou){

            runOnUiThread {
                var t = Toast.makeText(this, "Você Ganhou!", Toast.LENGTH_SHORT)
                t.show()
            }

            val intent =  Intent(this, MainActivity::class.java)
            mudarActivity(intent)
        }
        else{
            val intent = Intent(this, JogadaBotActivity::class.java)
            mudarActivity(intent)
        }
    }

}
