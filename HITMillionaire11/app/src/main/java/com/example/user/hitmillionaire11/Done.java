package com.example.user.hitmillionaire11;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.Locale;

public class Done extends AppCompatActivity {

    String mobileLanguge;
    private Locale locale;

    Button btnTryAgain;
    TextView txtResultScore, txtPassedQuestions;
    ProgressBar progressBar;

    FirebaseDatabase database;
    DatabaseReference question_score, updateUserScore;

    ObjectAnimator textColorAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);


        mobileLanguge = locale.getDefault().getLanguage();
        txtResultScore = findViewById(R.id.textViewScore_done);
        txtPassedQuestions = findViewById(R.id.textViewPassed_done);


        database = FirebaseDatabase.getInstance();
        question_score = database.getReference("Question_Score");
        updateUserScore = database.getReference("Users");
        btnTryAgain = findViewById(R.id.buttonTryAgain);
        progressBar = findViewById(R.id.progressBar_done);

        progressBar.getProgressDrawable().setColorFilter(
                Color.YELLOW, android.graphics.PorterDuff.Mode.SRC_IN);


        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Done.this, CategoriesActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            int score = extra.getInt("SCORE");
            int totalQuestion = extra.getInt("TOTAL");
            int correctAnswer = extra.getInt("CORRECT");

            progressBar.setMax(totalQuestion);
            progressBar.setProgress(correctAnswer);

            question_score.child(String.format("%s_%s", Common.currentUser.getUsername(),
                    Common.categoryID)).
                    setValue(new QuestionScore
                            (String.format("%s_%s",
                                    Common.currentUser.getUsername(),
                                    Common.categoryID),
                                    Common.currentUser.getUsername(),
                                    String.valueOf(score)));

            txtResultScore.setText(txtResultScore.getText().toString() + " " + score);
            if(score==1000000)
            {
                initTextEffect();
            }
            int userCurrentScore = Integer.parseInt(Common.currentUser.getScore());
            if (mobileLanguge.equals("en")) {
                txtPassedQuestions.setText(txtPassedQuestions.getText().toString() + String.format("%d / %d", correctAnswer, Common.questionList.size()));
            } else {
                txtPassedQuestions.setText(txtPassedQuestions.getText().toString() + String.format("%d / %d", Common.questionList.size(), correctAnswer));

            }
            if (userCurrentScore < score) {
                updateUserScore.child(Common.currentUser.getUsername()).child("score").setValue(score + "");
            }
        }
    }

    private void initTextEffect() {
        textColorAnim = ObjectAnimator.ofInt(txtResultScore, "textColor", Color.WHITE, Color.TRANSPARENT);
        textColorAnim.setDuration(1000);
        textColorAnim.setEvaluator(new ArgbEvaluator());
        textColorAnim.setRepeatCount(ValueAnimator.INFINITE);
        textColorAnim.setRepeatMode(ValueAnimator.REVERSE);
        textColorAnim.start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.category_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(Done.this, Start.class);
        Intent intent2 = new Intent(Done.this, EnterActivity.class);
        boolean logout = false;

        switch (item.getItemId()) {
            case R.id.menu_flags:
                intent.putExtra("CategoryID", "03");
                break;
            case R.id.menu_hero:
                intent.putExtra("CategoryID", "02");
                break;
            case R.id.menu_sport:
                intent.putExtra("CategoryID", "01");
                break;
            case R.id.menu_logout:
                logout = true;
                break;

        }
        if (logout) {
            startActivity(intent2);
            finish();
        } else {
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
