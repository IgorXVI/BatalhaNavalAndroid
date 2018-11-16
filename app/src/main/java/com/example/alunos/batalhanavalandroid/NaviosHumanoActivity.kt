package com.example.alunos.batalhanavalandroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class NaviosHumanoActivity : AppCompatActivity() {

    val g = Global.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navios_humano)
    }

    fun zerarPos(){
        var navio: Navio

        for(i in 2..4){
            navio = g.humano.tabuleiro.getNavio(i)
            navio.posInicial = navio.posFinal
        }
    }

    fun posicionarNavio(view: View){

    }
}
