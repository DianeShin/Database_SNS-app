package com.example.db_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public static database db;
    public static userDAO userDAOObject;
    public static postDAO postDAOObject;
    public static reactionDAO reactionDAOObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // default
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // DB init
        db = Room.databaseBuilder(getApplicationContext(), database.class, "db").allowMainThreadQueries().build();
        userDAOObject = db.userDAOMethod();
        postDAOObject = db.postDAOMethod();
        reactionDAOObject = db.reactionDAOMethod();

        // DB load
        syncFunctions.downloadReactionRemoteDB(reactionDAOObject);
        syncFunctions.downloadPostRemoteDB(postDAOObject);
        syncFunctions.downloadUserRemoteDB(userDAOObject);
    }

    public void createAccount(View view){
        Intent intent = new Intent(this, createAccountAction.class);
        startActivity(intent);
    }

    public void login(View view){
        Intent intent = new Intent(this, loginAction.class);
        startActivity(intent);
    }
}