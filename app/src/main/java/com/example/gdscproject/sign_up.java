package com.example.gdscproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class sign_up extends AppCompatActivity
{

    ImageButton ib_back_arrow ;
    EditText et_email , et_password , et_username ;
    TextView btn_sign_up , tv_go_to_sign_in;
    FirebaseAuth mAuth ;
    FirebaseFirestore db ;
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
        db = FirebaseFirestore.getInstance();




        ib_back_arrow.setOnClickListener(v -> finish_activity());

        btn_sign_up  = findViewById(R.id.btn_sign_up) ;
        btn_sign_up.setOnClickListener(v -> sign_up_with_email());

        tv_go_to_sign_in = findViewById(R.id.tv_go_to_sign_in);
        tv_go_to_sign_in.setOnClickListener(v -> start_sign_in());

    }

    void sign_up_with_email()
    {
        String email , password , username;
        email = et_email.getText().toString() ;
        password = et_password.getText().toString();
        username = et_username.getText().toString();
        if (email.equals("") || password.equals("")) Toast.makeText(this, "Please enter username and password to continue", Toast.LENGTH_SHORT).show();
        else
        {
            Dialog progress_dialog = new Dialog(this) ;
            progress_dialog.setContentView(R.layout.progress_dialog);
            progress_dialog.setCancelable(false);
            progress_dialog.setCanceledOnTouchOutside(false);
            progress_dialog.show();




            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(sign_up.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("TAG", "createUserWithEmail:success");
                                Toast.makeText(sign_up.this, "Success", Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();

                                Map<String, Object> curr_user = new HashMap<>() ;
                                curr_user.put("Email", user.getEmail()) ;
                                curr_user.put("uid", user.getUid()) ;
                                curr_user.put("name", username);
                                db.collection("Users").add(curr_user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {

                                    }
                                }) ;

                                user.sendEmailVerification().addOnCompleteListener(task1 -> {
                                    progress_dialog.dismiss();

                                    if (task1.isSuccessful()) {
                                        Toast.makeText(sign_up.this, "We have sent you an email. Verify your account to continue", Toast.LENGTH_SHORT).show() ;
                                        FirebaseAuth.getInstance().signOut();
                                        updateUI(user) ;
                                    }
                                }) ;

                            } else {
                                progress_dialog.dismiss();
                                // If sign in fails, display a message to the user.
                                Log.w("TAG", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(sign_up.this, task.getException().getLocalizedMessage(),
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

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                start_sign_in();
            }
        }, 3500) ;

    }


    void start_sign_in()
    {
        Intent intent = new Intent(this, sign_in.class) ;
        startActivity(intent) ;
        this.finish();
    }
}