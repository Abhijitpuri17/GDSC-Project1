package com.example.gdscproject.models;

public class Episode
{
    String episode_name ;
    String episode_description ;
    String episode_id ;
    String episode_image_link ;

    public String getEpisode_name() {
        return episode_name;
    }

    public String getEpisode_image_link() {
        return episode_image_link;
    }

    public String getEpisode_description() {
        return episode_description;
    }

    public Episode(String episode_name, String episode_description, String episode_image_link, String episode_id) {
        this.episode_name = episode_name;
        this.episode_description = episode_description;
        this.episode_id = episode_id;
        this.episode_image_link = episode_image_link ;
    }

    public String getEpisode_id() {
        return episode_id;
    }
}
