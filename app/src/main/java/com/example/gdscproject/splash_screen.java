package com.example.gdscproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class splash_screen extends AppCompatActivity
{
    ImageView splash_logo ;
    TextView tv_app_name, tv_app_description ;
    TextView btn_get_started ;
    Animation top_animation , bottom_animation ;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        splash_logo = findViewById(R.id.splash_logo) ;
        tv_app_name = findViewById(R.id.tv_app_name) ;
        tv_app_description = findViewById(R.id.tv_app_description) ;
        btn_get_started = findViewById(R.id.btn_get_started) ;
        top_animation = AnimationUtils.loadAnimation(this, R.anim.up_animation) ;
        bottom_animation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation) ;
        splash_logo.setAnimation(top_animation);
        tv_app_name.setAnimation(bottom_animation);
        tv_app_description.setAnimation(bottom_animation);
        btn_get_started.setAnimation(bottom_animation);

        btn_get_started.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_main();
            }
        });


    }

    void start_main()
    {
        if (FirebaseAuth.getInstance().getCurrentUser() != null && FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()) {
            SharedPreferences sharedPreferences = splash_screen.this.getSharedPreferences("fav_actors_selected_sp", MODE_PRIVATE) ;
            if (sharedPreferences.getInt("is_selected", 0) == 0) {
                Intent intent = new Intent(this, choose_fav_actors.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
            }
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}