package com.example.alunos.batalhanavalandroid

import java.util.*
import kotlin.jvm.internal.Ref
import java.io.Serializable

class AI(val tabuleiro: Tabuleiro): Serializable {

    /*
    sentido = true : positivo
    sentido = flase : negativo
     */

    /*
   Passos do ataque:

   primeiro : atacar a posição mais provável de conter um navio ->

   segundo : se o primeiro passo resultar em sucesso, atacar posição na vertical
   ou na horizontal do último ataque e repetir esse passo até um acerto, quando o
   acerto aconter definir o alinhamento do navio (vertical ou horizontal) ->

   terceiro : atacar posições relativas ao ponto de acerto até errar pela primeira vez, então
   inverter o sentido de ataque e atacar até errar uma segunda vez ->

   primeiro : atacar a posição mais provável de conter um navio -> ...


   repetir o ciclo (primeiro -> segundo -> terceiro -> primeiro -> segundo-> terceiro-> ...) até alguém ganhar
    */

    var posUltimo = IntArray(2)
    private var posInicalAcerto = IntArray(2)
    private var inimigoVertical = false
    private var erro1 = false
    private var erro2 = false
    private var sentido = false
    var segundo = false
    var terceiro = false
    private var tabuleiroProb = Array(7, {IntArray(7)})

    init {
        this.posUltimo[0] = -1
        this.posUltimo[1] = -1
    }

    fun ataque(){

        if(terceiro){
            this.terceiroPasso()
        }
        else if(segundo){
            this.segundoPasso()
        }
        else{
            this.primeiroPasso()
        }
    }

    fun checkAcerto(){
        val x = posUltimo[0]
        val y = posUltimo[1]
        if(x != -1 && y != -1){
            if(terceiro){
                if (!this.erro1) {
                    this.erro1 = !posJaAcertada(x, y)
                    if (this.erro1) {
                        this.sentido = !this.sentido
                    }
                } else {
                    this.erro2 = !posJaAcertada(x, y)
                    if (this.erro2) {
                        this.reset()
                    }
                }
            }
            else if(segundo){
               this.terceiro = posJaAcertada(x, y)
               if(this.terceiro){
                   this.segundo = false
               }
            }
            else{
                this.segundo = posJaAcertada(x, y)
            }
        }
    }

    private fun terceiroPasso(){
        val x: Int
        val y: Int
        if (!this.inimigoVertical) {
            y = this.posInicalAcerto[1]

            if (this.sentido) {
                x = this.posUltimo[0] + 1
            } else {
                x = this.posUltimo[0] - 1
            }
        } else {
            x = this.posInicalAcerto[0]
            if (this.sentido) {
                y = this.posUltimo[1] + 1
            } else {
                y = this.posUltimo[1] - 1
            }
        }

        if(posJaErrada(x, y)) {
            if (!this.erro1) {
                this.sentido = !this.sentido
                this.erro1 = true
                this.terceiroPasso()
            } else {
                this.reset()
                this.primeiroPasso()
            }
        } else {
            this.posUltimo[0] = x
            this.posUltimo[1] = y
            if (posJaAcertada(x, y)){
                this.terceiroPasso()
            }
        }
    }

    private fun segundoPasso(){
        var x: Int
        var y: Int
        val r = Random()
        x = this.posInicalAcerto[0]
        y = this.posInicalAcerto[1]

        if(posCercada(x, y)){
            if(posJaAcertada(x-1, y)){
                this.inimigoVertical = false
                this.sentido = false
                x--
            }
            else if(posJaAcertada(x+1, y)){
                this.inimigoVertical = false
                this.sentido = true
                x++
            }
            else if(posJaAcertada(x, y-1)){
                this.inimigoVertical = true
                this.sentido = false
                y--
            }
            else{
                this.inimigoVertical = true
                this.sentido = true
                y++
            }
            this.posUltimo[0] = x
            this.posUltimo[1] = y
            this.terceiroPasso()
        }
        else{
            while (this.posJaAtacada(x, y)) {
                this.inimigoVertical = r.nextBoolean()
                this.sentido = r.nextBoolean()
                if (!this.inimigoVertical) {
                    y = this.posInicalAcerto[1]
                    if (this.posInicalAcerto[0] == 6) {
                        this.sentido = false
                    }
                    if (this.posInicalAcerto[0] == 0) {
                        this.sentido = true
                    }
                    if (this.sentido) {
                        x = this.posInicalAcerto[0] + 1
                    } else {
                        x = this.posInicalAcerto[0] - 1
                    }
                } else {
                    x = this.posInicalAcerto[0]
                    if (this.posInicalAcerto[1] == 6) {
                        this.sentido = false
                    }
                    if (this.posInicalAcerto[1] == 0) {
                        this.sentido = true
                    }
                    if (this.sentido) {
                        y = this.posInicalAcerto[1] + 1
                    } else {
                        y = this.posInicalAcerto[1] - 1
                    }
                }
            }
            this.posUltimo[0] = x
            this.posUltimo[1] = y
        }
    }

