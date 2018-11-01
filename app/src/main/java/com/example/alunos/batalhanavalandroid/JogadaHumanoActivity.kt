package com.example.alunos.batalhanavalandroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream
import java.io.ObjectOutputStream

class JogadaHumanoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jogada_humano)

        val actionBar = supportActionBar
        actionBar!!.title = "Sua Vez"
        actionBar.elevation = 4.0F
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.Save -> {
                val intent = Intent(this, SaveActivity::class.java)
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
            val intent = Intent(this, MainActivity::class.java)
            val s = intent.getSerializableExtra("humano")
            val file = File(s + ".ser")
            val f = FileOutputStream(file)
            val o = ObjectOutputStream(f)
            o.writeObject(this)
            f.close()
            o.close()
            var t = Toast.makeText(this, "Jogo salvo com sucesso!", Toast.LENGTH_SHORT)
            t.show()
        } catch (e: Exception) {
            var t = Toast.makeText(this,
                    "Erro: Não foi possível salvar o jogo.", Toast.LENGTH_SHORT)
            t.show()
        }

    }
}
