package com.example.alunos.batalhanavalandroid

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import android.widget.ToggleButton
import kotlinx.android.synthetic.main.activity_jogada_humano.*
import java.io.ObjectOutputStream
import java.util.*
import kotlin.concurrent.schedule

class JogadaHumanoActivity : AppCompatActivity() {

    val g = Global.getInstance()
    var mp: MediaPlayer? = null
    var menuJogada: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jogada_humano)

        setImagensTabuleiro()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        menuJogada = menu
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

            val fileName = s + ".ser"
            val f = openFileOutput(fileName, Context.MODE_PRIVATE);
            val o = ObjectOutputStream(f)
            o.writeObject(g.humano)
            f.close()
            o.close()

            val fileNameBot = s + "Bot.ser"
            val fBot = openFileOutput(fileNameBot, Context.MODE_PRIVATE);
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

        g.humano.realizarJogada(x, y, g.bot)
        setImagensTabuleiro()
        som(x, y)

        val ganhou = g.bot.tabuleiro.todosNaviosDestruidos()

        if(ganhou){
            g.comecou = false

            runOnUiThread {
                var t = Toast.makeText(this, "Você Ganhou!", Toast.LENGTH_SHORT)
                t.show()
            }

            val intent =  Intent(this, MainActivity::class.java)
            mudarActivity(intent)
        }
        else{
            val intent = Intent(this, JogadaBotActivity::class.java)
            mudarActivity(intent)
        }
    }

    fun som(x: Int, y: Int){
        val acertou = g.bot.tabuleiro.tabuleiroPublico[x][y] == 'X'
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
        menuJogada?.getItem(R.id.SeusNavios)?.setEnabled(false)
        menuJogada?.getItem(R.id.Save)?.setEnabled(false)
        menuJogada?.getItem(R.id.MainMenu)?.setEnabled(false)
        Timer().schedule(3000){
            startActivity(intent)
            finish()
        }
    }
}
