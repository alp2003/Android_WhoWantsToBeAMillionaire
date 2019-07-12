package com.example.user.hitmillionaire11;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class LeaderBoard extends AppCompatActivity {

    //FireBase DataBase
    private FirebaseDatabase database;
    private DatabaseReference users;
    ArrayList<User> userList = new ArrayList<>();
    ListView listViewName, listViewScores;
    Button buttonSortBy;
    String[] stringsNames, stringsScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        listViewName = findViewById(R.id.list_view_names);
        listViewScores = findViewById(R.id.list_view_scores);
        buttonSortBy = findViewById(R.id.buttonSortBy);


        //Fire base reference to Users:
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshotIt : dataSnapshot.getChildren()) {
                    String username = snapshotIt.child("username").getValue().toString();
                    String email = snapshotIt.child("email").getValue().toString();
                    String phone = snapshotIt.child("phoneNumber").getValue().toString();
                    String password = snapshotIt.child("password").getValue().toString();
                    String score = snapshotIt.child("score").getValue().toString();

                    User user = new User(username, email, password, phone);
                    user.setScore(score);
                    userList.add(user);
                }
                Collections.sort(userList, new ScoreComrator());
                Collections.reverse(userList);
                initalListView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        buttonSortBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(LeaderBoard.this, v);
                getMenuInflater().inflate(R.menu.menu_sortby, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.sortby_name:
                                sortByName();
                                break;
                            case R.id.sortby_score:
                                sortByScore();
                                break;

                        }

                        return false;
                    }
                });
                popupMenu.show();
            }
        });

    }

    private void sortByScore() {
        Collections.sort(userList, new ScoreComrator());
        Collections.reverse(userList);
        initalListView();

    }

    private void sortByName() {

        Collections.sort(userList, new UserComparator());
        initalListView();

    }

    private void initalListView() {
        stringsNames = new String[userList.size()];
        stringsScores = new String[userList.size()];
        for (int i = 0; i < userList.size(); i++) {
            stringsNames[i] = userList.get(i).getUsername();
            stringsScores[i] = userList.get(i).getScore();
        }
        ArrayAdapter<String> stringArrayScoresAdapter = new ArrayAdapter(LeaderBoard.this, android.R.layout.simple_list_item_1, stringsScores) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = view.findViewById(android.R.id.text1);
                textView.setTextColor(Color.WHITE);
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(20f);
                return view;

            }
        };
        ArrayAdapter<String> stringArrayNamesAdapter = new ArrayAdapter(LeaderBoard.this, android.R.layout.simple_list_item_1, stringsNames) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = view.findViewById(android.R.id.text1);
                textView.setTextColor(Color.WHITE);
                textView.setTextSize(20f);
                textView.setGravity(Gravity.CENTER);
                return view;

            }
        };
        listViewScores.setAdapter(stringArrayScoresAdapter);
        listViewName.setAdapter(stringArrayNamesAdapter);
    }
}
