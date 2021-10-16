package com.example.gdscproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class ForgotPasswordActivity extends AppCompatActivity {


    TextView btn_confirm ;
    EditText et_email ;
    Dialog progress_dialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        btn_confirm = findViewById(R.id.btn_confirm) ;
        et_email = findViewById(R.id.et_email) ;

        btn_confirm.setOnClickListener(v -> {
            resetPassword();
        });



    }

    void resetPassword() {
        String email = et_email.getText().toString();
        if (email.isEmpty())
            Toast.makeText(this, "Please enter your email id", Toast.LENGTH_SHORT).show();
        else {

            progress_dialog = new Dialog(this) ;
            progress_dialog.setContentView(R.layout.progress_dialog);
            progress_dialog.setCancelable(false);
            progress_dialog.setCanceledOnTouchOutside(false);
            progress_dialog.show();

            FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(task -> {

                progress_dialog.dismiss();

                if (task.isSuccessful()) {
                    Toast.makeText(this, "We have sent you an email to reset the password", Toast.LENGTH_SHORT).show();

                    new Handler(getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            goToSignIn();
                        }
                    }, 3500);

                }
                else {
                    Toast.makeText(this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    void goToSignIn()
    {
        startActivity(new Intent(this, sign_in.class));
        this.finish();
    }



}