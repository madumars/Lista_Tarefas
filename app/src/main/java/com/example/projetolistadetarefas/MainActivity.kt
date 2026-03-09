package com.example.projetolistadetarefas

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private lateinit var etTarefa: EditText
    private lateinit var btnAdicionar: Button
    private lateinit var recyclerTarefas: RecyclerView
    private lateinit var recyclerConcluidas: RecyclerView

    private val listaAtivas = mutableListOf<Tarefa>()
    private val listaConcluidas = mutableListOf<Tarefa>()
    
    private lateinit var adapterAtivas: TarefaAdapter
    private lateinit var adapterConcluidas: TarefaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            setContentView(R.layout.activity_main)
            Log.d(TAG, "onCreate: Layout configurado com sucesso")

            etTarefa = findViewById(R.id.etTarefa)
            btnAdicionar = findViewById(R.id.btnAdicionar)
            recyclerTarefas = findViewById(R.id.recyclerTarefas)
            recyclerConcluidas = findViewById(R.id.recyclerConcluidas)

            setupAdapters()
            setupRecyclerViews()
            setupListeners()
        //erro ao iniciar app
        } catch (e: Exception) {
            Log.e(TAG, "Error in onCreate: ${e.message}", e)
            Toast.makeText(this, "Erro ao iniciar aplicativo", Toast.LENGTH_LONG).show()
        }
    }

    private fun setupAdapters() {
        adapterAtivas = TarefaAdapter(
            lista = listaAtivas, 
            onClick = { tarefa -> abrirDetalhes(tarefa) },
            onStatusChange = { tarefa -> moverParaConcluidas(tarefa) },
            onDelete = { tarefa -> deletarTarefa(tarefa, listaAtivas, adapterAtivas) }
        )

        adapterConcluidas = TarefaAdapter(
            lista = listaConcluidas,
            onClick = { tarefa -> abrirDetalhes(tarefa) },
            onStatusChange = { tarefa -> moverParaAtivas(tarefa) },
            onDelete = { tarefa -> deletarTarefa(tarefa, listaConcluidas, adapterConcluidas) }
        )
    }

    private fun setupRecyclerViews() {
        recyclerTarefas.layoutManager = LinearLayoutManager(this)
        recyclerTarefas.adapter = adapterAtivas

        recyclerConcluidas.layoutManager = LinearLayoutManager(this)
        recyclerConcluidas.adapter = adapterConcluidas
    }

    private fun setupListeners() {
        btnAdicionar.setOnClickListener {
            try {
                val texto = etTarefa.text.toString().trim()
                if (texto.isNotEmpty()) {
                    val novaTarefa = Tarefa(texto, false)
                    listaAtivas.add(novaTarefa)
                    adapterAtivas.notifyItemInserted(listaAtivas.size - 1)
                    etTarefa.text.clear()
                    Log.d(TAG, "Tarefa adicionada: $texto")
                } else {
                    Toast.makeText(this, "Digite uma tarefa!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error adding task: ${e.message}", e)
            }
        }
    }
    // deletar tarefa para melhorar interatividade
    private fun deletarTarefa(tarefa: Tarefa, lista: MutableList<Tarefa>, adapter: TarefaAdapter) {
        try {
            val index = lista.indexOf(tarefa)
            if (index != -1) {
                lista.removeAt(index)
                adapter.notifyItemRemoved(index)
                Log.d(TAG, "Tarefa removida: ${tarefa.descricao}")
                Toast.makeText(this, "Tarefa removida", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error deleting task: ${e.message}", e)
        }
    }

    //salvar detalhes, talvez colocar em cinza do lado da tarefa?
    private fun abrirDetalhes(tarefa: Tarefa) {
        try {
            val intent = Intent(this, DetalheActivity::class.java)
            intent.putExtra("descricao", tarefa.descricao)
            startActivity(intent)
            Log.d(TAG, "Abrindo detalhes: ${tarefa.descricao}")
            //erro de abrir tela descrição
        } catch (e: Exception) {
            Log.e(TAG, "Error opening details: ${e.message}", e)
            Toast.makeText(this, "Erro ao abrir detalhes", Toast.LENGTH_SHORT).show()
        }
    }

    private fun moverParaConcluidas(tarefa: Tarefa) {
        try {
            val index = listaAtivas.indexOf(tarefa)
            if (index != -1) {
                listaAtivas.removeAt(index)
                adapterAtivas.notifyItemRemoved(index)
                listaConcluidas.add(tarefa)
                adapterConcluidas.notifyItemInserted(listaConcluidas.size - 1)
                Log.d(TAG, "Tarefa movida para concluídas: ${tarefa.descricao}")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error moving to completed: ${e.message}", e)
        }
    }

    private fun moverParaAtivas(tarefa: Tarefa) {
        try {
            val index = listaConcluidas.indexOf(tarefa)
            if (index != -1) {
                listaConcluidas.removeAt(index)
                adapterConcluidas.notifyItemRemoved(index)
                listaAtivas.add(tarefa)
                adapterAtivas.notifyItemInserted(listaAtivas.size - 1)
                Log.d(TAG, "Tarefa movida para ativas: ${tarefa.descricao}")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error moving to active: ${e.message}", e)
        }
    }
}
