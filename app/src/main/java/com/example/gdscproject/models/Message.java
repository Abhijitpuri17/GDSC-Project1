package com.example.gdscproject.models;

import java.util.List;

public class Message
{
    String text ;
    public String user_image_link ;
    long createdAtTime = 0L ;
     List<String> likedBy ;
     String user_name ;
     public String message_id ;
     public String episode_id ;

    public String getUser_image_link() {
        return user_image_link;
    }

    public String getUser_name() {
        return user_name;
    }

    public Message(String text, long createdAtTime, List<String> likedBy, String user_name) {
        this.text = text;
        this.createdAtTime = createdAtTime;
        this.likedBy = likedBy;
        this.user_name = user_name ;
        this.message_id = message_id;
        this.episode_id = episode_id;
    }

    public String getText() {
        return text;
    }

    public long getCreatedAtTime() {
        return createdAtTime;
    }

    public List<String> getLikedBy() {
        return likedBy;
    }


}
