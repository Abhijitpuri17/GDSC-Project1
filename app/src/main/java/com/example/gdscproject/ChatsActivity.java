package com.example.gdscproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gdscproject.adapters.MessageAdapter;
import com.example.gdscproject.models.Message;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatsActivity extends AppCompatActivity
{

    RecyclerView rv_messages ;
    MessageAdapter messageAdapter ;
    ImageView iv_send_btn, iv_episode_image ;
    TextView tv_episode_name , tv_episode_description ;
    EditText et_message_text ;
    LinearLayoutManager llm ;
    List<Message> messageList ;
    FirebaseFirestore db ;
    Intent intent ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        intent = getIntent();
        db = FirebaseFirestore.getInstance();
        messageList = new ArrayList<>();

        db.collection("Messages").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if (task.isSuccessful())
                {
                    for (QueryDocumentSnapshot document : task.getResult()){
                        Map<String, Object> messages_data= document.getData() ;
                        if (messages_data.get("episodeid").equals(intent.getStringExtra("episode_id")))
                            messageList.add(new Message(messages_data.get("name").toString(), Long.parseLong(messages_data.get("created_at").toString()), (List<String>) messages_data.get("liked_by_list"), messages_data.get("name").toString())) ;
                    }
                }
            }
        }) ;

        iv_episode_image = findViewById(R.id.iv_episode_image) ;
        Glide.with(this).load(intent.getStringExtra("episode_image")).into(iv_episode_image) ;

        tv_episode_name = findViewById(R.id.tv_episode_name) ;
        tv_episode_name.setText(intent.getStringExtra("episode_name"));

        tv_episode_description = findViewById(R.id.tv_episode_description);
        tv_episode_description.setText(intent.getStringExtra("episode_description"));
        tv_episode_description.setMovementMethod(new ScrollingMovementMethod());


        iv_send_btn = findViewById(R.id.iv_send_btn) ;
        rv_messages = findViewById(R.id.rv_messages);
        et_message_text = findViewById(R.id.et_message) ;
        messageAdapter = new MessageAdapter(intent.getStringExtra("episode_id")) ;
        rv_messages.setAdapter(messageAdapter);

        llm = new LinearLayoutManager(this) ;
        rv_messages.setLayoutManager(llm);
        new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
               llm.scrollToPositionWithOffset(messageAdapter.getItemCount()-1, 1);
            }
        }.start() ;

        iv_send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send_message();
            }
        });

        Handler handler = new Handler() ;
        Runnable run = new Runnable() {
            @Override
            public void run() {
                messageAdapter.update();
                handler.postDelayed(this, 1000) ;
            }
        } ;

        handler.post(run) ;








    }

    void send_message()
    {
        Map<String, Object> message= new HashMap<>();
        String message_text = et_message_text.getText().toString() ;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                         if (document.getData().get("uid").equals(user.getUid())){
                             message.put("name", document.getData().get("name")) ;
                             message.put("userid", user.getUid()) ;
                             if (document.getData().containsKey("imagelink")) {
                                 message.put("user_photo", document.getData().get("imagelink")) ;
                             }
                             message.put("liked_by_list", new ArrayList<String>()) ;
                             message.put("message_text", message_text) ;
                             message.put("episodeid", intent.getStringExtra("episode_id")) ;
                             Long time_now = System.currentTimeMillis();
                             message.put("created_at", time_now) ;
                             db.collection("Messages").add(message).addOnSuccessListener(documentReference -> {
                                 et_message_text.setText("");

                                 InputMethodManager mgr = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE) ;
                                 llm.scrollToPositionWithOffset(messageAdapter.getItemCount()-1, 1);
                                 mgr.hideSoftInputFromWindow(et_message_text.getWindowToken(), 0) ;

                             }) ;
                             break;
                         }
                    }
                }
            }
        }) ;

    }





}