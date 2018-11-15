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


   repetir o ciclo (primeiro -> segundo -> terceiro -> primeiro -> segundo-> terceiro-> ...)
   até alguém ganhar
    */

    var posUltimo = IntArray(2)
    private var posInicalAcerto = IntArray(2)
    private var inimigoVertical = false
    private var erro1 = false
    private var erro2 = false
    private var sentido = false
    var segundo = false
    var terceiro = false
    var tabuleiroProb = Array(7, {IntArray(7)})

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

        /*confere o resultado do último ataque, com base nele determina qual vai ser o próximo
        passo*/

        val x = posUltimo[0]
        val y = posUltimo[1]
        if(x != -1 && y != -1){
            if(terceiro){

                //aqui a função confere se o último ataque do terceiro passo resultou em erro

                if (!this.erro1) {

                    //se for o primeiro erro, inverte o sentido de ataque

                    this.erro1 = !posJaAcertada(x, y)
                    if (this.erro1) {
                        this.sentido = !this.sentido
                    }
                } else {

                    //se for o segundo erro, termina o terceiro passo

                    this.erro2 = !posJaAcertada(x, y)
                    if (this.erro2) {
                        this.reset()
                    }
                }
            }
            else if(segundo){

                /*aqui a função confere se o último ataque do segundo passo resultou em acerto,
                se sim vai para o terceiro passo*/

               this.terceiro = posJaAcertada(x, y)
               if(this.terceiro){
                   this.segundo = false
               }
            }
            else{

                /*aqui a função confere se o último ataque do primeiro passo resultou em acerto,
                se sim vai para o segundo passo*/

                this.segundo = posJaAcertada(x, y)
            }
        }
    }

    private fun terceiroPasso(){

        /*aqui a função pega a proxima posição em relação à última posição de ataque (posUltimo)
        com base no sentido de ataque*/
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

            //se a posição ja estiver errada conta como um erro do terceiro passo

            if (!this.erro1) {

                //se for o primeiro erro, inverte o sentido de ataque e recomeça o terceiro passo

                this.sentido = !this.sentido
                this.erro1 = true
                this.terceiroPasso()
            } else {

                //se for o segundo erro, termina o terceiro passo e começa o primeiro

                this.reset()
                this.primeiroPasso()
            }
        } else {
            this.posUltimo[0] = x
            this.posUltimo[1] = y
            if (posJaAcertada(x, y)){

                /*se a posição ja tiver um acerto recomeça o terceiro passo, isso vai fazer
                com que a função ignore todos os acertos já existentes na mesma linha ou coluna*/

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

            /*a função pega um acerto adjacente e determina ele como a última posição atacada
            (posUltimo), também determina o alinhamento do navio e o sentido do proximo ataque
            baseado na localização desse acerto adjacente em relação à posição inicial de acerto*/

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

            /*baseado em um alinhamento randômico e em um sentido randômico a função pega uma
            posição, que não foi atacada, adjacente à posição inicial de acerto e determina
            ela como a última posição atacada (posUltimo)*/

            while (this.posJaAtacada(x, y)) {
                this.inimigoVertical = r.nextBoolean()
                this.sentido = r.nextBoolean()
                if (!this.inimigoVertical) {
                    y = this.posInicalAcerto[1]
                    if (this.sentido) {
                        x = this.posInicalAcerto[0] + 1
                    } else {
                        x = this.posInicalAcerto[0] - 1
                    }
                } else {
                    x = this.posInicalAcerto[0]
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

        //aqui eu gero as probabilades de acerto para cada posição nessa jogada
        this.resetTabuleiroProb()
        for(i in 2..4){
            this.gerarTabuleiroProb(i)
        }

        //aqui eu pego a posição com maior probabilidade de acerto
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

    fun gerarTabuleiroProb(tamanho: Int){

        /* aqui eu uso um algoritmo de função probabilidade, inspirado nesse artigo:
        "datagenetics.com/blog/december32011" na parte de "Probability Density Functions"*/

        var erro = false
        var peso = 1

        var xFinal = tamanho - 1

        for(y in 0..6){
            for(xInicial in 0..6){
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
                xFinal++
                peso = 1
            }
            xFinal = tamanho - 1
        }

        var yFinal = tamanho - 1

        for(x in 0..6){
            for(yInicial in 0..6){
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
                yFinal++
                peso = 1
            }
            yFinal = tamanho - 1
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
            return true
        }
        val c = this.tabuleiro.tabuleiroPublico[x][y]
        return c != '~'
    }

    private fun posJaErrada(x: Int, y: Int): Boolean {
        if(x > 6 || x < 0 || y > 6 || y < 0){
            return true
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