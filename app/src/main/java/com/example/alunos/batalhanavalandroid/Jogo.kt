package com.example.alunos.batalhanavalandroid

import android.support.v7.app.AppCompatActivity

abstract class Jogo: AppCompatActivity(){
    var comecou = false
    var humano: Jogador = Jogador("Igor")
    var bot = Bot()
}