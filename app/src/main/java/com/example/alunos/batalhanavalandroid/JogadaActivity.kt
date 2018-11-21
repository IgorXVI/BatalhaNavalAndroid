package com.example.alunos.batalhanavalandroid

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import java.util.*
import kotlin.concurrent.schedule

class JogadaActivity: TabuleiroActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jogada_humano)
    }

    var mp: MediaPlayer? = null

    fun setNumErroAcerto(tabuleiro: Tabuleiro){
        val lbl_acertos = findViewById<TextView>(R.id.lbl_acertos)
        val lbl_erros = findViewById<TextView>(R.id.lbl_erros)

        val textErros = "Erros: " + tabuleiro.erros.toString()
        val textAcertos = "Acertos: " + tabuleiro.acertos.toString()

        runOnUiThread {
            lbl_erros.text = textErros
            lbl_acertos.text = textAcertos
        }

    }

    fun som(x: Int, y: Int, tabuleiro: Tabuleiro){
        if(g.som){
            var acertou = !(x == -1 && y == -1)
            if(acertou){
                acertou = tabuleiro.tabuleiroPublico[x][y] == 'X'
            }

            if(acertou){
                mp = MediaPlayer.create(this, R.raw.explosao_som)
            }
            else{
                mp = MediaPlayer.create(this, R.raw.espuma_som)
            }
            mp?.start()
            mp?.setOnCompletionListener {
                mp?.release()
            }
        }
    }

    fun setErroAcerto(tabuleiro: Tabuleiro){
        setImagensErroAcerto(tabuleiro)
        setNumErroAcerto(tabuleiro)
    }

    open fun ataque(x: Int, y: Int, tabuleiro: Tabuleiro): Boolean{
        som(x,y, tabuleiro)
        setErroAcerto(tabuleiro)
        return tabuleiro.todosNaviosDestruidos()
    }

    fun ataqueHumano(view: View){
        travarTudo()

        val nome = resources.getResourceEntryName(view.id)
        val x = nome[4].toInt() - 48
        val y = nome[6].toInt() - 48
        g.humano.realizarJogada(x, y, g.bot)
        val ganhou = ataque(x, y, g.bot.tabuleiro)

        if(ganhou){
            mensagemFim("Você Ganhou!")
        }
        else{
            mudarVez("Bot")
        }
    }

    fun ataqueBot() {
        val arr = g.bot.realizarJogada()
        val ganhou = ataque(arr[0], arr[1], g.humano.tabuleiro)

        if(ganhou){
            desTravarMenu()
            mensagemFim("Você Perdeu!")
        }
        else{
            mudarVez("Humano")
        }
    }

    fun delay(): Long{
        val delay: Long
        if(somItem?.isChecked!!){
            delay = 3100
        }
        else{
            delay = 500
        }
        return delay
    }

    fun mudarVez(deQuem: String){
        Timer().schedule(delay()){
            if(deQuem == "Humano"){
                vezHumano()
            }
            else{
                vezBot()
            }
        }
    }

    fun vezHumano(){
        setContentView(R.layout.activity_jogada_humano)

        actionBar.title = "Sua Vez"
        setErroAcerto(g.bot.tabuleiro)
        desTravarTudo()
        desTravarMenu()
    }

    fun vezBot(){
        setContentView(R.layout.activity_jogada_bot)

       actionBar.title = "Vez do Bot"
        travarMenu()
        travarTudo()
        setImagensNavios(g.humano.tabuleiro)
        setErroAcerto(g.humano.tabuleiro)
        Timer().schedule(500){
            ataqueBot()
        }
    }

    fun mensagemFim(mensagem: String){
        salvarItem?.setEnabled(false)

        runOnUiThread{
            var t = Toast.makeText(this, mensagem, Toast.LENGTH_SHORT)
            t.show()
        }
    }

}