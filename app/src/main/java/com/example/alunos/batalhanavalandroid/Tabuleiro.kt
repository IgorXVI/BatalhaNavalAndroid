package com.example.alunos.batalhanavalandroid

import java.io.Serializable

class Tabuleiro internal constructor(val linhas: Int, val colunas: Int, var navios: MutableMap<Int, Navio>) : Serializable {

    var acertos: Int
    var erros: Int
    var tabuleiroPublico: Array<CharArray>
    var tabuleiroDoJogador: Array<CharArray>

    init {
        this.acertos = 0
        this.erros = 0
        this.tabuleiroPublico = Array(this.linhas) { CharArray(this.colunas) }
        this.tabuleiroDoJogador = Array(this.linhas) { CharArray(this.colunas) }

        this.gerarTabuleiro()
    }

    fun temOverlapNoTabuleiro(tamanho: Int): Boolean{
        val n = this.navios[tamanho]!!

        for((key, value) in this.navios){
            if(tamanho != key && n.overlap(value)){
                return true
            }
        }
        return false
    }

    fun resetTabuleiro() {
        for (i in 0..this.linhas-1) {
            for (j in 0..this.colunas-1) {
                this.tabuleiroPublico[i][j] = '~'
            }
        }
    }

    fun resetTabuleiroAux() {
        for (i in 0..this.linhas-1) {
            for (j in 0..this.colunas-1) {
                this.tabuleiroDoJogador[i][j] = '~'
            }
        }
    }

    fun gerarTabuleiro() {
        resetTabuleiro()
        val xMax = this.linhas - 1
        val yMax = this.colunas - 1

        for((key, value) in this.navios){
            value.gerarPosicoesRandomicas(xMax, yMax)
            while(temOverlapNoTabuleiro(key)){
                value.gerarPosicoesRandomicas(xMax, yMax)
            }
        }

        gerarTabuleiroAux()
    }

    fun gerarTabuleiroAux() {
        var xi: Int
        var xf: Int
        var yi: Int
        var yf: Int

        resetTabuleiroAux()

        for ((key, value) in this.navios) {
            xi = value.posicoes[0][0]
            yi = value.posicoes[0][1]
            xf = value.posicoes[key-1][0]
            yf = value.posicoes[key-1][1]
            if (!value.vertical) {
                if (xi < xf) {
                    for (i in xi..xf) {
                        this.tabuleiroDoJogador[i][yi] = ('a'.toInt() + key).toChar()
                    }
                } else {
                    for (i in xf..xi) {
                        this.tabuleiroDoJogador[i][yi] = ('a'.toInt() + key).toChar()
                    }
                }
            } else {
                if (yi < yf) {
                    for (i in yi..yf) {
                        this.tabuleiroDoJogador[xi][i] = ('a'.toInt() + key).toChar()
                    }
                } else {
                    for (i in yf..yi) {
                        this.tabuleiroDoJogador[xi][i] = ('a'.toInt() + key).toChar()
                    }
                }
            }
        }
    }

    fun posTemNavio(x: Int, y: Int): Boolean{
        return this.tabuleiroDoJogador[x][y] != '~'
    }

    fun navioDestruido(tamanho: Int): Boolean {
        val n = this.navios[tamanho]!!
        var x: Int
        var y: Int
        val posicoes = n.posicoes

        for (i in 0 until tamanho) {
            x = posicoes[i][0]
            y = posicoes[i][1]
            if (this.tabuleiroPublico[x][y] != 'X') {
                return false
            }
        }
        return true
    }

    fun posParteNavioDestruido(x: Int, y: Int): Boolean {
        for ((key, value) in navios) {
            if (navioDestruido(key) && value.posParte(x, y)) {
                return true
            }
        }
        return false
    }

    fun todosNaviosDestruidos(): Boolean {
        for (i in 0..this.linhas-1) {
            for (j in 0..this.colunas-1) {
                if (this.tabuleiroDoJogador[i][j] != '~' && this.tabuleiroPublico[i][j] != 'X') {
                    return false
                }
            }
        }
        return true
    }

    fun posCercada(x: Int, y: Int): Boolean {
        val semEsquerda = posJaAtacada(x - 1, y)
        val semDireita = posJaAtacada(x + 1, y)
        val semBaixo = posJaAtacada(x, y - 1)
        val semCima = posJaAtacada(x, y + 1)

        return semCima && semBaixo && semDireita && semEsquerda
    }

    fun posJaAtacada(x: Int, y: Int): Boolean {
        if (x >= this.linhas || x < 0 || y >= this.colunas || y < 0) {
            return true
        }
        val c = this.tabuleiroPublico[x][y]
        return c != '~'
    }

    fun posJaErrada(x: Int, y: Int): Boolean {
        if (x >= this.linhas || x < 0 || y >= this.colunas || y < 0) {
            return true
        }
        val c = this.tabuleiroPublico[x][y]
        return c == '*'
    }

    fun posJaAcertada(x: Int, y: Int): Boolean {
        if (x >= this.linhas || x < 0 || y >= this.colunas || y < 0) {
            return false
        }
        val c = this.tabuleiroPublico[x][y]
        return c == 'X'
    }

    fun posObstaculo(x: Int, y: Int): Boolean {
        return this.posParteNavioDestruido(x, y) || this.posJaErrada(x, y)
    }

    fun posInutil(x: Int, y: Int): Boolean {
        return this.posJaAtacada(x, y) || this.posCercada(x, y)
    }

    fun setAcerto(x: Int, y: Int) {
        this.tabuleiroPublico[x][y] = 'X'
        acertos++
    }

    fun setErro(x: Int, y: Int) {
        this.tabuleiroPublico[x][y] = '*'
        erros++
    }

}
