package com.example.gdscproject.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gdscproject.ActorClicked;
import com.example.gdscproject.R;
import com.example.gdscproject.models.Actor;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActorsAdapter extends RecyclerView.Adapter<ActorsAdapter.ActorsViewHolder> implements Filterable
{

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Actor> filteredList = new ArrayList<>();
            if (constraint.toString().equals("") || constraint.toString().isEmpty()) filteredList.addAll(allActorsList) ;
            else {
                for (Actor actor : allActorsList) {
                    if (actor.getActor_name().toLowerCase().contains(constraint.toString().toLowerCase())) filteredList.add(actor) ;
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList ;
        return  filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            actorList.clear();
            actorList.addAll((ArrayList<Actor>) results.values) ;

            notifyDataSetChanged();
        }
    } ;


    List<Actor> actorList;
    List<Actor> allActorsList;
    List<Actor> selectedActorsList ;
    ActorClicked listener ;

    public ActorsAdapter(List<Actor> actorList, ActorClicked listener)
    {
        this.actorList = actorList ;
        this.allActorsList = new ArrayList<>(actorList);
        selectedActorsList = new ArrayList<>() ;
        this.listener = listener ;
    }


    public void addActor(Actor actor)
    {
        if (this.actorList == null) this.actorList = new ArrayList<>();
        this.actorList.add(actor) ;
        notifyDataSetChanged();
    }

    class ActorsViewHolder extends RecyclerView.ViewHolder
    {
        private CircleImageView civ_actor_image ;
        private TextView tv_actor_name ;

        public ActorsViewHolder(View itemView) {
            super(itemView);
            civ_actor_image = itemView.findViewById(R.id.civ_actor_image) ;
            tv_actor_name = itemView.findViewById(R.id.tv_actor_name) ;
        }

    }





    @Override
    public @NotNull ActorsViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.actor_item, parent, false) ;
        ActorsViewHolder viewHolder= new ActorsViewHolder(view) ;

        viewHolder.itemView.setOnClickListener(v -> listener.add_to_selected_actors(actorList.get(viewHolder.getAdapterPosition()).getActor_name()));


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ActorsAdapter.ActorsViewHolder holder, int position) {

        Actor actor = actorList.get(position) ;
        Glide.with(holder.civ_actor_image).load(actor.getActor_image_link()).into(holder.civ_actor_image) ;
        holder.tv_actor_name.setText(actor.getActor_name());

    }

    @Override
    public int getItemCount() {
        return actorList.size() ;
    }




}


