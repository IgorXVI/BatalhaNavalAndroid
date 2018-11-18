package com.example.alunos.batalhanavalandroid

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton

abstract class TabuleiroActivity: JogoActivity() {

    var menuPrincipalItem: MenuItem? = null
    var sobreItem: MenuItem? = null

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        menuPrincipalItem = menu?.getItem(0)
        sobreItem = menu?.getItem(1)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.MenuPrincipal -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.Salvar -> {
                salvarArquivo()
            }
            R.id.Sobre -> {
                val intent = Intent(this, SobreActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun travarMenu(){
        menuPrincipalItem?.setEnabled(false)
        sobreItem?.setEnabled(false)
    }

    fun pegarPos(x: Int, y:Int): ImageButton {
        var id = resources.getIdentifier("pos_"+x.toString()+"_"+y.toString(),
                "id", this.packageName)
        var btn: ImageButton = findViewById(id)
        return btn
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

    fun setImagemAgua(x: Int, y: Int){
        val pos = pegarPos(x, y)
        runOnUiThread {
            pos.setImageResource(R.mipmap.agua)
        }
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

    abstract fun setImagensTabuleiro()
}