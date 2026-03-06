package com.example.projetolistadetarefas.api;

import com.example.projetolistadetarefas.model.Post;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public class Service {

    public interface ApiService {
        @GET("posts")
        Call<List<Post>> getPosts();
    }
}
