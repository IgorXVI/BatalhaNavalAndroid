package com.example.alunos.batalhanavalandroid

import android.content.Intent
import android.media.MediaPlayer
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import java.util.*
import kotlin.concurrent.schedule

abstract class JogadaActivity: TabuleiroActivity() {

    var jogador: Jogador? = null
    var mensagemFim: String? = null
    var proximaVez: Intent? = null
    var mp: MediaPlayer? = null

    fun setErro(x: Int, y: Int){
        val pos = pegarPos(x, y)

        runOnUiThread {
            pos.setImageResource(R.mipmap.agua_escura)
            pos.isClickable = false
        }
    }

    fun setAcerto(x: Int, y: Int){
        val pos = pegarPos(x, y)

        runOnUiThread {
            pos.setImageResource(R.mipmap.explosao)
            pos.isClickable = false
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
                    pos = pegarPos(i, j)
                    pos.isClickable = true
                }
            }
        }
    }

    fun setErrosAcertosTabuleiro(){
        var c: Char

        for(i in 0..tabuleiro!!.linhas-1){
            for(j in 0..tabuleiro!!.colunas-1){
                c = tabuleiro!!.tabuleiroPublico[i][j]
                if(c == 'X'){
                    setAcerto(i, j)
                }
                if(c == '*'){
                    setErro(i, j)
                }
            }
        }

    }

    fun setNumErroAcerto(){
        val lbl_acertos = findViewById<TextView>(R.id.lbl_acertos)
        val lbl_erros = findViewById<TextView>(R.id.lbl_erros)

        val textErros = "Erros: " + tabuleiro!!.erros.toString()
        val textAcertos = "Acertos: " + tabuleiro!!.acertos.toString()

        runOnUiThread {
            lbl_erros.text = textErros
            lbl_acertos.text = textAcertos
        }

    }

    fun somTorpedo(volume: Float){
        limparMp()
        mp = MediaPlayer.create(this, R.raw.ataque_som)
        mp?.setVolume(volume, volume)
        mp?.start()
    }

    fun somAcerto(volume: Float){
        limparMp()
        mp = MediaPlayer.create(this, R.raw.explosao_som)
        mp?.setVolume(volume, volume)
        mp?.start()
    }

    fun limparMp(){
        if(mp != null){
            mp?.stop()
            mp?.release()
            mp = null
        }
    }

    fun ataque(){
        if(g!!.som){
            somTorpedo(0.5F)
            mp?.setOnCompletionListener {
                setAtaque()
            }
        }
        else{
            setAtaque()
        }
    }

    fun setAtaque(){
        setNumErroAcerto()
        setErrosAcertosTabuleiro()
        if(g!!.som && jogador!!.acertou){
            somAcerto(10F)
            mp?.setOnCompletionListener {
                fimAtaque()
            }
        }
        else{
            fimAtaque()
        }
    }

    fun fimAtaque(){
        val ganhou = tabuleiro!!.todosNaviosDestruidos()
        if(ganhou){
            mensagemFim()
        }
        else if(!jogador!!.acertou){
            mudarVez()
        }
    }

    fun mensagemFim(){
        runOnUiThread{
            var t = Toast.makeText(this, mensagemFim!!, Toast.LENGTH_SHORT)
            t.show()
        }
        val intent = Intent(this, MainActivity::class.java)
        Timer().schedule(5000){
            startActivity(intent)
            finish()
        }
    }

    fun mudarVez(){
        Timer().schedule(500){
            startActivity(proximaVez!!)
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        limparMp()
    }

}