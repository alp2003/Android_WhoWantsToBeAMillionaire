package com.example.user.hitmillionaire11;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class CategoriesActivity extends AppCompatActivity  {

    ImageButton imageButtonUserSettings;
    ImageView imageButtonLeaderboard;
    String urlSport, urlHeroes,urlFlags;
    ImageView imageViewCategorySport, imageViewCategoryHeroes,imageViewCategoryFlags;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        imageViewCategorySport = findViewById(R.id.imageViewSport);
        imageViewCategoryHeroes = findViewById(R.id.imageViewHeroes);
        imageViewCategoryFlags = findViewById(R.id.imageViewFlags);


        urlHeroes ="https://firebasestorage.googleapis.com/v0/b/hitmillionaire.appspot.com/o/heroes.jpg?alt=media&token=0515c2f2-b03c-45df-aa43-84b24b54d38c" ;
        urlSport = "https://firebasestorage.googleapis.com/v0/b/hitmillionaire.appspot.com/o/sport.jpg?alt=media&token=22eab2e0-a793-4f58-b60d-4fcec1d95da3";
        urlFlags = "https://firebasestorage.googleapis.com/v0/b/hitmillionaire.appspot.com/o/flags.jpg?alt=media&token=cd9667e2-da32-4463-8fa3-26ef52457098";
        Glide.with(getApplicationContext()).load(urlSport).into(imageViewCategorySport);
        Glide.with(getApplicationContext()).load(urlHeroes).into(imageViewCategoryHeroes);
        Glide.with(getApplicationContext()).load(urlFlags).into(imageViewCategoryFlags);
        imageButtonLeaderboard = findViewById(R.id.imageViewLeaderboard);
        imageButtonLeaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoriesActivity.this , LeaderBoard.class);
                startActivity(intent);
            }
        });
        imageButtonUserSettings = findViewById(R.id.imageButtonUserSettings);
        imageButtonUserSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoriesActivity.this, UserSettingsActivity.class);
                startActivity(intent);
            }
        });

        imageViewCategorySport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoriesActivity.this,Start.class);
                intent.putExtra("CategoryID","01");
                startActivity(intent);
                finish();


            }
        });
        imageViewCategoryHeroes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CategoriesActivity.this,Start.class);
                intent.putExtra("CategoryID","02");
                startActivity(intent);
                finish();


            }
        });
        imageViewCategoryFlags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoriesActivity.this,Start.class);
                intent.putExtra("CategoryID","03");
                startActivity(intent);
                finish();

            }
        });
    }


}
