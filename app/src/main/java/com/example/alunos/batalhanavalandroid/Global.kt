package com.example.alunos.batalhanavalandroid

import android.app.Application

class Global: Application() {
    var humano: Jogador? = null
    var bot: Bot? = null
    var som = false
    var linhas = 7
    var colunas = 7

}