package com.example.alunos.batalhanavalandroid

import java.io.Serializable
import java.util.Random

class Navio internal constructor(var tamanho: Int) : Serializable {
    var posInicial: IntArray
    var posFinal: IntArray
    var vertical: Boolean = false
    var posicoes: Array<IntArray>

    init {
        this.posFinal = IntArray(2)
        this.posInicial = IntArray(2)
        this.posicoes = Array(this.tamanho) { IntArray(2) }
        this.gerarPosicoesRandomicas()
    }

    fun gerarPosicoesRandomicas() {
        val r = Random()
        this.vertical = r.nextBoolean()
        this.posInicial[0] = r.nextInt(7)
        this.posInicial[1] = r.nextInt(7)
        if (!this.vertical) {
            this.posFinal[1] = this.posInicial[1]
            this.posFinal[0] = this.posInicial[0] + this.tamanho - 1
            if (this.posFinal[0] >= 7) {
                this.posFinal[0] = this.posInicial[0] - this.tamanho + 1
            }
        } else {
            this.posFinal[0] = this.posInicial[0]
            this.posFinal[1] = this.posInicial[1] + this.tamanho - 1
            if (this.posFinal[1] >= 7) {
                this.posFinal[1] = this.posInicial[1] - this.tamanho + 1
            }
        }
        this.gerarTodasPosicoes()
    }

    fun gerarTodasPosicoes(){
        var inicio: Int

        if (!this.vertical) {
            if (this.posInicial[0] < this.posFinal[0]) {
                inicio = this.posInicial[0]
            } else {
                inicio = this.posFinal[0]
            }

            for (i in 0 until this.tamanho) {
                this.posicoes[i][1] = this.posInicial[1]
                this.posicoes[i][0] = inicio
                inicio++
            }
        } else {
            if (this.posInicial[1] < this.posFinal[1]) {
                inicio = this.posInicial[1]
            } else {
                inicio = this.posFinal[1]
            }

            for (i in 0 until this.tamanho) {
                this.posicoes[i][0] = this.posInicial[0]
                this.posicoes[i][1] = inicio
                inicio++
            }
        }
    }
}