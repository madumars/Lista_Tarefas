package com.example.projetolistadetarefas.api;
import retrofit2.Call;
import retrofit2.http.GET;

public class Service {

    interface ApiService {
        @GET("/posts")
        Call<List<Post>> getPosts();

}
