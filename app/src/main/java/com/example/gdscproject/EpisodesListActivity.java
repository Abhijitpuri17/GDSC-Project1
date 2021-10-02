package com.example.gdscproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.gdscproject.adapters.EpisodesAdapter;
import com.example.gdscproject.models.Episode;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EpisodesListActivity extends AppCompatActivity
{

    List<Episode> episodeList ;
    RecyclerView rv_episodes_list ;
    EpisodesAdapter episodesAdapter ;
    Intent intent ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episodes_list);
        intent = getIntent();
        episodeList = new ArrayList<>();
//        getEpisodesDemo(episodeList);
//
//        for (int i = 0 ; i < episodeList.size(); i++) {
//            Episode episode = episodeList.get(i);
//
//            Map<String, Object> episode_map = new HashMap<>();
//
//            episode_map.put("episode_name", episode.getEpisode_name());
//            episode_map.put("episode_description", episode.getEpisode_description());
//            episode_map.put("episode_image_link", episode.getEpisode_image_link());
//            episode_map.put("episode_id", episode.getEpisode_id()) ;
//
//
//            FirebaseFirestore.getInstance().collection("Shows").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                @Override
//                public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
//                    if (task.isSuccessful())  {
//                        for (QueryDocumentSnapshot document : task.getResult()) {
//                            if (document.getData().get("show_id").equals(intent.getStringExtra("show_id"))) {
//
//
//                                FirebaseFirestore.getInstance().collection("Shows").document(document.getId()).collection("Episodes").add(episode_map).addOnSuccessListener(documentReference -> {
//                Toast.makeText(EpisodesListActivity.this, "Success", Toast.LENGTH_SHORT).show();
//            });
//
//
//
//                            }
//
//
//                        }
//
//
//                    }
//                }
//            }) ;
//        }


        rv_episodes_list = findViewById(R.id.rv_episodes_list);
        episodesAdapter = new EpisodesAdapter(intent.getStringExtra("show_id"));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) ;
        rv_episodes_list.setLayoutManager(linearLayoutManager);
        rv_episodes_list.setAdapter(episodesAdapter);
    }


