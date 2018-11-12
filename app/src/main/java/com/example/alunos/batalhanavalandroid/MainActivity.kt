package com.example.alunos.batalhanavalandroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){

    val g = Global.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_continuar.setOnClickListener {
            continuar()
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
        g.arquivoJogo = ArquivoJogo()

        val intent =  Intent(this, JogadaHumanoActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun continuar(){
        if(g.arquivoJogo == null){
            menssagemErroLoad()
        }
        else{
            val deuCerto = g.arquivoJogo.load(g, this)
            if(deuCerto){
                val intent = Intent(this, JogadaHumanoActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                menssagemErroLoad()
            }
        }
    }

    fun menssagemErroLoad(){

        runOnUiThread {
            val text = Toast.makeText(this, "O jogo ainda não começou.",
                    Toast.LENGTH_SHORT)
            text.show()
        }

    }
}
