package com.example.alunos.batalhanavalandroid

import java.io.Serializable
import java.util.*

class Bot(nome: String, tabuleiro: Tabuleiro) : Jogador(nome, tabuleiro), Serializable {

    var tabuleiroProb: Array<IntArray>? = null

    /*Ataque : atacar uma posição randômica útil até acertar a primeira vez ou até errar 3 vezes, se
   já ouver um acerto ou 3 erros atacar a posição mais provável de conter um navio*/

    fun ataque(): IntArray{
        var x = -1
        var y = -1
        val tabAd = this.tabuleiroAdversario!!
        val acertos = tabAd.acertos
        val erros = tabAd.erros
        val linhas = tabAd.linhas
        val colunas = tabAd.colunas
        val navios = tabAd.navios

        this.tabuleiroProb = Array(linhas) {IntArray(colunas)}

        if(acertos == 0 && erros <= 3){
            //aqui a função gera uma posição randômica não atacada e não cercada
            val r = Random()
            x = r.nextInt(linhas)
            y = r.nextInt(colunas)
            while (tabAd.posInutil(x, y)) {
                x = r.nextInt(linhas)
                y = r.nextInt(colunas)
            }
        }
        else{
            /*aqui eu gero as probabilades de acerto para cada posição nessa jogada, com os navios
            que ainda não foram destruídos*/

            this.resetTabuleiroProb()

            for((key, valye) in navios){
                if(!tabAd.navioDestruido(key)){
                    this.gerarTabuleiroProb(key)
                }
            }

            //aqui eu pego a posição com maior probabilidade de acerto
            var maior = 0
            for(i in 0..linhas-1){

                for(j in 0..colunas-1){

                    if(this.tabuleiroProb!![i][j] > maior){
                        maior = this.tabuleiroProb!![i][j]
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
        val tabAd = this.tabuleiroAdversario!!
        val linhas = tabAd.linhas
        val colunas = tabAd.colunas

        var xFinal = tamanho - 1

        for(y in 0..colunas-1){
            for(xInicial in 0..linhas-1){
                for(x in xInicial..xFinal){
                    erro = tabAd.posObstaculo(x, y)
                    if(erro){
                        break
                    }
                    if(tabAd.posJaAcertada(x, y)){
                        peso += incremento
                    }
                }
                if(!erro){
                    for(x in xInicial..xFinal){
                        if(!tabAd.posJaAcertada(x, y)){
                            this.tabuleiroProb!![x][y] += peso
                        }
                    }
                }
                xFinal++
                peso = 1
            }
            xFinal = tamanho - 1
        }

        var yFinal = tamanho - 1

        for(x in 0..linhas-1){
            for(yInicial in 0..colunas-1){
                for(y in yInicial..yFinal){
                    erro = tabAd.posObstaculo(x, y)
                    if(erro){
                        break
                    }
                    if(tabAd.posJaAcertada(x, y)){
                        peso += incremento
                    }
                }
                if(!erro){
                    for(y in yInicial..yFinal){
                        if(!tabAd.posJaAcertada(x, y)){
                            this.tabuleiroProb!![x][y] += peso
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
        val tabAd = this.tabuleiroAdversario!!
        val linhas = tabAd.linhas
        val colunas = tabAd.colunas

        for(i in 0..linhas-1){

            for(j in 0..colunas-1){
                this.tabuleiroProb!![i][j] = 0
            }

        }
    }

    fun realizarJogada(tabuleiroAdversario: Tabuleiro){
        this.acertou = false
        this.tabuleiroAdversario = tabuleiroAdversario

        val arr = this.ataque()
        this.x = arr[0]
        this.y = arr[1]

        var bomba = false
        if(this.temBomba){
            val r = Random()
            bomba = r.nextBoolean()
        }

        this.atirar()
        if(bomba){
            usarBomba()
        }

    }

}
