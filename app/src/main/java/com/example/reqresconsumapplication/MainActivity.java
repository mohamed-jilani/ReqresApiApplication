package com.example.reqresconsumapplication;

import android.os.Bundle;
//import android.telecom.Call;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.reqresconsumapplication.modèle.User;
import com.example.reqresconsumapplication.modèle.UserResponse;
import com.example.reqresconsumapplication.service.ApiService;
import com.example.reqresconsumapplication.service.RetrofitClient;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); // Définit la Toolbar comme barre d'action

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialise l'adapter avec une liste vide pour commencer
        userAdapter = new UserAdapter(this, userList);
        recyclerView.setAdapter(userAdapter);

        // Récupérer les données depuis l'API
        fetchAllUsers();
    }

    private int currentPage = 1; // Page actuelle
    private int totalPages = 1; // Total de pages (sera mis à jour par l'API)

    private void fetchAllUsers() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        // Appelle l'API pour chaque page
        apiService.getUsers(currentPage).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Ajoute les utilisateurs à la liste
                    userList.addAll(response.body().getData());
                    userAdapter.notifyDataSetChanged(); // Met à jour l'adapter

                    // Met à jour le total des pages et incrémente la page actuelle
                    totalPages = response.body().getTotal_pages();
                    currentPage++;

                    // Si toutes les pages ne sont pas encore récupérées, appelle fetchAllUsers() pour la page suivante
                    if (currentPage <= totalPages) {
                        fetchAllUsers();
                    }
                } else {
                    Log.e("MainActivity", "Erreur lors de la récupération des utilisateurs");
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e("MainActivity", "Échec : " + t.getMessage());
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Infle le menu dans la barre d'action
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Gère les clics sur les éléments du menu
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            // Action lorsque le bouton Refresh est cliqué
            fetchAllUsers(); // Recharger les utilisateurs
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    /*
    private void fetchUsers() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
            apiService.getUsers(2).enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        userList.clear();
                        userList.addAll(response.body().getData());
                        userAdapter.notifyDataSetChanged(); // Met à jour l'adapter
                    } else {
                        Log.e("MainActivity", "Erreur lors de la récupération des utilisateurs");
                    }
                }

                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {
                    Log.e("MainActivity", "Échec : " + t.getMessage());
                }
            });

    }

     */
}
