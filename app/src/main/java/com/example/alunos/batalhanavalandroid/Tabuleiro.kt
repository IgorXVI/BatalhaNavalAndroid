package com.example.alunos.batalhanavalandroid

import java.io.Serializable
import java.util.ArrayList

class Tabuleiro internal constructor() : Serializable {

    var acertos: Int
    var erros: Int
    var tabuleiroPublico: Array<CharArray>
    var tabuleiroDoJogador: Array<CharArray>
    private var navios: MutableList<Navio>

    init {
        acertos = 0
        erros = 0
        this.tabuleiroPublico = Array(7) { CharArray(7) }
        this.tabuleiroDoJogador = Array(7) { CharArray(7) }
        this.navios = ArrayList()
        this.navios.add(Navio(2))
        this.navios.add(Navio(3))
        this.navios.add(Navio(4))
        this.gerarTabuleiro()
    }

    fun overlap(tamanho1: Int, tamanho2: Int): Boolean {
        val nt1: Double
        val dt1: Double
        val nt2: Double
        val dt2: Double
        val t1: Double
        val t2: Double
        var x1: Double
        var y1: Double
        var x2: Double
        var y2: Double
        var x3: Double
        var y3: Double
        var x4: Double
        var y4: Double
        var aux1: Double
        val a: Int
        val b: Int

        a = tamanho1 - 2
        b = tamanho2 - 2

        x1 = navios[a].posInicial[0].toDouble()
        y1 = navios[a].posInicial[1].toDouble()
        x2 = navios[a].posFinal[0].toDouble()
        y2 = navios[a].posFinal[1].toDouble()
        x3 = navios[b].posInicial[0].toDouble()
        y3 = navios[b].posInicial[1].toDouble()
        x4 = navios[b].posFinal[0].toDouble()
        y4 = navios[b].posFinal[1].toDouble()

        val verticalA = navios[a].vertical
        val verticalB = navios[b].vertical

        if (verticalA && verticalB && x1 == x3) {

            if (y1 > y2) {
                aux1 = y2
                y2 = y1
                y1 = aux1
            }
            if (y3 > y4) {
                aux1 = y4
                y4 = y3
                y3 = aux1
            }
            return y3 >= y1 && y3 <= y2 || y1 >= y3 && y1 <= y4
        }

        if (!verticalA && !verticalB && y1 == y3) {

            if (x1 > x2) {
                aux1 = x2
                x2 = x1
                x1 = aux1
            }
            if (x3 > x4) {
                aux1 = x4
                x4 = x3
                x3 = aux1
            }
            return x3 >= x1 && x3 <= x2 || x1 >= x3 && x1 <= x4
        }

        /*Aqui eu utilizo uma fórmula para ver se existe intersecção entre dois
        segmentos de reta.
        fonte: http://www.cs.swan.ac.uk/~cssimon/line_intersection.html */

        nt1 = (y3 - y4) * (x1 - x3) + (x4 - x3) * (y1 - y3)
        dt1 = (x4 - x3) * (y1 - y2) - (x1 - x2) * (y4 - y3)
        t1 = nt1 / dt1
        nt2 = (y1 - y2) * (x1 - x3) + (x2 - x1) * (y1 - y3)
        dt2 = (x4 - x3) * (y1 - y2) - (x1 - x2) * (y4 - y3)
        t2 = nt2 / dt2
        return t1 >= 0 && t1 <= 1 && t2 >= 0 && t2 <= 1
    }

    fun resetTabuleiro() {
        for (i in 0..6) {
            for (j in 0..6) {
                this.tabuleiroPublico[i][j] = '~'
            }
        }
    }

    fun resetTabuleiroAux() {
        for (i in 0..6) {
            for (j in 0..6) {
                this.tabuleiroDoJogador[i][j] = '~'
            }
        }
    }

    fun gerarTabuleiro() {
        resetTabuleiro()

        while (overlap(3, 4)) {
            navios[1].gerarPosicoesRandomicas()
        }
        while (overlap(2, 3) || overlap(2, 4)) {
            navios[0].gerarPosicoesRandomicas()
        }

        gerarTabuleiroAux()
    }

    fun gerarTabuleiroAux() {
        var xi: Int
        var xf: Int
        var yi: Int
        var yf: Int

        resetTabuleiroAux()

        for (k in 0..2) {
            val n = navios[k]
            xi = n.posInicial[0]
            yi = n.posInicial[1]
            xf = n.posFinal[0]
            yf = n.posFinal[1]
            if (!navios[k].vertical) {
                if (xi < xf) {
                    for (i in xi..xf) {
                        this.tabuleiroDoJogador[i][yi] = ('a'.toInt() + k).toChar()
                    }
                } else {
                    for (i in xf..xi) {
                        this.tabuleiroDoJogador[i][yi] = ('a'.toInt() + k).toChar()
                    }
                }
            } else {
                if (yi < yf) {
                    for (i in yi..yf) {
                        this.tabuleiroDoJogador[xi][i] = ('a'.toInt() + k).toChar()
                    }
                } else {
                    for (i in yf..yi) {
                        this.tabuleiroDoJogador[xi][i] = ('a'.toInt() + k).toChar()
                    }
                }
            }
        }
    }

    fun posTemNavio(x: Int, y: Int): Boolean{
        return this.tabuleiroDoJogador[x][y] != '~'
    }

    fun navioDestruido(tamanho: Int): Boolean {
        val n = this.navios[tamanho - 2]
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

    fun posParteNavio(x: Int, y: Int, tamanho: Int): Boolean {
        val n = this.navios[tamanho - 2]
        var x1: Int
        var y1: Int
        val posicoes = n.posicoes

        for (i in 0 until tamanho) {
            x1 = posicoes[i][0]
            y1 = posicoes[i][1]

            if (x == x1 && y == y1) {
                return true
            }
        }
        return false
    }

    fun posParteNavioDestruido(x: Int, y: Int): Boolean {
        for (i in 2..4) {
            if (navioDestruido(i) && posParteNavio(x, y, i)) {
                return true
            }
        }
        return false
    }

    fun todosNaviosDestruidos(): Boolean {
        for (i in 0..6) {
            for (j in 0..6) {
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
        if (x > 6 || x < 0 || y > 6 || y < 0) {
            return true
        }
        val c = this.tabuleiroPublico[x][y]
        return c != '~'
    }

    fun posJaErrada(x: Int, y: Int): Boolean {
        if (x > 6 || x < 0 || y > 6 || y < 0) {
            return true
        }
        val c = this.tabuleiroPublico[x][y]
        return c == '*'
    }

    fun posJaAcertada(x: Int, y: Int): Boolean {
        if (x > 6 || x < 0 || y > 6 || y < 0) {
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

    fun getNavio(tamanho: Int): Navio {
        return this.navios[tamanho - 2]
    }

    fun setAcerto(x: Int, y: Int) {
        this.tabuleiroPublico[x][y] = 'X'
        acertos++
    }

    fun setErro(x: Int, y: Int) {
        this.tabuleiroPublico[x][y] = '*'
        erros++
    }

    fun setNavio(n: Navio) {
        val index = n.tamanho - 2
        this.navios[index] = n
    }

}
