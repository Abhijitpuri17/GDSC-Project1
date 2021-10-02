package com.example.gdscproject.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.gdscproject.ChatsActivity;
import com.example.gdscproject.R;
import com.example.gdscproject.adapters.TvShowsAdapter;
import com.example.gdscproject.models.TvShow;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    RecyclerView rv_tv_shows ;
    TvShowsAdapter tvShowsAdapter ;
    SearchView searchView;
    FirebaseFirestore db ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false) ;

        rv_tv_shows = view.findViewById(R.id.rv_tv_shows);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), 2);
//        showsList = getShows();
        db = FirebaseFirestore.getInstance();


        tvShowsAdapter = new TvShowsAdapter();
        rv_tv_shows.setAdapter(tvShowsAdapter);
        rv_tv_shows.setLayoutManager(gridLayoutManager);
        searchView = view.findViewById(R.id.search_view_actors) ;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                tvShowsAdapter.getFilter().filter(newText);
                return true;
            }

        });

        return view;

    }

    /**
     * Demo method to get Tv shows for testing
     */

    List<TvShow> getShows()
    {
        List<TvShow> showList = new ArrayList<>();
        showList.add(new TvShow("Kota Factory", "https://m.media-amazon.com/images/M/MV5BZmRlMTkwMmYtMjU3Mi00MDRlLTg2ZjUtMGVkZGY3MTgwZmEyXkEyXkFqcGdeQXVyNDAzNDk0MTQ@._V1_.jpg", "1")) ;
        showList.add(new TvShow("Money Heist", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ5u40zBD3xLKKByt-UBrnzU1Qb2hd5jVxBJQ&usqp=CAU", "2")) ;
        showList.add(new TvShow("Lucifier", "https://flxt.tmsimg.com/assets/p18480237_b_v13_ac.jpg", "3")) ;
        showList.add(new TvShow("Friends", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTl9U-220iTI4rEUYzK1YPbsQPkS3AqS3OqsQ&usqp=CAU", "4")) ;
        showList.add(new TvShow("Big bang theory", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTXjdMgK7rtFXRJAJ0YQZGnTaG5XYNegeNDfQ&usqp=CAU", "5")) ;
        showList.add(new TvShow("Dark", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQwwlfzrNA316N8EVOlnTT0DFABCqUXl9Gtmg&usqp=CAU", "6")) ;

        return showList ;
    }
}