package com.example.alunos.batalhanavalandroid

import android.widget.ImageButton

abstract class TabuleiroActivity: JogoActivity() {

    var tabuleiro: Tabuleiro? = null

    fun pegarPos(x: Int, y:Int): ImageButton {
        var id = resources.getIdentifier("pos_"+x.toString()+"_"+y.toString(),
                "id", this.packageName)
        var btn: ImageButton = findViewById(id)
        return btn
    }

    fun setImagensNavios(){
        for(i in 2..4){
            setImagemNavio(i)
        }
    }

    fun setImagemNavio(tamanho: Int){
        var x: Int
        var y: Int
        val arr = tabuleiro!!.navios[tamanho]!!.posicoes
        val vertical = !tabuleiro!!.navios[tamanho]!!.vertical

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

    fun setAguaTabuleiro(tamanho: Int){
        val c = ('a'.toInt() + tamanho).toChar()

        for(i in 0..tabuleiro!!.linhas-1){
            for(j in 0..tabuleiro!!.colunas-1){
                if(tabuleiro!!.tabuleiroDoJogador[i][j] == c){
                    setImagemAgua(i, j)
                }
            }
        }
    }

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

}