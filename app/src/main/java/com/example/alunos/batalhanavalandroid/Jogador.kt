package com.example.alunos.batalhanavalandroid

import java.io.Serializable
import android.R.attr.y
import android.R.attr.x



open class Jogador(val nome: String, val tabuleiro: Tabuleiro) : Serializable {

    private var x = -1
    private var y = -1
    private var adversario: Jogador? = null
    var temBomba = true

    fun realizarJogada(x: Int, y: Int, adversario: Jogador, bomba: Boolean) {
        this.x = x
        this.y = y
        this.adversario = adversario
        this.atirar()

        if(bomba){
            bomba()
        }

    }

    private fun atirar() {
        if (!this.adversario!!.tabuleiro.posTemNavio(x, y)) {
           this. adversario!!.tabuleiro.setErro(x, y)
        } else {
            this.adversario!!.tabuleiro.setAcerto(x, y)
        }
    }

    private fun bomba(){
        this.temBomba = false
        val xe = this.x - 1
        val xd = this.x + 1
        val yb = this.y - 1
        val yc = this.y + 1
        if (xe > -1) {
            this.x = xe
            this.atirar()
        }
        if (xd < 7) {
            this.x = xd
            this.atirar()
        }
        if (yb > -1) {
            this.y = yb
            this.atirar()
        }
        if (yc < 7) {
            this.y = yc
            this.atirar()
        }
    }

}
