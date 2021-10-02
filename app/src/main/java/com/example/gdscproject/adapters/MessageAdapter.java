package com.example.gdscproject.adapters;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gdscproject.ChatsActivity;
import com.example.gdscproject.R;
import com.example.gdscproject.models.Message;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.okhttp.internal.DiskLruCache;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>
{
    List<Message> messageList ;
    FirebaseFirestore db ;
    String episode_id ;


    public MessageAdapter(String episode_id) {
        this.messageList = new ArrayList<>();
        this.episode_id = episode_id;
        db = FirebaseFirestore.getInstance();
    }

    public void update() {
        db.collection("Messages").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    messageList.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Map<String, Object> messages_data = document.getData();
                        if (messages_data.get("episodeid").equals(episode_id))
                            messageList.add(new Message(messages_data.get("message_text").toString(), Long.parseLong(messages_data.get("created_at").toString()), (List<String>) messages_data.get("liked_by_list"),
                                    messages_data.get("name").toString()));
                    }
                    Collections.sort(messageList, (lhs, rhs) -> {
                        if (lhs.getCreatedAtTime() < rhs.getCreatedAtTime()) return -1;
                        return 1 ;
                    }) ;
                    notifyDataSetChanged();
                }
            }
        });
    }













    @Override
    public MessageViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_message_item, parent, false) ;

        MessageViewHolder viewHolder = new MessageViewHolder(view);



        return viewHolder;
    }

    @Override
    public void onBindViewHolder( MessageAdapter.MessageViewHolder holder, int position) {
        holder.tv_message.setText(messageList.get(position).getText());
        holder.tv_username.setText(messageList.get(position).getUser_name());

        if (messageList.get(position).user_image_link != null && !messageList.get(position).user_image_link.isEmpty())
        Glide.with(holder.itemView).load(messageList.get(position).user_image_link).into(holder.iv_user_image) ;


    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_username, tv_createdAt, tv_message, tv_like_count ;
        ImageView iv_user_image , iv_like ;

        public MessageViewHolder(View itemView) {
            super(itemView);
            tv_username = itemView.findViewById(R.id.tv_username) ;
            tv_createdAt = itemView.findViewById(R.id.tv_created_at);
            tv_message = itemView.findViewById(R.id.tv_message_content) ;
            tv_like_count = itemView.findViewById(R.id.tv_like_count) ;
            iv_user_image = itemView.findViewById(R.id.iv_user_photo) ;
            iv_like = itemView.findViewById(R.id.iv_like_button) ;
        }
    }
}
