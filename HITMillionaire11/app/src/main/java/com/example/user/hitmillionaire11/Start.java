package com.example.user.hitmillionaire11;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Locale;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;

public class Start extends AppCompatActivity {

    String mobileLanguge;
    private Locale locale;

    FirebaseDatabase database;
    DatabaseReference questions;

    TextView textViewCategoryName;
    Button buttonStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mobileLanguge = locale.getDefault().getLanguage();
        textViewCategoryName = findViewById(R.id.textViewCategoryName);
        buttonStart = findViewById(R.id.buttonStartGame);

        database = FirebaseDatabase.getInstance();
        questions = database.getReference("Questions");
        Common.categoryID = getIntent().getStringExtra("CategoryID");
        switch (Common.categoryID)
        {
            case "01": textViewCategoryName.setText(R.string.cat_sport);
                break;
            case "02": textViewCategoryName.setText(R.string.cat_heroes);
                break;
            case "03": textViewCategoryName.setText(R.string.cat_flags);
                break;

        }
        loadQuestion(Common.categoryID);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Start.this,Playing.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadQuestion(String categoryID) {

        if (Common.questionList.size() > 0) {
            Common.questionList.clear();
        }
            questions.orderByChild("CategoryID").equalTo(categoryID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Question ques = postSnapshot.getValue(Question.class);
                            if(ques.getLanguage().equals(mobileLanguge)) {
                                Common.questionList.add(ques);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) { }
                });


        Collections.shuffle(Common.questionList);
    }
}
