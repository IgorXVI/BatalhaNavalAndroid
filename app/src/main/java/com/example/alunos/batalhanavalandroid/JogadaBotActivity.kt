package com.example.alunos.batalhanavalandroid

import android.content.Intent
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_jogada_humano.*
import java.util.*
import kotlin.concurrent.schedule

class JogadaBotActivity : AppCompatActivity() {

    val g = Global.getInstance()
    var mp: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jogada_bot)

        setImagensTabuleiro()

        travarTudo()

        Timer().schedule(500){
            ataque()
        }
    }

    fun pegarPos(x: Int, y:Int): ImageButton {
        var id = resources.getIdentifier("pos_"+x.toString()+"_"+y.toString(),
                "id", this.packageName)
        var btn: ImageButton = findViewById(id)
        return btn
    }

    fun travarTudo(){
        var pos: ImageButton
        runOnUiThread{
            for(i in 0..6){
                for(j in 0..6){
                    pos = pegarPos(i, j)
                    pos.isClickable = false
                }
            }
        }
    }

    fun setErro(x: Int, y: Int){
        val pos = pegarPos(x, y)
        runOnUiThread {
            pos.setImageResource(R.mipmap.espuma)
        }
    }

    fun setAcerto(x: Int, y: Int){
        val pos = pegarPos(x, y)
        runOnUiThread {
            pos.setImageResource(R.mipmap.explosao)
        }
    }

    fun setImagensTabuleiro(){
        val tabuleiro = g.humano.tabuleiro
        var c: Char?
        for(i in 0..6){
            for(j in 0..6){
                c = tabuleiro.tabuleiroPublico[i][j]
                if(c == 'X'){
                    setAcerto(i, j)
                }
                if(c == '*'){
                    setErro(i, j)
                }
            }
        }
    }

    fun ataque() {
        g.bot.realizarJogada(g.humano)
        setImagensTabuleiro()
        val x = g.bot.posUltimo[0]
        val y = g.bot.posUltimo[1]

        som(x,y)

        val ganhou = g.humano.tabuleiro.todosNaviosDestruidos()

        if(ganhou){
            g.comecou = false

            runOnUiThread{
                var t = Toast.makeText(this, "Você Perdeu!", Toast.LENGTH_SHORT)
                t.show()
            }

            val intent =  Intent(this, MainActivity::class.java)
            mudarActivity(intent)
        }
        else{
            val intent = Intent(this, JogadaHumanoActivity::class.java)
            mudarActivity(intent)
        }
    }

    fun som(x: Int, y: Int){
        val acertou = g.humano.tabuleiro.tabuleiroPublico[x][y] == 'X'
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

    fun mudarActivity(intent: Intent){
        Timer().schedule(3000){
            startActivity(intent)
            finish()
        }
    }
}
