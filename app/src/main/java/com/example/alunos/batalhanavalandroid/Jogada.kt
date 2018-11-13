package com.example.alunos.batalhanavalandroid

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.Toast
import java.io.ObjectOutputStream
import java.util.*
import kotlin.concurrent.schedule

open class Jogada: AppCompatActivity() {

    val g = Global.getInstance()
    var mp: MediaPlayer? = null

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.MenuPrincipal -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.Sobre -> {
                val intent = Intent(this, SobreActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
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

    fun setImagensTabuleiro(tabuleiro: Tabuleiro){
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

    fun som(x: Int, y: Int, tabuleiro: Tabuleiro){
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

    fun mudarActivity(intent: Intent){
        Timer().schedule(3100){
            startActivity(intent)
            finish()
        }
    }

    fun salvar(){
        val salvou = salvarArquivo()
        if(!salvou){
            menssagemErroSave()
        }
    }

    fun menssagemErroSave(){

        runOnUiThread {
            val text = Toast.makeText(this, "Não foi possível salvar o jogo.",
                    Toast.LENGTH_SHORT)
            text.show()
        }

    }

    fun salvarArquivo(): Boolean{
        try {
            val fileName = "humano.ser"
            val f = openFileOutput(fileName, Context.MODE_PRIVATE)
            val o = ObjectOutputStream(f)
            o.writeObject(g.humano)
            f.close()
            o.close()

            val fileNameBot = "bot.ser"
            val fBot = openFileOutput(fileNameBot, Context.MODE_PRIVATE)
            val oBot = ObjectOutputStream(fBot)
            oBot.writeObject(g.bot)
            fBot.close()
            oBot.close()
            return true
        }
        catch (e: Exception) {
            return false
        }
    }

}