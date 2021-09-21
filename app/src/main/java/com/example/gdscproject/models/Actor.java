package com.example.gdscproject.models;

public class Actor
{
    private String actor_image_link ;
    private String actor_name ;

    public String getActor_image_link() {
        return actor_image_link;
    }

    public Actor(String actor_image_link, String actor_name) {
        this.actor_image_link = actor_image_link;
        this.actor_name = actor_name;
    }

    public void setActor_image_link(String actor_image_link) {
        this.actor_image_link = actor_image_link;
    }

    public String getActor_name() {
        return actor_name;
    }

    public void setActor_name(String actor_name) {
        this.actor_name = actor_name;
    }
}
