package com.example.alunos.batalhanavalandroid

import android.app.Application

class Global(): Application() {
    var humano = Jogador()
    var bot = Bot(this.humano)
    var ultimaActivity = "NaviosHumanoActivity"
    var som = false
}