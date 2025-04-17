package com.example.trabalhokotlin

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

class DetailActivity : AppCompatActivity() {

    private lateinit var dao: TarefaDao
    private var tarefaId: Int = -1
    private lateinit var editText: EditText
    private lateinit var btnSalvar: Button
    private lateinit var btnVoltar: Button
    private lateinit var btnExcluir: Button
    private lateinit var tarefaAtual: Tarefa

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        editText = findViewById(R.id.editTextDetalhes)
        btnSalvar = findViewById(R.id.btnSalvarDetalhes)
        btnVoltar = findViewById(R.id.btnVoltar)
        btnExcluir = findViewById(R.id.btnExcluir)

        tarefaId = intent.getIntExtra("tarefa_id", -1)
        dao = AppDatabase.getInstance(this).tarefaDao()

        if (tarefaId != -1) {
            GlobalScope.launch(Dispatchers.IO) {
                val tarefa = dao.listar().find { it.id == tarefaId }
                tarefa?.let {
                    tarefaAtual = it
                    withContext(Dispatchers.Main) {
                        editText.setText(it.detalhes)
                    }

                    btnSalvar.setOnClickListener {
                        val novosDetalhes = editText.text.toString()
                        val tarefaAtualizada = tarefaAtual.copy(detalhes = novosDetalhes)

                        GlobalScope.launch(Dispatchers.IO) {
                            dao.atualizar(tarefaAtualizada)
                            withContext(Dispatchers.Main) {
                                Toast.makeText(this@DetailActivity, "Detalhes salvos", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    btnExcluir.setOnClickListener {
                        GlobalScope.launch(Dispatchers.IO) {
                            dao.deletar(tarefaAtual)
                            withContext(Dispatchers.Main) {
                                Toast.makeText(this@DetailActivity, "Tarefa excluída", Toast.LENGTH_SHORT).show()
                                finish() // volta pro menu
                            }
                        }
                    }
                }
            }
        }

        btnVoltar.setOnClickListener {
            finish()
        }
    }
}
