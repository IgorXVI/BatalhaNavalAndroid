package com.example.alunos.batalhanavalandroid

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import java.util.*
import kotlin.concurrent.schedule

class JogadaBotActivity : JogadaActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jogada_bot)

        setImagensTabuleiro()
        Timer().schedule(1000){
            ataque()
        }
    }

    override fun setImagensTabuleiro(){
        travarTudo()

        var c: Char
        var tamanho: Int

        for(i in 0..6){
            for(j in 0..6){
                c = g.humano.tabuleiro.tabuleiroPublico[i][j]
                if(c == 'X'){
                    setAcerto(i, j)
                }
                else if(c == '*'){
                    setErro(i, j)
                }
                else{
                    c = g.humano.tabuleiro.tabuleiroDoJogador[i][j]
                    if(c != '~'){
                        tamanho = c.toInt() - 95
                        setImagemNavio(i, j, tamanho)
                    }
                }
            }
        }
    }

    fun ataque() {
        travarMenu()

        g.bot.realizarJogada()
        setImagensTabuleiro()

        val x = g.bot.cerebro.posUltimo[0]
        val y = g.bot.cerebro.posUltimo[1]
        som(x,y, g.humano.tabuleiro)

        val ganhou = g.humano.tabuleiro.todosNaviosDestruidos()

        if(ganhou){
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

}
