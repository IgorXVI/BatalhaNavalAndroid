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
        g.humano = Jogador()
        g.bot = Bot(g.humano)
        g.som = true

        val intent =  Intent(this, NaviosHumanoActivity::class.java)
        startActivity(intent)
        finish()
    }

}