    private fun primeiroPasso(){
        this.resetTabuleiroProb()
        for(i in 2..4){
            this.prob(i)
        }

        var x = -1
        var y = -1
        var maior = 0
        for(i in 0..6){

            for(j in 0..6){

                if(this.tabuleiroProb[i][j] > maior){
                    maior = this.tabuleiroProb[i][j]
                    x = i
                    y = j
                }

            }

        }

        this.posUltimo[0] = x
        this.posUltimo[1] = y
        this.posInicalAcerto[0] = x
        this.posInicalAcerto[1] = y
    }

    fun prob(tamanho: Int){
        var erro = false
        var xInicial = 0
        var xFinal = tamanho - 1
        var peso = 1

        for(y in 0..6){
            for(x in xInicial..xFinal){
                erro = posJaErrada(x, y)
                if(erro){
                    break
                }
                if(posJaAcertada(x, y)){
                    peso++
                }
            }
            if(!erro){
                for(x in xInicial..xFinal){
                    if(!posJaAcertada(x, y)){
                        this.tabuleiroProb[x][y] += peso
                    }
                }
            }
            xInicial++
            xFinal++
            peso = 1
        }

        var yInicial = 0
        var yFinal = tamanho - 1

        for(x in 0..6){
            for(y in yInicial..yFinal){
                erro = posJaErrada(x, y)
                if(erro){
                    break
                }
                if(posJaAcertada(x, y)){
                    peso++
                }
            }
            if(!erro){
                for(y in yInicial..yFinal){
                    if(!posJaAcertada(x, y)){
                        this.tabuleiroProb[x][y] += peso
                    }
                }
            }
            yInicial++
            yFinal++
            peso = 1
        }

    }

    private fun posCercada(x: Int, y: Int): Boolean{
        val semEsquerda = posJaAtacada(x-1, y)
        val semDireita = posJaAtacada(x+1, y)
        val semBaixo = posJaAtacada(x, y-1)
        val semCima = posJaAtacada(x, y+1)

        return semCima && semBaixo && semDireita && semEsquerda
    }

    private fun posJaAtacada(x: Int, y: Int): Boolean {
        if(x > 6 || x < 0 || y > 6 || y < 0){
            return false
        }
        val c = this.tabuleiro.tabuleiroPublico[x][y]
        return c != '~'
    }

    private fun posJaErrada(x: Int, y: Int): Boolean {
        if(x > 6 || x < 0 || y > 6 || y < 0){
            return false
        }
        val c = this.tabuleiro.tabuleiroPublico[x][y]
        return c == '*'
    }

    private fun posJaAcertada(x: Int, y: Int): Boolean {
        if(x > 6 || x < 0 || y > 6 || y < 0){
            return false
        }
        val c = this.tabuleiro.tabuleiroPublico[x][y]
        return c == 'X'
    }

    fun resetTabuleiroProb(){
        for(i in 0..6){

            for(j in 0..6){
                this.tabuleiroProb[i][j] = 0
            }

        }
    }

    private fun reset(){
        this.segundo = false
        this.terceiro = false
        this.erro1 = false
        this.erro2 = false
        this.posUltimo[0] = -1
        this.posUltimo[1] = -1
    }

}