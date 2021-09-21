package com.example.gdscproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class sign_up extends AppCompatActivity
{

    ImageButton ib_back_arrow ;
    EditText et_email , et_password , et_username ;
    TextView btn_sign_up ;
    FirebaseAuth mAuth ;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ib_back_arrow = findViewById(R.id.ib_back_arrow) ;
        et_email = findViewById(R.id.et_email) ;
        et_password = findViewById(R.id.et_password) ;
        et_username = findViewById(R.id.et_username) ;
        mAuth = FirebaseAuth.getInstance();



        ib_back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish_activity();
            }
        });

        btn_sign_up  = findViewById(R.id.btn_sign_up) ;
        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sign_up_with_email();
            }
        });




    }

    void sign_up_with_email()
    {
        String email , password ;
        email = et_email.getText().toString() ;
        password = et_password.getText().toString();
        if (email.equals("") || password.equals("")) Toast.makeText(this, "Please enter username and password to continue", Toast.LENGTH_SHORT).show();
        else
        {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("TAG", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("TAG", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(sign_up.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }




    void finish_activity()
    {
        this.finish();
    }
    private void updateUI(Object o) {
        Intent intent = new Intent(this, choose_fav_actors.class) ;
        startActivity(intent) ;
    }
}