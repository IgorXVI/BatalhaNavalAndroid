package com.example.alunos.batalhanavalandroid

import java.io.Serializable
import java.util.Random

class Navio internal constructor(var tamanho: Int) : Serializable {

    var posicoes = Array(this.tamanho) { IntArray(2) }
    var vertical = false

    init {
        gerarPosicoesRandomicas(this.tamanho, this.tamanho)
    }

    fun gerarPosicoes(xi: Int, yi: Int, xMax: Int, yMax: Int){
        var inicio: Int

        if (!this.vertical) {

            if (xi + this.tamanho - 1 > xMax) {
                inicio = xi - this.tamanho + 1
            }
            else{
                inicio = xi
            }
            for (i in 0..this.tamanho-1) {
                this.posicoes[i][1] = yi
                this.posicoes[i][0] = inicio
                inicio++
            }

        } else {

            if (yi + this.tamanho - 1 > yMax) {
                inicio = yi - this.tamanho + 1
            }
            else{
                inicio = yi
            }
            for (i in 0..this.tamanho-1) {
                this.posicoes[i][0] = xi
                this.posicoes[i][1] = inicio
                inicio++
            }

        }

    }

    fun gerarPosicoesRandomicas(xMax: Int, yMax: Int){
        val r = Random()
        val xi = r.nextInt(xMax + 1)
        val yi = r.nextInt(yMax + 1)
        this.vertical = r.nextBoolean()
        gerarPosicoes(xi, yi, xMax, yMax)
    }

    fun overlap(navio: Navio): Boolean {
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

        x1 = this.posicoes[0][0].toDouble()
        y1 = this.posicoes[0][1].toDouble()
        x2 = this.posicoes[this.tamanho-1][0].toDouble()
        y2 = this.posicoes[this.tamanho-1][1].toDouble()
        x3 = navio.posicoes[0][0].toDouble()
        y3 = navio.posicoes[0][1].toDouble()
        x4 = navio.posicoes[navio.tamanho-1][0].toDouble()
        y4 = navio.posicoes[navio.tamanho-1][1].toDouble()

        val verticalA = this.vertical
        val verticalB = navio.vertical

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

    fun posParte(x: Int, y: Int): Boolean {
        var xi = this.posicoes[0][0]
        var yi = this.posicoes[0][1]
        var xf = this.posicoes[this.tamanho-1][0]
        var yf = this.posicoes[this.tamanho-1][1]
        var aux: Int
        val vertical = this.vertical

        if (vertical && x == xi) {
            if (yi > yf) {
                aux = yf
                yf = yi
                yi = aux
            }
            return y >= yi && y <= yf
        }
        else if(!vertical && y == yi){
            if (xi > xf) {
                aux = xf
                xf = xi
                xi = aux
            }
            return x >= xi && x <= xf
        }
        else{
            return false
        }

    }

}