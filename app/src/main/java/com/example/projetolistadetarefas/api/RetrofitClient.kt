package com.example.projetolistadetarefas.api

import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    private const val HOSTNAME = "jsonplaceholder.typicode.com"

    // 1. Criar o PIN (O valor abaixo é um exemplo, o app vai falhar e mostrar o correto no Logcat se o site mudar)
    private val certificatePinner = CertificatePinner.Builder()
        .add(HOSTNAME, "sha256/5g6m7kU/d5r5e/x28fGq62qI4N6nO16o285Qv765S4o=")
        .build()

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // 2. Adicionar o certificatePinner ao cliente OkHttp
    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .certificatePinner(certificatePinner)
        .build()

    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiService::class.java)
    }
}
