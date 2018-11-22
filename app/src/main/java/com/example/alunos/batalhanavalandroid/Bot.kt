package com.example.alunos.batalhanavalandroid

import java.io.Serializable
import java.util.*

class Bot(nome: String, tabuleiro: Tabuleiro, val humano: Jogador) : Jogador(nome, tabuleiro), Serializable {

    val cerebro = AI(humano.tabuleiro)

    fun realizarJogada(): IntArray {
        val arr = this.cerebro.ataque()

        val r = Random()
        val bomba = r.nextBoolean()

        super.realizarJogada(arr[0], arr[1], this.humano, bomba)
        return arr
    }

}
