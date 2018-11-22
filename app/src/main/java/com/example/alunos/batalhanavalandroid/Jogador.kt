package com.example.alunos.batalhanavalandroid

import java.io.Serializable

open class Jogador(val nome: String, val tabuleiro: Tabuleiro) : Serializable {

    fun realizarJogada(x: Int, y: Int, adversario: Jogador) {
        if (!adversario.tabuleiro.posTemNavio(x, y)) {
            adversario.tabuleiro.setErro(x, y)
        } else {
            adversario.tabuleiro.setAcerto(x, y)
        }
    }

}
