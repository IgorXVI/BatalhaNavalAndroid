package com.example.alunos.batalhanavalandroid

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import java.util.*
import kotlin.concurrent.schedule

class JogadaBotActivity : Jogada() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jogada_bot)

        setImagensTabuleiro(g.humano.tabuleiro)
        travarTudo()
        Timer().schedule(1000){
            ataque()
        }
    }

    override fun setImagensTabuleiro(tabuleiro: Tabuleiro){
        var c: Char
        var tamanho: Int

        for(i in 0..6){
            for(j in 0..6){
                c = g.humano.tabuleiro.tabuleiroDoJogador[i][j]
                if(c != '~'){
                    tamanho = c.toInt() - 95
                    setImagemNavio(i, j, tamanho)
                }

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

    fun setImagemNavio(x: Int, y: Int, tamanho: Int){
        val pos = pegarPos(x, y)
        runOnUiThread {
            if(tamanho == 2){
                pos.setImageResource(R.mipmap.cruzador)
            }
            else if(tamanho == 3){
                pos.setImageResource(R.mipmap.encouracado)
            }
            else if(tamanho == 4){
                pos.setImageResource(R.mipmap.porta_avioes)
            }
        }
    }

    fun ataque() {
        g.bot.realizarJogada()
        salvarArquivo()
        setImagensTabuleiro(g.humano.tabuleiro)

        val x = g.bot.cerebro.posUltimo[0]
        val y = g.bot.cerebro.posUltimo[1]
        //som(x,y, g.humano.tabuleiro)

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
