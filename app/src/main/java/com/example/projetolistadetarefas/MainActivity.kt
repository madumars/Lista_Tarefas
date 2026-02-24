package com.example.projetolistadetarefas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var etTarefa: EditText
    private lateinit var btnAdicionar: Button
    private lateinit var recyclerTarefas: RecyclerView

    private val listaTarefas = mutableListOf<Tarefa>()
    private lateinit var adapter: TarefaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etTarefa = findViewById(R.id.etTarefa)
        btnAdicionar = findViewById(R.id.btnAdicionar)
        recyclerTarefas = findViewById(R.id.recyclerTarefas)

        // Configura o Adapter com a lista e a ação de clique para abrir os detalhes
        adapter = TarefaAdapter(listaTarefas) { tarefa ->
            val intent = Intent(this, DetalheActivity::class.java)
            intent.putExtra("descricao", tarefa.descricao)
            startActivity(intent)
        }

        recyclerTarefas.layoutManager = LinearLayoutManager(this)
        recyclerTarefas.adapter = adapter

        btnAdicionar.setOnClickListener {
            val texto = etTarefa.text.toString().trim()

            if (texto.isNotEmpty()) {
                listaTarefas.add(Tarefa(texto, false))
                adapter.notifyItemInserted(listaTarefas.size - 1)
                etTarefa.text.clear()
            } else {
                Toast.makeText(this, "Digite uma tarefa!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
