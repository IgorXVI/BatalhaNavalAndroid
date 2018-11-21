package com.example.alunos.batalhanavalandroid

import java.util.*
import kotlin.jvm.internal.Ref
import java.io.Serializable

class AI(val tabuleiro: Tabuleiro): Serializable {

   /*Ataque : atacar uma posição randômica útil até acertar a primeira vez ou até errar 3 vezes, se
   já ouver um acerto ou 3 erros atacar a posição mais provável de conter um navio*/

    var tabuleiroProb = Array(7, {IntArray(7)})

    init {
        this.resetTabuleiroProb()
    }

    fun ataque(): IntArray{

        var x = -1
        var y = -1
        if(this.tabuleiro.acertos == 0 && this.tabuleiro.erros <= 3){
            //aqui a função gera uma posição randômica não atacada e não cercada
            val r = Random()
            x = r.nextInt(7)
            y = r.nextInt(7)
            while (this.tabuleiro.posInutil(x, y)) {
                x = r.nextInt(7)
                y = r.nextInt(7)
            }
        }
        else{
            /*aqui eu gero as probabilades de acerto para cada posição nessa jogada, com os navios
            que ainda não foram destruídos*/
            this.resetTabuleiroProb()
            for(i in 2..4){
                if(!this.tabuleiro.navioDestruido(i)){
                    this.gerarTabuleiroProb(i)
                }
            }

            //aqui eu pego a posição com maior probabilidade de acerto
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
        }

        val arr = IntArray(2)
        arr[0] = x
        arr[1] = y

        return arr
    }

    fun gerarTabuleiroProb(tamanho: Int){

        /* aqui eu uso um algoritmo de função probabilidade, inspirado nesse artigo:
        "datagenetics.com/blog/december32011" na parte de "Probability Density Functions"*/

        var erro = false
        var peso = 1
        val incremento = 1000

        var xFinal = tamanho - 1

        for(y in 0..6){
            for(xInicial in 0..6){
                for(x in xInicial..xFinal){
                    erro = this.tabuleiro.posObstaculo(x, y)
                    if(erro){
                        break
                    }
                    if(this.tabuleiro.posJaAcertada(x, y)){
                        peso += incremento
                    }
                }
                if(!erro){
                    for(x in xInicial..xFinal){
                        if(!this.tabuleiro.posJaAcertada(x, y)){
                            this.tabuleiroProb[x][y] += peso
                        }
                    }
                }
                xFinal++
                peso = 1
            }
            xFinal = tamanho - 1
        }

        var yFinal = tamanho - 1

        for(x in 0..6){
            for(yInicial in 0..6){
                for(y in yInicial..yFinal){
                    erro = this.tabuleiro.posObstaculo(x, y)
                    if(erro){
                        break
                    }
                    if(this.tabuleiro.posJaAcertada(x, y)){
                        peso += incremento
                    }
                }
                if(!erro){
                    for(y in yInicial..yFinal){
                        if(!this.tabuleiro.posJaAcertada(x, y)){
                            this.tabuleiroProb[x][y] += peso
                        }
                    }
                }
                yFinal++
                peso = 1
            }
            yFinal = tamanho - 1
        }

    }

    fun resetTabuleiroProb(){
        for(i in 0..6){

            for(j in 0..6){
                this.tabuleiroProb[i][j] = 0
            }

        }
    }

}