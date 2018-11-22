package com.example.alunos.batalhanavalandroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.io.ObjectInputStream

class MainActivity : JogoActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        g = this.application as Global

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return false
    }

    fun novoJogo(){
        g?.linhas = 7
        g?.colunas = 7
        g?.som = false
        g?.humano = Jogador("Humano", gerarTabuleiro())
        g?.bot = Bot("Bot", gerarTabuleiro(), g!!.humano)

        val intent =  Intent(this, NaviosHumanoActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun gerarTabuleiro(): Tabuleiro{
        val mapa = mutableMapOf<Int, Navio>()

        for(i in 2..4){
            mapa[i] = Navio(i)
        }

        val t = Tabuleiro(7, 7, mapa)
        t.gerarTabuleiro()
        return t
    }

}
