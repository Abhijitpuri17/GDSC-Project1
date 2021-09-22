package com.example.gdscproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gdscproject.adapters.ActorsAdapter;
import com.example.gdscproject.adapters.SelectedActorsAdapter;
import com.example.gdscproject.models.Actor;

import java.util.ArrayList;
import java.util.List;

public class choose_fav_actors extends AppCompatActivity implements ActorClicked{

    RecyclerView rv_actors ,rv_selected_actors;
    ActorsAdapter adapter ;
    SearchView searchView;
    SelectedActorsAdapter selectedActorsAdapter ;
    List<Actor> actorList, selectedActorList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_fav_actors);

        actorList = new ArrayList<>() ;
        selectedActorList = new ArrayList<>();
        addDemoActors(actorList);

        rv_actors = findViewById(R.id.rv_actors) ;
        adapter = new ActorsAdapter(actorList, new ArrayList<>(), this) ;
        rv_actors.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3) ;
        rv_actors.setLayoutManager(gridLayoutManager);

        rv_selected_actors = findViewById(R.id.rv_selected_actors) ;
        selectedActorsAdapter = new SelectedActorsAdapter(new ArrayList<>(selectedActorList), this) ;
        rv_selected_actors.setAdapter(selectedActorsAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false) ;
        rv_selected_actors.setLayoutManager(linearLayoutManager);



        searchView = findViewById(R.id.search_view_actors) ;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(choose_fav_actors.this, "text submit Query", Toast.LENGTH_SHORT).show();
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(choose_fav_actors.this, "text change Query", Toast.LENGTH_SHORT).show();
                adapter.getFilter().filter(newText);
                return true;
            }
        });



    }

    /**
     * This is a demo method for testing
     */
    void addDemoActors(List<Actor> actorList)
    {
        actorList.add(new Actor("https://m.media-amazon.com/images/M/MV5BMjAwNDU3MzcyOV5BMl5BanBnXkFtZTcwMjc0MTIxMw@@._V1_UY209_CR9,0,140,209_AL_.jpg", "Robert De Niro")) ;
        actorList.add(new Actor("https://m.media-amazon.com/images/M/MV5BMTQ3OTY0ODk0M15BMl5BanBnXkFtZTYwNzE4Njc4._V1_UY209_CR5,0,140,209_AL_.jpg", "Jack Nicholson")) ;
        actorList.add(new Actor("https://m.media-amazon.com/images/M/MV5BMTg3MDYyMDE5OF5BMl5BanBnXkFtZTcwNjgyNTEzNA@@._V1_UY209_CR65,0,140,209_AL_.jpg", "Marlon Brando")) ;
        actorList.add(new Actor("https://m.media-amazon.com/images/M/MV5BMjE5NDU2Mzc3MV5BMl5BanBnXkFtZTcwNjAwNTE5OQ@@._V1_UY209_CR8,0,140,209_AL_.jpg", "Denzel Washington")) ;
        actorList.add(new Actor("https://m.media-amazon.com/images/M/MV5BMTE5NTk4Mjc0OF5BMl5BanBnXkFtZTYwNzI0NDM2._V1_UY209_CR5,0,140,209_AL_.jpg", "Katharine Hepburn")) ;
        actorList.add(new Actor("https://m.media-amazon.com/images/M/MV5BMTIyOTE3MDM5Ml5BMl5BanBnXkFtZTYwMzA2MTM2._V1_UY209_CR10,0,140,209_AL_.jpg", "Humphrey Bogart")) ;
        actorList.add(new Actor("https://m.media-amazon.com/images/M/MV5BMTU4Mjk5MDExOF5BMl5BanBnXkFtZTcwOTU1MTMyMw@@._V1_UY209_CR4,0,140,209_AL_.jpg", "Meryl Streep")) ;
        actorList.add(new Actor("https://m.media-amazon.com/images/M/MV5BMjE2NDY2NDc1Ml5BMl5BanBnXkFtZTcwNjAyMjkwOQ@@._V1_UY209_CR9,0,140,209_AL_.jpg", "Daniel Day-Lewis")) ;
        actorList.add(new Actor("https://m.media-amazon.com/images/M/MV5BMTQ0OTE3MzQ2Nl5BMl5BanBnXkFtZTcwMDc2MDc1NA@@._V1_UX140_CR0,0,140,209_AL_.jpg", " Sidney Poitier")) ;
    }

    /** This method is called from ActorsAdapter by clicking on an actor
     * @param selected_actors
     */
    @Override
    public void add_to_selected_actors(List<Actor> selected_actors) {

         searchView.setQuery("", true);
        searchView.clearFocus();
        selectedActorList = new ArrayList<>(selected_actors) ;

        List<Actor> actorList_new = new ArrayList<>();
        for (Actor actor : actorList) {
            if (!selected_actors.contains(actor)) actorList_new.add(actor) ;
        }
        adapter = new ActorsAdapter(actorList_new,selected_actors, this) ;
        selectedActorList.clear();
        selectedActorList.addAll(selected_actors);
        selectedActorsAdapter  = new SelectedActorsAdapter(selected_actors, this) ;
        rv_actors.setAdapter(adapter);
        rv_selected_actors.setAdapter(selectedActorsAdapter);

        adapter.notifyDataSetChanged();

    }
}