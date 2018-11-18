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
        runOnUiThread {
            menuPrincipalItem?.setEnabled(false)
            sobreItem?.setEnabled(false)
        }
    }

    fun pegarPos(x: Int, y:Int): ImageButton {
        var id = resources.getIdentifier("pos_"+x.toString()+"_"+y.toString(),
                "id", this.packageName)
        var btn: ImageButton = findViewById(id)
        return btn
    }

    fun setImagemNavio(x: Int, y: Int, tamanho: Int){
        val arr = g.humano.tabuleiro.getNavio(tamanho).posicoes
        val vertical = !g.humano.tabuleiro.getNavio(tamanho).vertical
        var x: Int
        var y: Int

        runOnUiThread {
            if(tamanho == 2){
                x = arr[0][0]
                y = arr[0][1]
                val posI = pegarPos(x, y)

                x = arr[1][0]
                y = arr[1][1]
                val posF = pegarPos(x, y)

                if(vertical){
                    posI.setImageResource(R.mipmap.cvi)
                    posF.setImageResource(R.mipmap.cvf)
                }
                else{
                    posI.setImageResource(R.mipmap.chi)
                    posF.setImageResource(R.mipmap.chf)
                }
            }
            else if(tamanho == 3){
                x = arr[0][0]
                y = arr[0][1]
                val posI = pegarPos(x, y)

                x = arr[1][0]
                y = arr[1][1]
                val posM = pegarPos(x, y)

                x = arr[2][0]
                y = arr[2][1]
                val posF = pegarPos(x, y)

                if(vertical){
                    posI.setImageResource(R.mipmap.evi)
                    posM.setImageResource(R.mipmap.evm)
                    posF.setImageResource(R.mipmap.evf)
                }
                else{
                    posI.setImageResource(R.mipmap.ehi)
                    posM.setImageResource(R.mipmap.ehm)
                    posF.setImageResource(R.mipmap.ehf)
                }
            }
            else if(tamanho == 4){
                x = arr[0][0]
                y = arr[0][1]
                val posI = pegarPos(x, y)

                x = arr[1][0]
                y = arr[1][1]
                val posMI = pegarPos(x, y)

                x = arr[2][0]
                y = arr[2][1]
                val posMF = pegarPos(x,y)

                x = arr[3][0]
                y = arr[3][1]
                val posF = pegarPos(x, y)

                if(vertical){
                    posI.setImageResource(R.mipmap.pvi)
                    posMI.setImageResource(R.mipmap.pvmi)
                    posMF.setImageResource(R.mipmap.pvmf)
                    posF.setImageResource(R.mipmap.pvf)
                }
                else{
                    posI.setImageResource(R.mipmap.phi)
                    posMI.setImageResource(R.mipmap.phmi)
                    posMF.setImageResource(R.mipmap.phmf)
                    posF.setImageResource(R.mipmap.phf)
                }
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