package com.example.alunos.batalhanavalandroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_load_save.*
import java.io.File
import java.io.FileInputStream
import java.io.ObjectInputStream

class LoadSaveActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_save)

        btn_load.setOnClickListener {
            var txt = findViewById<EditText>(R.id.edit_nomeSave).text.toString()
            if(txt.isEmpty()){
                var t = Toast.makeText(this,
                        "É necessário informar o nome do seu almirante.", Toast.LENGTH_SHORT)
                t.show()
            }
            else{
                loadSave(txt)
            }
        }
    }

    fun loadSave(nome: String){
        try {
            val file = File(nome + ".ser")
            val fi = FileInputStream(file)
            val oi = ObjectInputStream(fi)
            val saveHumano = oi.readObject() as Jogador
            oi.close()
            fi.close()

            val fileBot = File(nome + ".ser")
            val fiBot = FileInputStream(fileBot)
            val oiBot = ObjectInputStream(fiBot)
            val saveBot = oiBot.readObject() as Bot
            oiBot.close()
            fiBot.close()


        }
        catch (e: Exception) {
            var t = Toast.makeText(this,
                    "Erro: Não foi possível fazer load do save.", Toast.LENGTH_SHORT)
            t.show()
        }

    }
}
