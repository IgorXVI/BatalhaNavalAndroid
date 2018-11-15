package com.example.alunos.batalhanavalandroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.io.ObjectInputStream

class MainActivity : AppCompatActivity(){

    val g = Global.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_continuar.setOnClickListener {
            loadArquivo()
        }

        btn_novoJogo.setOnClickListener {
            novoJogo()
        }

        btn_sobre.setOnClickListener {
            val intent = Intent(this, SobreActivity::class.java)
            startActivity(intent)
        }
    }

    fun novoJogo(){
        g.humano = Jogador()
        g.bot = Bot(g.humano)

        val intent =  Intent(this, JogadaHumanoActivity::class.java)
        startActivity(intent)
        finish()
    }

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
}
