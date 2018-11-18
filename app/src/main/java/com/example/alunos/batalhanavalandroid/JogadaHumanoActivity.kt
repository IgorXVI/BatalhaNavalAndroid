package com.example.alunos.batalhanavalandroid

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast

class JogadaHumanoActivity : JogadaActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jogada_humano)

        setImagensTabuleiro()
    }

    override fun setImagensTabuleiro(){
        var c: Char
        for(i in 0..6){
            for(j in 0..6){
                c = g.bot.tabuleiro.tabuleiroPublico[i][j]
                if(c == 'X'){
                    setAcerto(i, j)
                }
                if(c == '*'){
                    setErro(i, j)
                }
            }
        }
    }

    fun ataque(view: View){
        travarTudo()
        travarMenu()

        var nome = resources.getResourceEntryName(view.id)
        var x = nome[4].toInt() - 48
        var y = nome[6].toInt() - 48

        g.humano.realizarJogada(x, y, g.bot)
        setImagensTabuleiro()
        som(x, y, g.bot.tabuleiro)

        val ganhou = g.bot.tabuleiro.todosNaviosDestruidos()

        if(ganhou){

            runOnUiThread {
                var t = Toast.makeText(this, "Você Ganhou!", Toast.LENGTH_SHORT)
                t.show()
            }

            val intent =  Intent()
            mudarActivity(intent)
        }
        else{
            val intent = Intent(this, JogadaBotActivity::class.java)
            mudarActivity(intent)
        }
    }

}
