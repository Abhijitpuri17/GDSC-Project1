package com.example.gdscproject.adapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gdscproject.EpisodesListActivity;
import com.example.gdscproject.R;
import com.example.gdscproject.models.Actor;
import com.example.gdscproject.models.Message;
import com.example.gdscproject.models.TvShow;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TvShowsAdapter extends RecyclerView.Adapter<TvShowsAdapter.TvShowsViewHolder> implements Filterable
{



    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<TvShow> filteredList = new ArrayList<>();
            if (constraint.toString().equals("") || constraint.toString().isEmpty()) filteredList.addAll(allShowsList) ;
            else {
                for (TvShow show : allShowsList) {
                    if (show.getShow_name().toLowerCase().contains(constraint.toString().toLowerCase())) filteredList.add(show) ;
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList ;
            return  filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            showsList.clear();
            showsList.addAll((ArrayList<TvShow>) results.values) ;
            notifyDataSetChanged();
        }
    } ;



     List<TvShow> showsList ;
     List<TvShow> allShowsList ;

     public TvShowsAdapter()
     {
         this.showsList = new ArrayList<>();
         update();
       allShowsList = new ArrayList<>(showsList) ;
       notifyDataSetChanged();
     }

    public void update() {
        FirebaseFirestore.getInstance().collection("Shows").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    showsList.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Map<String, Object> shows_data = document.getData();
                        showsList.add(new TvShow(shows_data.get("show_name").toString(), shows_data.get("show_image").toString(),  shows_data.get("show_id").toString()));
                    }
                    notifyDataSetChanged();
                }
            }
        });
    }



    @Override
    public TvShowsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tv_show_item, parent, false);
        TvShowsViewHolder viewHolder = new TvShowsViewHolder(view) ;

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(), EpisodesListActivity.class);
                intent.putExtra("show_id", showsList.get(viewHolder.getAdapterPosition()).id) ;
                parent.getContext().startActivity(intent);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder( TvShowsAdapter.TvShowsViewHolder holder, int position) {
        holder.tv_show_name.setText(showsList.get(position).show_name);
        Glide.with(holder.iv_show_cover_image).load(showsList.get(position).image_link).into(holder.iv_show_cover_image) ;

        holder.iv_show_cover_image.setFocusable(true);

    }

    @Override
    public int getItemCount() {
        return showsList.size();
    }

    static class TvShowsViewHolder extends RecyclerView.ViewHolder
    {
        ImageView iv_show_cover_image ;
        TextView tv_show_name ;

        public TvShowsViewHolder( View itemView) {
            super(itemView);
            iv_show_cover_image = itemView.findViewById(R.id.iv_show_cover_image);
            tv_show_name = itemView.findViewById(R.id.tv_show_name) ;
        }
    }




}
