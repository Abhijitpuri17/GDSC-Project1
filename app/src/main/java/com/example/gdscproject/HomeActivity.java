package com.example.gdscproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.gdscproject.adapters.TvShowsAdapter;
import com.example.gdscproject.fragments.FavouritesFragment;
import com.example.gdscproject.fragments.HomeFragment;
import com.example.gdscproject.fragments.ProfileFragment;
import com.example.gdscproject.models.TvShow;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity
{

    BottomNavigationView bottomNavigationView ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new HomeFragment()).commit() ;


        bottomNavigationView = findViewById(R.id.bottom_navigation) ;
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item)
            {
                Fragment f = new HomeFragment() ;

                switch (item.getItemId())
                {
                    case R.id.home: f = new HomeFragment();
                                    break ;
                    case R.id.favs: f = new FavouritesFragment();
                                    break ;
                    case R.id.profile: f = new ProfileFragment();
                                    break ;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, f).commit() ;

                return true;
            }
        });

    }

}