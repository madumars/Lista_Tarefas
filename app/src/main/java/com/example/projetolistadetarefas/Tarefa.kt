package com.example.projetolistadetarefas

data class Tarefa (
    val descricao: String,
    var concluida: Boolean = false
)