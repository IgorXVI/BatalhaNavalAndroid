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
    private var segundo = false
    private var terceiro = false

    init {
        this.posUltimo[0] = -1
        this.posUltimo[1] = -1
    }

    fun ataque(): IntArray{
        checkAcerto()
        if(terceiro){
            this.terceiroPasso()
        }
        else if(segundo){
            this.segundoPasso()
        }
        else{
            this.primeiroPasso()
        }
        return this.posUltimo
    }

    private fun checkAcerto(){
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
                if(this.segundo){
                    this.posInicalAcerto = posUltimo
                }
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

        if (x > 6 || x < 0 || y > 6 || y < 0) {
            if (!this.erro1) {
                this.sentido = !this.sentido
                this.erro1 = true
                this.terceiroPasso()
            } else {
                this.reset()
                this.primeiroPasso()
            }
        } else if (posJaErrada(x, y)) {
            if (!this.erro1) {
                this.sentido = !this.sentido
                this.erro1 = true
                this.terceiroPasso()
            } else {
                this.reset()
                this.primeiroPasso()
            }
        } else if (posJaAcertada(x, y)) {
            val arr = IntArray(2)
            arr[0] = x
            arr[1] = y
            this.posUltimo = arr
            this.terceiroPasso()
        } else {
            this.posUltimo[0] = x
            this.posUltimo[1] = y
        }
    }

    private fun segundoPasso(){
        var x: Int
        var y: Int
        val r = Random()
        x = this.posInicalAcerto[0]
        y = this.posInicalAcerto[1]
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

    private fun primeiroPasso(){
        var x: Int
        var y: Int

        x = this.posAdjacenteAcerto()[0]
        y = this.posAdjacenteAcerto()[1]

        if(x == -1 && y == -1){
            val r = Random()
            x = r.nextInt(7)
            y = r.nextInt(7)
            while (this.posInutil(x, y)) {
                x = r.nextInt(7)
                y = r.nextInt(7)
            }
        }

        this.posUltimo[0] = x
        this.posUltimo[1] = y
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
    }

    private fun posInutil(x: Int, y: Int): Boolean{
        var cima: Boolean
        var baixo: Boolean
        var esquerda: Boolean
        var direita: Boolean

        if(x > 0){
            esquerda = !posJaAtacada(x-1, y)
        }
        else{
            esquerda = false
        }

        if(x < 6){
            direita = !posJaAtacada(x+1, y)
        }
        else{
            direita = false;
        }

        if(y > 0){
            baixo = !posJaAtacada(x, y-1);
        }
        else{
            baixo = false;
        }

        if(y < 6){
            cima = !posJaAtacada(x, y+1);
        }
        else{
            cima = false;
        }

        return !cima && !baixo && !direita && !esquerda;
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