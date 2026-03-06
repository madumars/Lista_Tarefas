package com.example.projetolistadetarefas.api;

import com.example.projetolistadetarefas.Tarefa;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("posts") // Removi a barra inicial pois o Retrofit prefere caminhos relativos à BASE_URL
    Call<List<Tarefa>> getTarefas();
}
