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
import com.example.projetolistadetarefas.api.RetrofitClient
import com.example.projetolistadetarefas.database.DatabaseHelper
import com.example.projetolistadetarefas.model.Post
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_main)

        etTarefa = findViewById(R.id.etTarefa)
        btnAdicionar = findViewById(R.id.btnAdicionar)
        recyclerTarefas = findViewById(R.id.recyclerTarefas)
        recyclerConcluidas = findViewById(R.id.recyclerConcluidas)

        adapterAtivas = TarefaAdapter(listaAtivas, 
            onClick = { tarefa -> abrirDetalhes(tarefa) },
            onStatusChange = { tarefa -> moverParaConcluidas(tarefa) }
        )

        adapterConcluidas = TarefaAdapter(listaConcluidas,
            onClick = { tarefa -> abrirDetalhes(tarefa) },
            onStatusChange = { tarefa -> moverParaAtivas(tarefa) }
        )

        recyclerTarefas.layoutManager = LinearLayoutManager(this)
        recyclerTarefas.adapter = adapterAtivas

        recyclerConcluidas.layoutManager = LinearLayoutManager(this)
        recyclerConcluidas.adapter = adapterConcluidas

        btnAdicionar.setOnClickListener {
            val texto = etTarefa.text.toString().trim()
            if (texto.isNotEmpty()) {
                val novaTarefa = Tarefa(texto, false)
                listaAtivas.add(novaTarefa)
                adapterAtivas.notifyItemInserted(listaAtivas.size - 1)
                etTarefa.text.clear()
            } else {
                Toast.makeText(this, "Digite uma tarefa!", Toast.LENGTH_SHORT).show()
            }
        }

        // CHAMA A API AO INICIAR
        buscarDadosDaApi()
    }

    private fun buscarDadosDaApi() {
        val api = RetrofitClient.instance
        api.getPosts().enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful) {
                    val posts = response.body()
                    val db = DatabaseHelper(this@MainActivity)
                    posts?.forEach { post ->
                        db.insertPost(post.title, post.body)
                        // Opcional: Adicionar à lista para mostrar na tela
                        listaAtivas.add(Tarefa(post.title, false))
                    }
                    adapterAtivas.notifyDataSetChanged()
                    Log.d("API", "Dados salvos no banco e carregados na lista")
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Log.e("API_ERROR", t.message ?: "Erro desconhecido")
            }
        })
    }

    private fun abrirDetalhes(tarefa: Tarefa) {
        val intent = Intent(this, DetalheActivity::class.java)
        intent.putExtra("descricao", tarefa.descricao)
        startActivity(intent)
    }

    private fun moverParaConcluidas(tarefa: Tarefa) {
        val index = listaAtivas.indexOf(tarefa)
        if (index != -1) {
            listaAtivas.removeAt(index)
            adapterAtivas.notifyItemRemoved(index)
            listaConcluidas.add(tarefa)
            adapterConcluidas.notifyItemInserted(listaConcluidas.size - 1)
        }
    }

    private fun moverParaAtivas(tarefa: Tarefa) {
        val index = listaConcluidas.indexOf(tarefa)
        if (index != -1) {
            listaConcluidas.removeAt(index)
            adapterConcluidas.notifyItemRemoved(index)
            listaAtivas.add(tarefa)
            adapterAtivas.notifyItemInserted(listaAtivas.size - 1)
        }
    }
}
