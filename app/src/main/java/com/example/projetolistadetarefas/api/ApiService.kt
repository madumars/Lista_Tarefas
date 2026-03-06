package com.example.projetolistadetarefas.api

import com.example.projetolistadetarefas.model.Post
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("posts")
    fun getPosts(): Call<List<Post>>
}
