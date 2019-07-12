package com.example.user.hitmillionaire11;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    ImageButton imageButtonVolume, imageButtonContact;
    Button imageButtonEnter;
    boolean volumeToggle = false;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(this, R.raw.millionaire);
        mediaPlayer.setLooping(true);

        mediaPlayer.start();
        imageButtonEnter = findViewById(R.id.imageButtonEnter);
        imageButtonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EnterActivity.class);
                intent.putExtra("volume", volumeToggle);
                startActivity(intent);
            }
        });

        imageButtonContact = findViewById(R.id.imageButtonContactUs);
        imageButtonContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ContactActivity.class);
                startActivity(intent);
            }
        });
        imageButtonVolume = findViewById(R.id.imageButtonSound);
        imageButtonVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ImageButton) v).setBackgroundResource(!volumeToggle ? R.drawable.ic_volume_off_black_24dp : R.drawable.ic_volume_on_black_24dp);
                if (!volumeToggle) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                } else {
                    mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.millionaire);
                    mediaPlayer.setLooping(true);

                    mediaPlayer.start();
                }
                volumeToggle = !volumeToggle;
            }
        });
    }
}
