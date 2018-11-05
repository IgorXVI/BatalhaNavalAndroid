package com.example.alunos.batalhanavalandroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){

    val g = Global.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_continuar.setOnClickListener {
            if(g.comecou){
                val intent = Intent(this, JogadaHumanoActivity::class.java)
                startActivity(intent)
            }
            else{
                var t = Toast.makeText(this,
                        "O jogo ainda não começou.", Toast.LENGTH_SHORT)
                t.show()
            }
        }

        btn_novoJogo.setOnClickListener {
            val intent = Intent(this, NovoJogoActivity::class.java)
            startActivity(intent)
        }

        btn_load.setOnClickListener {
            val intent = Intent(this, LoadSaveActivity::class.java)
            startActivity(intent)
        }

        btn_sobre.setOnClickListener {
            val intent = Intent(this, SobreActivity::class.java)
            startActivity(intent)
        }
    }
}
