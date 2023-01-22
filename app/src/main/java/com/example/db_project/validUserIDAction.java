package com.example.db_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Timestamp;
import java.util.List;

public class validUserIDAction extends AppCompatActivity {
    public static final String EXTRA_USERID = "com.example.db_project.USERID";
   String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valid_user_id);

        // get info from textbox
        Intent containUserIdIntent = getIntent();
        userID = containUserIdIntent.getStringExtra(EXTRA_USERID);
        TextView VUUserIDText = findViewById(R.id.VUCurrentID);
        VUUserIDText.setText(userID);
    }

    public void checkUserID(View view){
        // find new userID
        EditText userIDInput = (EditText) findViewById(R.id.VUUserIDInput);
        String userID = userIDInput.getText().toString();

        // sync userDB
        syncFunctions.downloadUserRemoteDB(MainActivity.userDAOObject);
        List<String> existingUserIdArrayList = MainActivity.userDAOObject.loadID();

        // check if ID already exists.
        if(existingUserIdArrayList.contains(userID)){
            Intent invalidUserIDIntent = new Intent(this, invalidUserIDAction.class);
            startActivity(invalidUserIDIntent);
        }
        else{
            Intent validUserIDIntent = new Intent(this, validUserIDAction.class);
            validUserIDIntent.putExtra(EXTRA_USERID, userID);
            startActivity(validUserIDIntent);
        }
    }

    public void createAccount(View view){
        // create timestamp
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // get previously set userID
        EditText nameInput = (EditText) findViewById(R.id.VUNameInput);
        String name = nameInput.getText().toString();

        // create new Account instance
        user newAccount = new user();
        newAccount.userID = userID;
        newAccount.name = name;
        newAccount.timestamp = timestamp.toString();

        // insert in both local and remote db
        MainActivity.userDAOObject.insertUsers(newAccount);
        syncFunctions.postUserRemoteDB(syncFunctions.userToRemoteUser(newAccount));

        // start next intent
        Intent createAccountSuccessIntent = new Intent(this, createAccountSuccessAction.class);
        startActivity(createAccountSuccessIntent);
    }
}