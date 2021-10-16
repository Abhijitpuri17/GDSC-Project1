package com.example.gdscproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class sign_in extends AppCompatActivity
{

    GoogleSignInClient mGoogleSignInClient ;
    private final static int RC_SIGN_IN = 101 ;
    EditText et_email , et_password , et_username ;
    LinearLayout google_sign_in_btn ;
    TextView btn_sign_in , tv_go_to_sign_up, tv_forgot_password;
    private FirebaseAuth mAuth ;
    FirebaseFirestore db ;
    Dialog progress_dialog ;
    ImageButton ib_back_arrow ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ib_back_arrow = findViewById(R.id.ib_back_arrow) ;
        google_sign_in_btn = findViewById(R.id.google_sig_in_btn) ;
        btn_sign_in = findViewById(R.id.btn_sign_in);
        et_email = findViewById(R.id.et_email) ;
        et_password = findViewById(R.id.et_password) ;
        tv_forgot_password = findViewById(R.id.tv_forgot_password) ;
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        /** Go to previous activity by back arrow
         */
        ib_back_arrow.setOnClickListener(v -> finish_activity());

        /**
         * Sign In with google button
         */
        google_sign_in_btn.setOnClickListener(v -> signIn());

        /**
         * Sign In with email and password
         */
        btn_sign_in.setOnClickListener(v -> sign_in_with_email());

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        tv_go_to_sign_up = findViewById(R.id.tv_go_to_sign_up);
        tv_go_to_sign_up.setOnClickListener(v -> start_sign_up());

        tv_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(sign_in.this, ForgotPasswordActivity.class)) ;
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null && currentUser.isEmailVerified()){
            updateUI(null);
        }
    }

    void finish_activity()
    {
        this.finish();
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        progress_dialog = new Dialog(this) ;
        progress_dialog.setContentView(R.layout.progress_dialog);
        progress_dialog.setCancelable(false);
        progress_dialog.setCanceledOnTouchOutside(false);
        progress_dialog.show();
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("TAG", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("TAG", "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progress_dialog.dismiss();

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            Map<String, Object> curr_user = new HashMap<>() ;
                            curr_user.put("Email", user.getEmail()) ;
                            curr_user.put("uid", user.getUid()) ;
                            curr_user.put("name", user.getDisplayName());
                            curr_user.put("imagelink", user.getPhotoUrl().toString()) ;
                            db.collection("Users").add(curr_user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(sign_in.this, "SUCCESSSSSSS", Toast.LENGTH_SHORT).show();
                                }
                            }) ;



                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }

    void sign_in_with_email()
    {
        String email , password ;
        email = et_email.getText().toString() ;
        password = et_password.getText().toString();
        if (email.equals("") || password.equals("")) Toast.makeText(this, "Please enter username and password to continue", Toast.LENGTH_SHORT).show();
        else {

            Dialog progress_dialog = new Dialog(this) ;
            progress_dialog.setContentView(R.layout.progress_dialog);
            progress_dialog.setCancelable(false);
            progress_dialog.setCanceledOnTouchOutside(false);
            progress_dialog.show();


            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progress_dialog.dismiss();
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("TAG", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();

                                if (user.isEmailVerified())
                                updateUI(user);
                                else Toast.makeText(sign_in.this, "Please verify your account by the emai we have sent to you" , Toast.LENGTH_SHORT).show();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("TAG", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(sign_in.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

    void start_sign_up()
    {
        Intent intent = new Intent(this, sign_up.class) ;
        startActivity(intent) ;
        this.finish();
    }


    private void updateUI(Object o) {
        Intent intent = new Intent(this, choose_fav_actors.class) ;
        startActivity(intent) ;
    }
}