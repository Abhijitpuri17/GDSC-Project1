package com.example.gdscproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gdscproject.adapters.ActorsAdapter;
import com.example.gdscproject.models.Actor;

import java.util.ArrayList;
import java.util.List;

public class choose_fav_actors extends AppCompatActivity implements ActorClicked{

    RecyclerView rv_actors ,rv_selected_actors;
    List<Actor> actorList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_fav_actors);
        actorList = new ArrayList<>() ;
        addDemoActors(actorList);
        rv_actors = findViewById(R.id.rv_actors) ;
        ActorsAdapter adapter = new ActorsAdapter(actorList, this) ;
        rv_actors.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3) ;
        rv_actors.setLayoutManager(gridLayoutManager);




        SearchView searchView = findViewById(R.id.search_view_actors) ;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter("");
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                adapter.notifyDataSetChanged();
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

    @Override
    public void add_to_selected_actors(String actor) {
        TextView tv_selected_actors = findViewById(R.id.tv_selected_actors) ;
        if (tv_selected_actors.getText().toString().equals("No Actor Selected")) tv_selected_actors.setText("");
        if (tv_selected_actors.getText().toString().contains(actor)) Toast.makeText(this, "Aready selected", Toast.LENGTH_SHORT).show();
        else tv_selected_actors.setText(tv_selected_actors.getText().toString() + ",  " + actor);
    }
}