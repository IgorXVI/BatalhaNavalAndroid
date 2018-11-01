package com.example.alunos.batalhanavalandroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class JogadaBotActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jogada_bot)

        val actionBar = supportActionBar
        actionBar!!.title = "Vez do Bot"
        actionBar.elevation = 4.0F
    }
}
