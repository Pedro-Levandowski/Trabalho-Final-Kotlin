package com.example.trabalhokotlin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var btnAdd: Button
    private lateinit var adapter: ArrayAdapter<String>

    private val listaTarefas = mutableListOf<Tarefa>()
    private lateinit var dao: TarefaDao

    private val addTaskLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val descricao = result.data?.getStringExtra("descricao")
            val detalhes = result.data?.getStringExtra("detalhes")

            if (!descricao.isNullOrBlank()) {
                val novaTarefa = Tarefa(descricao = descricao, detalhes = detalhes ?: "")
                salvarTarefa(novaTarefa)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)
        btnAdd = findViewById(R.id.btnAdd)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf())
        listView.adapter = adapter

        dao = AppDatabase.getInstance(this).tarefaDao()

        carregarTarefas()

        btnAdd.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            addTaskLauncher.launch(intent)
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            val tarefaSelecionada = listaTarefas[position]
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("tarefa_id", tarefaSelecionada.id)
            startActivity(intent)
        }
    }

    private fun carregarTarefas() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val tarefas = dao.listar()
                listaTarefas.clear()
                listaTarefas.addAll(tarefas)
                withContext(Dispatchers.Main) {
                    adapter.clear()
                    adapter.addAll(listaTarefas.map { it.descricao })
                    adapter.notifyDataSetChanged()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun salvarTarefa(tarefa: Tarefa) {
        GlobalScope.launch(Dispatchers.IO) {
            dao.inserir(tarefa)
            carregarTarefas()
        }
    }

    override fun onResume() {
        super.onResume()
        carregarTarefas()
    }
}
