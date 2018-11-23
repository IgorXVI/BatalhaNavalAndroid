package com.example.alunos.batalhanavalandroid

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.Switch
import kotlinx.android.synthetic.main.activity_jogada_humano.*

class JogadaHumanoActivity : JogadaActivity() {

    var humano: Jogador? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jogada_humano)

        g = this.application as Global
        tabuleiro = g!!.bot!!.tabuleiro
        humano = g!!.humano!!

        setBotaoBomba()
        setNumErroAcerto()
        setErrosAcertosTabuleiro()
    }

    fun ataqueHumano(view: View){
        travarTudo()
        travarMenu()

        val nome = resources.getResourceEntryName(view.id)
        val x = nome[4].toInt() - 48
        val y = nome[6].toInt() - 48

        val bomba = findViewById<Switch>(R.id.switch_bomba).isChecked

        humano!!.realizarJogada(x, y, tabuleiro!!, bomba)

        if(bomba){
            setBotaoBomba()
        }

        val ganhou = ataque(x, y)

        if(ganhou){
            mensagemFim("Você Ganhou!")
        }
        else{
            mudarVez("Bot")
        }
    }

    fun setBotaoBomba(){

        runOnUiThread {
            val sBomba = findViewById<Switch>(R.id.switch_bomba)
            sBomba.isChecked = false
            sBomba.isClickable = humano!!.temBomba
            if(!sBomba.isClickable){
                sBomba.setTextColor(Color.parseColor("#ffcc0000"))
            }
        }

    }


}