//    void getEpisodesDemo(List<Episode> episodeList)
//    {
//       episodeList.add(new Episode("Season 1 , Episode 1", "Vaibhav migrates to Kota to prepare for India’s toughest college entrance examination." +
//               " Does this late entrant stand a chance in the race? Dedicated to Shrimati SL Loney ji," +
//               " Shri Irodov ji and Maananiya HC Verma ji",
//               "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQROkgjrBns_ORHkY4bSVfzHluIrPLhxzfPNd0KdBtB6Xqcbzbz7Pupj2dtBRI6-uPEbkU&usqp=CAU", intent.getStringExtra("show_id")+"1"))  ;
//
//       episodeList.add(new Episode("Season 1 ,Episode 2", "Vaibhav is part of the pack but ‘studying’ isn't the only hurdle he faces. When Meena, Uday," +
//               " and Shivangi fail to help him, Jeetu bhaiya volunteers. Will Jeetu bhaiya be able to save Vaibhav from the horrors of mess-food, insomnia, and constipation? ",
//               "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQM0lUe9OEL7GbHlx-7izAj5yVkb98fEeivPKC5Ku7lylS0Aasg40QJ_VLbu28RkNSb31g&usqp=CAU", intent.getStringExtra("show_id")+"2")) ;
//
//
//       episodeList.add(new Episode("Season 1, Episode 3", "How do you tackle an insurmountable hurdle? What do you do when no effort yields results and there " +
//               "is no solution in sight? With Jeetu bhaiya as a witness, Vaibhav and Meena lose this one to Inorganic Chemistry (Or do they?)",
//               "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQXQXv7oG-QKfB7Qopjqp2PLSoKjJXLlM14GnR4XavTzCBenvcqU5wRpeNoWVqJTXiBnQo&usqp=CAU", intent.getStringExtra("show_id")+"3")) ;
//
//
//        episodeList.add(new Episode("Season 1 , Episode 4", "What is a better strategy to attain a goal - An uninterrupted stride without any intermissions or" +
//                " one that consists of rejuvenating breaks? Vaibhav and Meena find their modes of recharging. ",
//                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQyyXNwoGO8lJ6EgNZ63aGUfvWP0KHIzg31UTWwoWAoz9iZ2kE8BKsTs0LobY6tJRKAu8Q&usqp=CAU", intent.getStringExtra("show_id")+"4")) ;
//
//
//        episodeList.add(new Episode("Season 1 , Episode 5", "How far can one go to ensure success? Vaibhav left his home once for the cause, will he be able to do" +
//                " the same again? ", "https://i.ytimg.com/vi/923ks1pc0LQ/maxresdefault.jpg", intent.getStringExtra("show_id")+"5")) ;
//
//        episodeList.add(new Episode("Season 2 , Episode 1", "With their new classes off to a bleak start, Vaibhav and his peers question the value of the IIT dream and " +
//                "seek inspiration from a trusted source.",
//                "https://occ-0-2085-2186.1.nflxso.net/dnm/api/v6/9pS1daC2n6UGc3dUogvWIPMR_OU/AAAABSDUaXHHpF0Oldl4eJMTUf7-zsR0gB8t1N43f5sQIoWo8s9w0Fldc631IkZ_uS8y4GIKQ6LeNCbyR2" +
//                        "atuWrQd3B1QzDNMBeNIce-nrAdsiLQ4Ble.jpg?r=4d9", intent.getStringExtra("show_id")+"21"))  ;
//
//        episodeList.add(new Episode("Season 2 ,Episode 2", "Struggling with Physics, Vaibhav attempts to bend the rules to attend Jeetu’s classes. Meena frets that teenage " +
//                "hormones will get the best of him.", "https://occ-0-2085-2186.1.nflxso.net/dnm/api/v6/9pS1daC2n6UGc3dUogvWIPMR_OU/AAAABTOln8QU4Ibj7S_Er9zumcU4EoMV__SHPIN_GdVXDg-iLCYCgJJ3ovMeomb_s5zSAd09UDrAOow" +
//                "FuOJH-epoyvwX31TiBKgvQg9MwCdO4W0fgMiS.jpg?r=bbb", "22")) ;
//
//        episodeList.add(new Episode("Season 2, Episode 3", "Jeetu searches for staff for his institute, making some reluctant choices along the way. Filled with self-doubt, Vartika evades practice tests " +
//                "at Aimers.", "https://occ-0-2085-2186.1.nflxso.net/dnm/api/v6/9pS1daC2n6UGc3dUogvWIPMR_OU/AAAABTKV2FgzJjp1lDkdhDC3WFkSYbIT1j9h8I-_T8IiaxQKus-FixBjIjWk3yu3MR_D2H1brVgJr0_JQcFz-NhS1BkYt6BjCs7uz4kEBA023S_" +
//                "LpbCz.jpg?r=ad6", "23")) ;
//
//        episodeList.add(new Episode("Season 2 , Episode 4", "When a bout of jaundice slows Vaibhav down in his studying, Jeetu insists he calls his only hope to get well quickly: his mother.",
//                "https://occ-0-2085-2186.1.nflxso.net/dnm/api/v6/9pS1daC2n6UGc3dUogvWIPMR_OU/AAAABee5ZAXXHQt0ShvJbqJFBMFuXj6cEabHcqWR8192T5GLDdA08bJNhvO88uw9Cyx0Lu7Mbsln8G-wXol61SJSGyR_ydAPJS1CE2B2eXSxV9lJjogW.jpg?r=1de", "4")) ;
//
//        episodeList.add(new Episode("Season 2 , Episode 5", "JEE results send Kota into celebration mode and give Vaibhav, Meena and Uday glimpses into their own futures, but the occasion comes with sad truths.",
//                "https://occ-0-2085-2186.1.nflxso.net/dnm/api/v6/9pS1daC2n6UGc3dUogvWIPMR_OU/AAAABVyj5Nlen3O2WiDbP_4SMBdFU7FnrDaE-4-XGk7wEXzgG4Gh-MYmkmJ31xfKsVLCUeqAXWAn4UAedAe96lnyKaix3bCMSA93pPk18W427IvOGPaD.jpg?r=477", "5")) ;
//
//    }




}