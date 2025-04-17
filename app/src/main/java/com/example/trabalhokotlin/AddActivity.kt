package com.example.trabalhokotlin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        val editDescricao = findViewById<EditText>(R.id.editTextDescricao)
        val editDetalhes = findViewById<EditText>(R.id.editTextDetalhes)
        val btnSalvar = findViewById<Button>(R.id.btnSalvar)

        btnSalvar.setOnClickListener {
            val descricao = editDescricao.text.toString()
            val detalhes = editDetalhes.text.toString()

            val resultIntent = Intent().apply {
                putExtra("descricao", descricao)
                putExtra("detalhes", detalhes)
            }

            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}
