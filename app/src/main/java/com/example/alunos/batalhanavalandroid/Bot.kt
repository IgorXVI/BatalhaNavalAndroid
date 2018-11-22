package com.example.alunos.batalhanavalandroid

import java.io.Serializable

class Bot(nome: String, tabuleiro: Tabuleiro, val humano: Jogador) : Jogador(nome, tabuleiro), Serializable {

    val cerebro = AI(humano.tabuleiro)

    fun realizarJogada(): IntArray {
        val arr = this.cerebro.ataque()
        super.realizarJogada(arr[0], arr[1], this.humano)
        return arr
    }

}
