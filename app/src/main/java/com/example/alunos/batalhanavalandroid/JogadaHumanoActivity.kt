package com.example.alunos.batalhanavalandroid

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Switch

class JogadaHumanoActivity : JogadaActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jogada_humano)

        g = this.application as Global
        tabuleiro = g!!.bot!!.tabuleiro
        jogador = g!!.humano
        proximaVez = Intent(this, JogadaBotActivity::class.java)
        mensagemFim = "Você Ganhou!"

        setErrosAcertosTabuleiro()
        setNumErroAcerto()
        setBotaoBomba()
    }

    fun ataqueHumano(view: View){
        travarTudo()
        travarMenu()
        val nome = resources.getResourceEntryName(view.id)
        val x = nome[4].toInt() - 48
        val y = nome[6].toInt() - 48

        val bomba = findViewById<Switch>(R.id.switch_bomba).isChecked
        g?.humano?.realizarJogada(x, y, tabuleiro!!, bomba)
        if(bomba){
            setBotaoBomba()
        }

        ataque()

        if(!tabuleiro!!.todosNaviosDestruidos() && g!!.humano!!.acertou){
            desTravarTudo()
            desTravarMenu()
        }
    }

    override fun fimAtaque() {
        val ganhou = tabuleiro!!.todosNaviosDestruidos()
        if(ganhou){
            mensagemFim()
        }
        else if(!jogador!!.acertou){
            mudarVez()
        }
        else{
            desTravarTudo()
            desTravarMenu()
        }
    }

    fun setBotaoBomba(){

        runOnUiThread {
            val sBomba = findViewById<Switch>(R.id.switch_bomba)
            sBomba.isChecked = false
            sBomba.isClickable = g!!.humano!!.temBomba
            if(!sBomba.isClickable){
                sBomba.setTextColor(Color.parseColor("#ffcc0000"))
            }
        }

    }

    fun travarTudo(){
        var pos: ImageButton
        runOnUiThread{
            for(i in 0..tabuleiro!!.linhas-1){
                for(j in 0..tabuleiro!!.colunas-1){
                    pos = pegarPos(i, j)
                    pos.isClickable = false
                }
            }
        }
    }

    fun desTravarTudo(){
        var pos: ImageButton
        runOnUiThread{
            for(i in 0..tabuleiro!!.linhas-1){
                for(j in 0..tabuleiro!!.colunas-1){
                    if(!tabuleiro!!.posJaAtacada(i, j)){
                        pos = pegarPos(i, j)
                        pos.isClickable = true
                    }
                }
            }
        }
    }


}
