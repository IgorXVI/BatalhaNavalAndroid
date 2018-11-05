package com.example.alunos.batalhanavalandroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_jogada_humano.*
import java.io.File
import java.io.FileOutputStream
import java.io.ObjectOutputStream
import java.util.*
import kotlin.concurrent.schedule

class JogadaHumanoActivity : AppCompatActivity() {

    val g = Global.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jogada_humano)

        setImagensTabuleiro()

        if(!g.humano.temBomba){
            travarBomba()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.Save -> {
                salvar()
            }
            R.id.SeusNavios -> {
                val intent = Intent(this, NaviosHumanoActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.MainMenu -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun salvar(){
        try {
            val s = g.humano.nome.toString()

            val file = File(s + ".ser")
            val f = FileOutputStream(file)
            val o = ObjectOutputStream(f)
            o.writeObject(g.humano)
            f.close()
            o.close()

            val fileBot = File(s + "Bot.ser")
            val fBot = FileOutputStream(fileBot)
            val oBot = ObjectOutputStream(fBot)
            oBot.writeObject(g.bot)
            fBot.close()
            oBot.close()

            var t = Toast.makeText(this,
                    "Jogo salvo com sucesso!", Toast.LENGTH_SHORT)
            t.show()
        }
        catch (e: Exception) {
            var t = Toast.makeText(this,
                    "Erro: Não foi possível salvar o jogo.", Toast.LENGTH_SHORT)
            t.show()
        }

    }

    fun pegarPos(x: Int, y:Int): ImageButton{
        var id = resources.getIdentifier("pos_"+x.toString()+"_"+y.toString(),
                "id", this.packageName)
        var btn: ImageButton = findViewById(id)
        return btn
    }

    fun travarTudo(){
        var pos:ImageButton
        runOnUiThread{
            for(i in 0..6){
                for(j in 0..6){
                    pos = pegarPos(i, j)
                    pos.isClickable = false
                }
            }
        }
    }

    fun destravarTudo(){
        var pos:ImageButton
        runOnUiThread{
            for(i in 0..6){
                for(j in 0..6){
                    pos = pegarPos(i, j)
                    pos.isClickable = true
                }
            }
        }
    }

    fun setErro(x: Int, y: Int){
        val pos = pegarPos(x, y)
        runOnUiThread {
            pos.setImageResource(R.mipmap.espuma)
            pos.isClickable = false;
        }
    }

    fun setAcerto(x: Int, y: Int){
        val pos = pegarPos(x, y)
        runOnUiThread {
            pos.setImageResource(R.mipmap.explosao)
            pos.isClickable = false;
        }
    }

    fun setImagensTabuleiro(){
        val tabuleiro = g.bot.tabuleiro
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

    fun ataque(view: View) {
        travarTudo()
        var nome = resources.getResourceEntryName(view.id)
        var x = nome[4].toInt() - 48
        var y = nome[6].toInt() - 48

        var bomba = false
        if(btn_bomba.isChecked){
            travarBomba()
            bomba = true
        }

        g.humano.realizarJogada(x, y, g.bot, bomba)
        setImagensTabuleiro()

        val ganhou = g.bot.tabuleiro.todosNaviosDestruidos()

        if(ganhou){
            g.comecou = false

            runOnUiThread {
                var t = Toast.makeText(this, "Você Ganhou!", Toast.LENGTH_SHORT)
                t.show()
            }

            val intent =  Intent(this, MainActivity::class.java)

            Timer().schedule(2000){
                startActivity(intent)
                finish()
            }
        }
        else{
            val intent = Intent(this, JogadaBotActivity::class.java)

            Timer().schedule(1000){
                startActivity(intent)
                finish()
            }
        }
    }

    fun travarBomba(){
        runOnUiThread {
            btn_bomba.isClickable = false
            btn_bomba.isChecked = false
        }
    }
}
