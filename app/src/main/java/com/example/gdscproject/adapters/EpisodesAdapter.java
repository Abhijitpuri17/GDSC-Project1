package com.example.gdscproject.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gdscproject.ChatsActivity;
import com.example.gdscproject.R;
import com.example.gdscproject.models.Episode;
import com.example.gdscproject.models.TvShow;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;


public class EpisodesAdapter extends RecyclerView.Adapter<EpisodesAdapter.EpisodeViewHolder>
{

    List<Episode> episodeList ;
    String show_id ;

    public EpisodesAdapter(String show_id) {
        this.show_id = show_id;
        this.episodeList = new ArrayList<>();
        update();

    }

    public void update() {

        FirebaseFirestore.getInstance().collection("Shows").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        FirebaseFirestore.getInstance().collection("Shows").document(document.getId()).collection("Episodes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                                episodeList.clear();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Map<String, Object> episode_data = document.getData();
                                    episodeList.add(new Episode(episode_data.get("episode_name").toString(), episode_data.get("episode_description").toString(), episode_data.get("episode_image_link").toString(), episode_data.get("episode_id").toString()));
                                }
                                Collections.sort(episodeList, (lhs, rhs) -> {
                                    if (Integer.parseInt(lhs.getEpisode_id()) <= Integer.parseInt(rhs.getEpisode_id()))
                                        return -1;
                                    return 1;
                                });
                                notifyDataSetChanged();
                            }
                        });
                    }





                }
            }
        });
    }


    @Override
    public EpisodeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.episode_item, parent, false);

        EpisodeViewHolder viewHolder = new EpisodeViewHolder(view) ;


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(), ChatsActivity.class);
                intent.putExtra("episode_id", episodeList.get(viewHolder.getAdapterPosition()).getEpisode_id()) ;
                intent.putExtra("episode_image", episodeList.get(viewHolder.getAdapterPosition()).getEpisode_image_link());
                intent.putExtra("episode_name", episodeList.get(viewHolder.getAdapterPosition()).getEpisode_name()) ;
                intent.putExtra("episode_description", episodeList.get(viewHolder.getAdapterPosition()).getEpisode_description()) ;
                parent.getContext().startActivity(intent);
            }
        });



        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EpisodesAdapter.EpisodeViewHolder holder, int position) {
        holder.tv_episode_name.setText(episodeList.get(position).getEpisode_name());
        holder.tv_episode_description.setText(episodeList.get(position).getEpisode_description());
        Glide.with(holder.iv_episode_image).load(episodeList.get(position).getEpisode_image_link()).into(holder.iv_episode_image) ;
    }

    @Override
    public int getItemCount() {
        return episodeList.size();
    }

    static class EpisodeViewHolder extends RecyclerView.ViewHolder
    {

        TextView tv_episode_name , tv_episode_description ;
        ImageView iv_episode_image ;

        public EpisodeViewHolder(View itemView) {
            super(itemView);
            tv_episode_name = itemView.findViewById(R.id.tv_episode_name);
            tv_episode_description = itemView.findViewById(R.id.tv_episode_description);
            iv_episode_image = itemView.findViewById(R.id.iv_episode_image);
        }
    }
}
