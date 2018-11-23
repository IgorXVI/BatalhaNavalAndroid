package com.example.alunos.batalhanavalandroid

import java.io.Serializable
import android.R.attr.y
import android.R.attr.x



open class Jogador(val nome: String, val tabuleiro: Tabuleiro) : Serializable {

    var x = -1
    var y = -1
    var temBomba = true
    protected var tabuleiroAdversario: Tabuleiro? = null

    fun realizarJogada(x: Int, y: Int, tabuleiroAdversario: Tabuleiro, bomba: Boolean) {
        this.x = x
        this.y = y
        this.tabuleiroAdversario = tabuleiroAdversario
        this.atirar()

        if(bomba){
            usarBomba()
        }

    }

    protected fun atirar() {
        if (!this.tabuleiroAdversario!!.posTemNavio(x, y)) {
           this.tabuleiroAdversario!!.setErro(x, y)
        } else {
            this.tabuleiroAdversario!!.setAcerto(x, y)
        }
    }

    protected fun usarBomba(){
        this.temBomba = false
        val xo = this.x
        val yo = this.y

        val xe = this.x - 1
        val xd = this.x + 1
        val yb = this.y - 1
        val yc = this.y + 1

        if (xe > -1) {
            this.x = xe
            this.y = yo
            this.atirar()
        }
        if (xd < 7) {
            this.x = xd
            this.y = yo
            this.atirar()
        }
        if (yb > -1) {
            this.x = xo
            this.y = yb
            this.atirar()
        }
        if (yc < 7) {
            this.x = xo
            this.y = yc
            this.atirar()
        }
    }

}
