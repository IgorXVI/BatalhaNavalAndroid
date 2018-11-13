package com.example.alunos.batalhanavalandroid

import java.util.*
import kotlin.jvm.internal.Ref

class AI(val tabuleiro: Tabuleiro) {

    /*
    sentido = true : positivo
    sentido = flase : negativo
     */

    /*
   Passos do ataque:

   primeiro : atacar uma posição adjacente à um acerto, se não ouver nenhuma atacar
   posição randômica útil ->

   segundo : se o primeiro passo resultar em sucesso, atacar posição na vertical
   ou na horizontal do último ataque e repetir esse passo até um acerto, quando o
   acerto aconter definir o alinhamento do navio (vertical ou horizontal) ->

   terceiro : atacar posições relativas ao ponto de acerto até errar pela primeira vez, então
   inverter o sentido de ataque e atacar até errar uma segunda vez ->

   primeiro : atacar posição randômica útil -> ...


   repetir o ciclo (primeiro -> segundo -> terceiro -> primeiro) até alguém ganhar
    */

    var posUltimo = IntArray(2)
    private var posInicalAcerto = IntArray(2)
    private var inimigoVertical = false
    private var erro1 = false
    private var erro2 = false
    private var sentido = false
    var segundo = false
    var terceiro = false

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

        var fim = x > 6 || x < 0 || y > 6 || y < 0
        if(!fim){
            fim = posJaErrada(x, y)
        }

        if(fim) {
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

        if(posInutil(x, y)){
            this.reset()
            this.primeiroPasso()
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
        var x: Int
        var y: Int

        x = this.posAdjacenteAcerto()[0]
        y = this.posAdjacenteAcerto()[1]

        if(x == -1 && y == -1){
            val r = Random()
            x = r.nextInt(7)
            y = r.nextInt(7)
            while (this.posJaAtacada(x,y) || this.posInutil(x, y)) {
                x = r.nextInt(7)
                y = r.nextInt(7)
            }
        }

        this.posUltimo[0] = x
        this.posUltimo[1] = y
        this.posInicalAcerto[0] = x
        this.posInicalAcerto[1] = y
    }

    private fun posJaAtacada(x: Int, y: Int): Boolean {
        val c = this.tabuleiro.tabuleiroPublico[x][y]
        return c != '~'
    }

    private fun posJaErrada(x: Int, y: Int): Boolean {
        val c = this.tabuleiro.tabuleiroPublico[x][y]
        return c == '*'
    }

    private fun posJaAcertada(x: Int, y: Int): Boolean {
        val c = this.tabuleiro.tabuleiroPublico[x][y]
        return c == 'X'
    }

    private fun reset(){
        this.segundo = false
        this.terceiro = false
        this.erro1 = false
        this.erro2 = false
        this.posUltimo[0] = -1
        this.posUltimo[1] = -1
    }

    private fun posInutil(x: Int, y: Int): Boolean{
        var semCima: Boolean
        var semBaixo: Boolean
        var semEsquerda: Boolean
        var semDireita: Boolean

        if(x > 0){
            semEsquerda = posJaAtacada(x-1, y)
        }
        else{
            semEsquerda = true
        }

        if(x < 6){
            semDireita = posJaAtacada(x+1, y)
        }
        else{
            semDireita = true
        }

        if(y > 0){
            semBaixo = posJaAtacada(x, y-1)
        }
        else{
            semBaixo = true
        }

        if(y < 6){
            semCima = posJaAtacada(x, y+1)
        }
        else{
            semCima = true
        }

        return semCima && semBaixo && semDireita && semEsquerda
    }

    fun posAdjacenteAcerto(): IntArray{
        var pos = IntArray(2)
        for(i in 0..6){
            for(j in 0..6){
                if(posJaAcertada(i,j)){
                    if(i > 0){
                        if(!posJaAtacada(i-1,j)){
                            pos[0] = i - 1
                            pos[1] = j
                            return pos
                        }
                    }

                    if(i < 6){
                        if(!posJaAtacada(i+1,j)){
                            pos[0] = i + 1
                            pos[1] = j
                            return pos
                        }
                    }

                    if(j > 0){
                        if(!posJaAtacada(i,j-1)){
                            pos[0] = i
                            pos[1] = j - 1
                            return pos
                        }
                    }

                    if(j < 6){
                        if(!posJaAtacada(i, j+1)){
                            pos[0] = i
                            pos[1] = j + 1
                            return pos
                        }
                    }
                }
            }
        }
        pos[0] = -1
        pos[1] = -1
        return pos
    }

}