package com.example.alunos.batalhanavalandroid

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Switch
import java.util.*
import kotlin.concurrent.schedule

class JogadaHumanoActivity : JogadaActivity() {

    var humano: Jogador? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jogada_humano)

        g = this.application as Global
        tabuleiro = g!!.bot!!.tabuleiro
        humano = g!!.humano!!

        setImagensNavios()
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
        humano!!.realizarJogada(x, y, tabuleiro!!, bomba)
        if(bomba){
            setBotaoBomba()
        }

        val intent = Intent(this, JogadaBotActivity::class.java)

        Timer().schedule(somTorpedo(true)){
            setNumErroAcerto()
            setErrosAcertosTabuleiro(humano!!.tiros!!)

            Timer().schedule(somTorpedo(humano!!.acertou)){
                val ganhou = tabuleiro!!.todosNaviosDestruidos()
                if(!ganhou){
                    mudarVez(intent)
                }
                else{
                    fim("Você Ganhou!")
                }
            }

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
