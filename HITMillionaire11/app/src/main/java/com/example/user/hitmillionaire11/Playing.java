package com.example.user.hitmillionaire11;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Playing extends AppCompatActivity implements View.OnClickListener {

    CountDownTimer countDownTimer;
    final static long INTERVAL = 1000;
    final static long TIMEOUT = 60000;
    Button buttonCorrect = null;
    boolean isPause = false, isHalfHelpPressed = false;
    SharedPreferences sharedPreferences;
    int progressValue = 0;
    int index = 0, score = 0, thisQuestion = 1, totalQuestion, correctAnswer;
    String[] stringsScores;
    String scoreStr;
    int progress = 60;
    List<Button> notCorrectBtnList;

    FirebaseDatabase database;
    DatabaseReference questions;

    ProgressBar progressBar;
    ImageView question_image;
    Button btnA, btnB, btnC, btnD;
    TextView txtScore, txtQuestionNum, question_text, textViewClock;
    ImageButton imageButtonSkipQuestion, imageButtonHalf, imageButtonCall;
    LinearLayout linearLayout;
    android.support.constraint.Group ans_btn_group;

    boolean isSkipQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);

        sharedPreferences = getSharedPreferences("details", MODE_PRIVATE);

        stringsScores = new String[15];
        scoreStr = this.getString(R.string.score_text_view);
        initStringsScores();

        isSkipQuestion = false;
        database = FirebaseDatabase.getInstance();
        questions = database.getReference("Questions");

        question_text = findViewById(R.id.textView_question);
        txtQuestionNum = findViewById(R.id.textView_question_number);
        txtScore = findViewById(R.id.textViewSocre);
        txtScore.setText(scoreStr + " " + score);
        question_image = findViewById(R.id.imageView_question);


        progressBar = findViewById(R.id.progressBar2);
        btnA = findViewById(R.id.button_answerA);
        btnB = findViewById(R.id.button_answerB);
        btnC = findViewById(R.id.button_answerC);
        btnD = findViewById(R.id.button_answerD);

        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnD.setOnClickListener(this);

        imageButtonSkipQuestion = findViewById(R.id.imageButtonSkipQuestion);
        imageButtonHalf = findViewById(R.id.imageButton5050);
        imageButtonCall = findViewById(R.id.imageButtonCall);

        linearLayout = findViewById(R.id.linearLayout);

        textViewClock = findViewById(R.id.textViewClock);
        textViewClock.setText("60");

        question_text = findViewById(R.id.textView_question);

        ans_btn_group = findViewById(R.id.answers_btn_group);

        imageButtonHalf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isHalfHelpPressed = true;
                deleteIncorretAnswers();
            }
        });

        imageButtonSkipQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //skipQuestion();
                markCorretAnswer();
            }
        });

        imageButtonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleAssistant();
            }
        });
    }

    private void markCorretAnswer() {

        Drawable drawable = getResources().getDrawable(R.drawable.correct_answer_btn);
        isSkipQuestion = true;
        imageButtonSkipQuestion.setEnabled(false);
        imageButtonSkipQuestion.setVisibility(View.INVISIBLE);

        List<Button> btnsList = new ArrayList<>();
        buttonCorrect = new Button(this);
        btnsList.add(btnA);
        btnsList.add(btnB);
        btnsList.add(btnC);
        btnsList.add(btnD);

        for (Button button : btnsList) {
            if ((button.getText().toString().equals(Common.questionList.get(index).getCorrectAnswer())) ) {
                buttonCorrect = button;
                break;
            }

        }
        buttonCorrect.setBackground(drawable);


    }

    private void googleAssistant() {
        String query;
        String url = "http://www.google.com/#q=";
        query = Common.questionList.get(index).getCorrectAnswer();
        String final_url = url + query;
        Uri uri = Uri.parse(final_url);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
        imageButtonCall.setVisibility(View.INVISIBLE);
        imageButtonCall.setEnabled(false);
    }

    private void skipQuestion() {
        isSkipQuestion = true;
        index++;
        thisQuestion++;

        if (index < 15) {
            score = Integer.parseInt(stringsScores[index]);
        }
        if (index == 15) {
            score = Integer.parseInt(stringsScores[index - 1]);
            Intent intent = new Intent(Playing.this, Done.class);
            Bundle dataSend = new Bundle();
            dataSend.putInt("SCORE", score);
            dataSend.putInt("TOTAL", totalQuestion);
            dataSend.putInt("CORRECT", ++correctAnswer);
            intent.putExtras(dataSend);
            startActivity(intent);
            finish();
        }
        showQuestion(index);
        txtScore.setText(scoreStr + " " + score);
        imageButtonSkipQuestion.setEnabled(false);

        imageButtonSkipQuestion.setVisibility(View.INVISIBLE);
    }

    private void deleteIncorretAnswers() {
        int count = 0;
        String correctAnswer = Common.questionList.get(index).getCorrectAnswer();
        List<Button> btnsList = new ArrayList<>();
        notCorrectBtnList = new ArrayList<>();
        btnsList.add(btnA);
        btnsList.add(btnB);
        btnsList.add(btnC);
        btnsList.add(btnD);

        for (Button button : btnsList) {
            if ((!button.getText().toString().equals(correctAnswer)) && count < 2) {
                notCorrectBtnList.add(button);
                count++;
            }
        }

        for (Button button : notCorrectBtnList) {
            button.setEnabled(false);
            button.setTextColor(Color.BLACK);
        }

        imageButtonHalf.setEnabled(false);
        imageButtonHalf.setVisibility(View.INVISIBLE);
    }


    private void initStringsScores() {

        for (int i = 0; i < 15; i++) {
            stringsScores[0] = "100";
            stringsScores[1] = "200";
            stringsScores[2] = "300";
            stringsScores[3] = "500";
            stringsScores[4] = "1000";
            stringsScores[5] = "2000";
            stringsScores[6] = "4000";
            stringsScores[7] = "8000";
            stringsScores[8] = "16000";
            stringsScores[9] = "32000";
            stringsScores[10] = "6400";
            stringsScores[11] = "125000";
            stringsScores[12] = "250000";
            stringsScores[13] = "500000";
            stringsScores[14] = "1000000";
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        totalQuestion = Common.questionList.size();

        countDownTimer = new CountDownTimer(TIMEOUT, INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (isPause) {
                    cancel();

                }
                isPause = false;
                progressBar.setProgress(progressValue);
                progressValue++;
                progress = (Integer.parseInt(textViewClock.getText().toString()));
                textViewClock.setText((--progress + ""));
            }

            @Override
            public void onFinish() {
                countDownTimer.cancel();
                showQuestion(++index);
            }
        };
        showQuestion(index);
    }

    @Override
    public void onClick(View v) {

        countDownTimer.cancel();
        if (index < totalQuestion) {
            Button clickedButton = (Button) v;
            if (clickedButton.getText().equals(Common.questionList.get(index).getCorrectAnswer())) {
                thisQuestion++;
                score = Integer.parseInt(stringsScores[index]);
//                if (isSkipQuestion) {
//                    score = Integer.parseInt(stringsScores[++index]);
//                    isSkipQuestion = false;
//                    imageButtonSkipQuestion.setEnabled(false);
//                }
                txtScore.setText(scoreStr + " " + score);
                correctAnswer++;
                showQuestion(++index);
            } else {
                //New Activity - Finish the game
                Intent intent = new Intent(Playing.this, Done.class);
                Bundle dataSend = new Bundle();
                dataSend.putInt("SCORE", score);
                dataSend.putInt("TOTAL", totalQuestion);
                dataSend.putInt("CORRECT", correctAnswer);
                intent.putExtras(dataSend);
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        isPause = true;
        if (isHalfHelpPressed) {
            sharedPreferences.edit().putString("Button1", notCorrectBtnList.get(0).getText().toString()).putString("Button2", notCorrectBtnList.get(1).getText().toString()).commit();
        }

    }

    private void showQuestion(int i) {
        if (i < totalQuestion && progress > 0) {
            txtQuestionNum.setText(String.format("%d / %d", thisQuestion, totalQuestion));
            progressBar.setProgress(0);
            progressValue = 0;
            textViewClock.setText("60");

            if (Common.questionList.get(index).getIsImageQuestion().equals("true")) {
                Glide.with(getApplicationContext()).load(Common.questionList.get(index).getQuestion()).into(question_image);
                question_text.setVisibility(View.GONE);
                question_image.setVisibility(View.VISIBLE);
            } else {
                question_text.setText(Common.questionList.get(index).getQuestion());
                question_text.setVisibility(View.VISIBLE);
                question_image.setVisibility(View.INVISIBLE);
            }

            btnA.setText(Common.questionList.get(index).getAnswerA());
            btnB.setText(Common.questionList.get(index).getAnswerB());
            btnC.setText(Common.questionList.get(index).getAnswerC());
            btnD.setText(Common.questionList.get(index).getAnswerD());

            if (!isPause) {
                resetGameAttributes();
                countDownTimer.start();
            }
//            if(isSkipQuestion)
//            {
//                resetGameAttributes();
//                isSkipQuestion=false;
//            }
        } else {
            //New Activity - Finish the game
            Intent intent = new Intent(Playing.this, Done.class);
            Bundle dataSend = new Bundle();
            dataSend.putInt("SCORE", score);
            dataSend.putInt("TOTAL", totalQuestion);
            dataSend.putInt("CORRECT", correctAnswer);
            intent.putExtras(dataSend);
            startActivity(intent);
            finish();
        }
    }

    private void resetGameAttributes() {
        btnA.setEnabled(true);
        btnB.setEnabled(true);
        btnC.setEnabled(true);
        btnD.setEnabled(true);

        btnA.setTextColor(Color.WHITE);
        btnB.setTextColor(Color.WHITE);
        btnC.setTextColor(Color.WHITE);
        btnD.setTextColor(Color.WHITE);

        if(buttonCorrect != null){
            Drawable drawable = getResources().getDrawable(R.drawable.answer_btn_reset);

            buttonCorrect.setBackground(drawable);
            buttonCorrect=null;
        }
        initStringsScores();

        if (isHalfHelpPressed) {
            String button1 = sharedPreferences.getString("Button1", "");
            String button2 = sharedPreferences.getString("Button2", "");
            if (btnA.getText().toString().equals(button1) || btnA.getText().toString().equals(button2)) {
                btnA.setEnabled(false);
                btnA.setTextColor(Color.BLACK);
            }
            if (btnB.getText().toString().equals(button1) || btnB.getText().toString().equals(button2)) {
                btnB.setEnabled(false);
                btnB.setTextColor(Color.BLACK);
            }
            if (btnC.getText().toString().equals(button1) || btnC.getText().toString().equals(button2)) {
                btnC.setEnabled(false);
                btnC.setTextColor(Color.BLACK);
            }
            if (btnD.getText().toString().equals(button1) || btnD.getText().toString().equals(button2)) {
                btnD.setEnabled(false);
                btnD.setTextColor(Color.BLACK);
            }
            isHalfHelpPressed = false;
        }

    }
}
