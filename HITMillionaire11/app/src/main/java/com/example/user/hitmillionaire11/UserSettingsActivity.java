package com.example.user.hitmillionaire11;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserSettingsActivity extends AppCompatActivity {

    ProgressBar progressBar;

    //FireBase DataBase
    private FirebaseDatabase database;
    private DatabaseReference users;


    EditText editTextEmail;
    EditText editTextPassoword;
    EditText editTextRePassword;
    EditText editTextPhone;

    ImageButton imageButtonSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        editTextEmail = findViewById(R.id.editTextEmail_editor);
        editTextPassoword = findViewById(R.id.editTextPassword_editor);
        editTextRePassword = findViewById(R.id.editTextRepassword_editor);
        editTextPhone = findViewById(R.id.editTextPhone_editor);
        imageButtonSubmit = findViewById(R.id.imageButton_editor_submit);

        //Init Edit text
        editTextEmail.setText(Common.currentUser.getEmail());
        editTextPassoword.setText(Common.currentUser.getPassword());
        editTextRePassword.setText(Common.currentUser.getPassword());
        editTextPhone.setText(Common.currentUser.getPhoneNumber());

        //Set lisnter to save
        imageButtonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassoword.getText().toString();
                String rePassword = editTextRePassword.getText().toString();
                String phoneNumber = editTextPhone.getText().toString();

                if(!checkRegInputs(password,rePassword,phoneNumber,email))
                {
                    return;
                }
                Common.currentUser.setEmail(email);
                Common.currentUser.setPassword(password);
                Common.currentUser.setPhoneNumber(phoneNumber);
                users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        DatabaseReference databaseReference = database.getReference("Users").child(Common.currentUser.getUsername());
                        databaseReference.setValue(Common.currentUser);
                        }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                ProgressBarAnimation animation = new ProgressBarAnimation(UserSettingsActivity.this,progressBar,0f,100f);
                animation.setDuration(5000);
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setAnimation(animation);
                imageButtonSubmit.setActivated(false);
                Toast.makeText(UserSettingsActivity.this, R.string.save_toast, Toast.LENGTH_LONG).show();
                imageButtonSubmit.setVisibility(View.INVISIBLE);

            }

        });
    }

    private Boolean checkRegInputs(String password, String repassword, String phone, String email) {
        if (password.trim().length() == 0 || repassword.trim().length() == 0) {
            Toast.makeText(this, R.string.registration_inputs, Toast.LENGTH_SHORT).show();
            return false;
        } else if (!password.equals(repassword)) {
            Toast.makeText(this, R.string.unmatch_passwords, Toast.LENGTH_SHORT).show();
            return false;
        } else if (!email.matches("^[a-zA-Z0-9]+@[a-zA-Z0-9]+(.[a-zA-Z]{2,})$")) {
            Toast.makeText(this, R.string.not_valid_mail, Toast.LENGTH_SHORT).show();
            return false;
        } else if (phone.length() != 10) {
            Toast.makeText(this, R.string.phone_digits, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
