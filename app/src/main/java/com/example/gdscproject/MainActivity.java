package com.example.gdscproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity
{

    TextView btn_sign_in , btn_sign_up;
    FirebaseAuth mAuth ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_sign_in = findViewById(R.id.btn_sign_in) ;
        btn_sign_up = findViewById(R.id.btn_sign_up) ;
        mAuth = FirebaseAuth.getInstance();

        btn_sign_in.setOnClickListener(v -> start_sign_in());

        btn_sign_up.setOnClickListener(v -> start_sign_up());
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            updateUI(null);
        }
    }

    void start_sign_up()
    {
        Intent intent = new Intent(this, sign_up.class) ;
        startActivity(intent) ;
    }

    void start_sign_in()
    {
        Intent intent = new Intent(this, sign_in.class) ;
        startActivity(intent) ;
    }

    private void updateUI(Object o) {
        Intent intent = new Intent(this, choose_fav_actors.class) ;
        startActivity(intent) ;
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.finish();
    }
}