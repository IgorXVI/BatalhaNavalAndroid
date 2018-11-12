package com.example.alunos.batalhanavalandroid

import android.content.Context
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class ArquivoJogo(var humano: Jogador, var bot: Bot) {
    fun salvar(context: Context): Boolean{
        try {
            val fileName = "humano.ser"
            val f = context.openFileOutput(fileName, Context.MODE_PRIVATE)
            val o = ObjectOutputStream(f)
            o.writeObject(this.humano)
            f.close()
            o.close()

            val fileNameBot = "bot.ser"
            val fBot = context.openFileOutput(fileNameBot, Context.MODE_PRIVATE)
            val oBot = ObjectOutputStream(fBot)
            oBot.writeObject(this.bot)
            fBot.close()
            oBot.close()
            return true
        }
        catch (e: Exception) {
            return false
        }
    }

    fun load(context: Context): Boolean{
        try {
            val fileName = "humano.ser"
            val fi = context.openFileInput(fileName);
            val oi = ObjectInputStream(fi)
            val saveHumano = oi.readObject() as Jogador
            oi.close()
            fi.close()

            val fileNameBot = "bot.ser"
            val fiBot = context.openFileInput(fileNameBot);
            val oiBot = ObjectInputStream(fiBot)
            val saveBot = oiBot.readObject() as Bot
            oiBot.close()
            fiBot.close()

            this.humano = saveHumano
            this.bot = saveBot

            val derrotaHumano = this.humano.tabuleiro.todosNaviosDestruidos()
            val derrotaBot = this.bot.tabuleiro.todosNaviosDestruidos()
            return derrotaHumano || derrotaBot
        }
        catch (e: Exception) {
            return false
        }
    }
}