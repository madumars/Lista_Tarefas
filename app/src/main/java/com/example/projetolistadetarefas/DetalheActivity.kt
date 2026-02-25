package com.example.projetolistadetarefas

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
//import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DetalheActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhe)

        //val tvDescricao = findViewById<TextView>(R.id.tvDescricao)
        val etTarefa2 = findViewById<EditText>(R.id.etTarefa2)
        val btnSalvar = findViewById<Button>(R.id.btnSalvar)

        // Recebe a descrição que veio da tela anterior
        val descricaoOriginal = intent.getStringExtra("descricao")
        //tvDescricao.text = descricaoOriginal
        
        // Preenche o campo de edição
        etTarefa2.setText(descricaoOriginal)

        btnSalvar.setOnClickListener {
            val novoTexto = etTarefa2.text.toString().trim()

            if (novoTexto.isNotEmpty()) {
                Toast.makeText(this, "Informação guardada: $novoTexto", Toast.LENGTH_SHORT).show()
                finish() // Fecha a tela e volta para a anterior
            } else {
                Toast.makeText(this, "A descrição não pode estar vazia!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
