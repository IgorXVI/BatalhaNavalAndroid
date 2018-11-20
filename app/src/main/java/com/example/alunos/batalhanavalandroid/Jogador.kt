package com.example.alunos.batalhanavalandroid

import java.io.Serializable

open class Jogador internal constructor() : Serializable {

    var nome: String
    val tabuleiro: Tabuleiro

    init {
        this.nome = "Humano"
        this.tabuleiro = Tabuleiro()
    }

    fun realizarJogada(x: Int, y: Int, adversario: Jogador) {
        if (!adversario.tabuleiro.posTemNavio(x, y)) {
            adversario.tabuleiro.setErro(x, y)
        } else {
            adversario.tabuleiro.setAcerto(x, y)
        }
    }
}
