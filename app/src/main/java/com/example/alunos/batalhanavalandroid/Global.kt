package com.example.alunos.batalhanavalandroid

import android.app.Application

class Global: Application() {
    private val tNULL = Tabuleiro(7, 7, mutableMapOf(1 to Navio(1)))
    var humano = Jogador("NULL", tNULL)
    var bot = Bot("NULL", tNULL, this.humano)
    var som = false
    var linhas = 7
    var colunas = 7

}