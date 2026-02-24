package com.example.projetolistadetarefas

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetalheActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhe)

        val descricao = intent.getStringExtra("descricao")
        val tvDescricao = findViewById<TextView>(R.id.tvDescricao)

        tvDescricao.text = descricao
    }
}
