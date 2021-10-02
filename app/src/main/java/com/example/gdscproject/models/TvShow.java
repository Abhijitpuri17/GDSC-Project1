package com.example.gdscproject.models;

public class TvShow {
   public String show_name ;
   public String image_link ;
    public String id ;


    public TvShow(String show_name, String image_link, String id)
    {
        this.id = id ;
        this.image_link = image_link ;
        this.show_name = show_name ;
    }

    public String getShow_name() {
        return show_name;
    }
}
