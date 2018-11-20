package com.example.alunos.batalhanavalandroid

import java.io.Serializable

class Bot(private val humano: Jogador) : Jogador(), Serializable {
    val cerebro: AI

    init {
        this.nome = "Bot"
        this.cerebro = AI(humano.tabuleiro)
    }

    fun realizarJogada(): IntArray {
        val arr = this.cerebro.ataque()
        super.realizarJogada(arr[0], arr[1], this.humano)
        return arr
    }

}
