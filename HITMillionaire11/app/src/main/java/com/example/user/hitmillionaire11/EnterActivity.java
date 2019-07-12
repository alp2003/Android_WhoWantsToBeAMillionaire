package com.example.user.hitmillionaire11;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EnterActivity extends AppCompatActivity {


    boolean volumeToggle= false;
    //FireBase DataBase
    private FirebaseDatabase database;
    private DatabaseReference users;

    //User inputs for reg
    EditText editTextUsername;
    EditText editTextPassword;
    EditText editTextRepassword;
    EditText editTextEmail;
    EditText editTextPhone;

    //User inputs for login
    EditText editTextUsernameLogin;
    EditText editTextPasswordLogin;

    //Buttons
    Button imageButtonRegister, imageButtonLogin;
    ImageButton imageButtonLoginSubmit, imageButtonRegSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);

        //FireBase
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        volumeToggle = getIntent().getBooleanExtra("volume",false);


        //Login with exist user
        imageButtonLogin = findViewById(R.id.imageButtonLogin);
        imageButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(EnterActivity.this);
                final View diView = getLayoutInflater().inflate(R.layout.login_activity, null);
                editTextUsernameLogin = diView.findViewById(R.id.editTextUser_login);
                editTextPasswordLogin = diView.findViewById(R.id.editTextPassword_login);
                imageButtonLoginSubmit = diView.findViewById(R.id.imageButtonLogin_submit);

                imageButtonLoginSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String userName = editTextUsernameLogin.getText().toString();
                        final String password = editTextPasswordLogin.getText().toString();
                        users.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.child(userName).exists()) {
                                    if (!userName.isEmpty()) {
                                        User login = dataSnapshot.child(userName).getValue(User.class);
                                        if (login.getPassword().equals(password)) {
                                            Toast.makeText(EnterActivity.this, R.string.login_success, Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(EnterActivity.this, CategoriesActivity.class);
                                            Common.currentUser = login;
                                            startActivity(intent);
                                            finish();

                                        } else {
                                            Toast.makeText(EnterActivity.this, R.string.wrong_password, Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(EnterActivity.this, R.string.enter_username, Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(EnterActivity.this, R.string.user_not_exist, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                });
                builder.setView(diView).setPositiveButton(R.string.back, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == dialog.BUTTON_POSITIVE)
                            dialog.dismiss();
                    }
                }).show();

            }
        });

        //Create a new user
        imageButtonRegister = findViewById(R.id.imageButtonRegister);
        imageButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(EnterActivity.this);
                final View diView = getLayoutInflater().inflate(R.layout.registration_activity, null);
                editTextUsername = diView.findViewById(R.id.editTextUsername_signup);
                editTextPassword = diView.findViewById(R.id.editTextPassword_signup);
                editTextRepassword = diView.findViewById(R.id.editTextRepassword_signup);
                editTextEmail = diView.findViewById(R.id.editTextEmail_signup);
                editTextPhone = diView.findViewById(R.id.editTextPhone_signup);

                imageButtonRegSubmit = diView.findViewById(R.id.imageButtonSubmit);
                imageButtonRegSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String username = editTextUsername.getText().toString();
                        final String password = editTextPassword.getText().toString();
                        final String repassword = editTextRepassword.getText().toString();
                        final String phone = editTextPhone.getText().toString();
                        final String email = editTextEmail.getText().toString();
                        if (!checkRegInputs(username, password, repassword, phone, email)) {
                            return;
                        }

                        final User user = new User(username, email, password, phone);
                        users.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.child(user.getUsername()).exists()) {
                                    Toast.makeText(EnterActivity.this, R.string.user_exists, Toast.LENGTH_SHORT).show();
                                } else {
                                    users.child(user.getUsername()).setValue(user);
                                    Toast.makeText(EnterActivity.this, R.string.registration_complete, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(EnterActivity.this, CategoriesActivity.class);
                                    Common.currentUser = user;
                                    startActivity(intent);
                                    finish();

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });
                builder.setView(diView).setPositiveButton(R.string.back, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == dialog.BUTTON_POSITIVE)
                            dialog.dismiss();
                    }
                }).show();

            }
        });
    }

    private Boolean checkRegInputs(String username, String password, String repassword, String phone, String email) {

        if (username.isEmpty() || password.isEmpty() || repassword.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, R.string.registration_inputs, Toast.LENGTH_SHORT).show();

            return false;
        }
        if (username.trim().length() == 0) {
            Toast.makeText(this, R.string.enter_username, Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.trim().length() == 0 || repassword.trim().length() == 0) {
            Toast.makeText(this, R.string.edit_txt_password, Toast.LENGTH_SHORT).show();
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


