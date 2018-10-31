package com.example.alunos.batalhanavalandroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionBar = supportActionBar
        actionBar!!.title = "Batalha Naval"
        actionBar.elevation = 4.0F
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.NovoJogo -> {
                val intent = Intent(this, NovoJogoActivity::class.java)
                startActivity(intent)
            }
            R.id.Load -> {
                val intent = Intent(this, LoadSaveActivity::class.java)
                startActivity(intent)
            }
            R.id.Save -> {
                val intent = Intent(this, SaveActivity::class.java)
                startActivity(intent)
            }
            R.id.Sobre -> {
                val intent = Intent(this, SobreActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
        finish()
    }
}
