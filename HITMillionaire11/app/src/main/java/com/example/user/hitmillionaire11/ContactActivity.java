package com.example.user.hitmillionaire11;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ContactActivity extends AppCompatActivity {

    Button buttonSendMail, buttonRules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        buttonSendMail = findViewById(R.id.email_btn);
        buttonRules = findViewById(R.id.rules_btn);

        buttonSendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = "eranhd8793@gmail.com";
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{address});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Millionaire Feedback");
                intent.setType("text/html");
                startActivity(Intent.createChooser(intent, "Send Email with:"));

            }
        });

        buttonRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String site = "https://firebasestorage.googleapis.com/v0/b/hitmillionaire.appspot.com/o/rules.png?alt=media&token=f16832c8-cc67-4bde-9ffc-ec46236f77a6";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(site));
                startActivity(intent);

            }
        });
    }
}
