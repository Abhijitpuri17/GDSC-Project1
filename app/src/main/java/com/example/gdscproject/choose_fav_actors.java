package com.example.gdscproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gdscproject.adapters.ActorsAdapter;
import com.example.gdscproject.models.Actor;

import java.util.ArrayList;
import java.util.List;

public class choose_fav_actors extends AppCompatActivity implements ActorsAdapter.IActorSelected {

    RecyclerView rv_actors ,rv_selected_actors;
    ActorsAdapter adapter ;
    SearchView searchView;
    List<Actor> actorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_fav_actors);

        actorList = new ArrayList<>() ;
        addDemoActors(actorList);

        rv_actors = findViewById(R.id.rv_actors) ;
        adapter = new ActorsAdapter(actorList, new ArrayList<>(), this) ;
        rv_actors.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3) ;
        rv_actors.setLayoutManager(gridLayoutManager);

        searchView = findViewById(R.id.search_view_actors) ;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }

        });

        TextView btn_submit = findViewById(R.id.btn_confirm_adding_actors);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = choose_fav_actors.this.getSharedPreferences("fav_actors_selected_sp", MODE_PRIVATE) ;
                sharedPreferences.edit().putInt("is_selected", 1).apply();
                Intent intent = new Intent(choose_fav_actors.this, HomeActivity.class);
                startActivity(intent) ;
            }
        });






    }


    /**
     * This is a demo method for testing
     */
    void addDemoActors(List<Actor> actorList)
    {
        actorList.add(new Actor("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTnImPW4s91ZjlXIsxV2nrHDRc6Ow-EuzDn1w&usqp=CAU", "Jitendra Kumar")) ;
        actorList.add(new Actor("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSb3Me0agAUxj_vqbJuKq7Uadd4tlDXOBLs_A&usqp=CAU", "Navin Kasturia")) ;
        actorList.add(new Actor("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ4lZGhdpg6KelA9EKsXhTJ25WTYxG4i70Vew&usqp=CAU", "Badri Chavan")) ;
        actorList.add(new Actor("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTNLnBFZ0JIGx7MNjtnjlOhvBgYXsvXi0iv9g&usqp=CAU", "Pankaj Tripathi")) ;
        actorList.add(new Actor("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ6tpXiB_461OEMpZgurzjOAhAU9gMXL9fqLQ&usqp=CAU", "Nawazuddin Siddiqui")) ;
        actorList.add(new Actor("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQeTKD3bdHxgiFYLV1L--zF8IFE65m9u2UHLg&usqp=CAU", "Mithila Palkar")) ;
        actorList.add(new Actor("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSHAaZKDQs3Yvcwf-6LONwIGyFWI6eviDbbHg&usqp=CAU", "Barkha singh")) ;
        actorList.add(new Actor("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSd0dg__IbxMiMurrmUiPba_EWgZn7IWVYepw&usqp=CAU", "Ahsas Channa")) ;
        actorList.add(new Actor("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS7F-vmS5aa6ZuQoqr77dN49K9VJl1E-BwNXg&usqp=CAU", "Manoj Bajpayee")) ;
        actorList.add(new Actor("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSebkgK0lyKUaqDQ-RIqIB0qXJWJsffl5xaeg&usqp=CAU", "Pratik Gandhi")) ;
    }


    @Override
    public void onActorClick() {
        searchView.setQuery("", false);
        searchView.clearFocus();

    }
}