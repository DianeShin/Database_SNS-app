package com.example.db_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class createAccountAction extends AppCompatActivity {
    public static final String EXTRA_USERID = "com.example.db_project.USERID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_action);
    }

    public void checkUserID(View view){
        EditText userIDInput = (EditText) findViewById(R.id.VUUserIDInput);
        String userID = userIDInput.getText().toString();

        // sync DB
        syncFunctions.downloadUserRemoteDB(MainActivity.userDAOObject);
        List<String> existingUserIdArrayList = MainActivity.userDAOObject.loadID();

        // userID already exists -> invalid userID
        if(existingUserIdArrayList.contains(userID)){
            Intent invalidUserIDIntent = new Intent(this, invalidUserIDAction.class);
            startActivity(invalidUserIDIntent);
        }
        // userID doesn't exist -> valid userID
        else{
            Intent validUserIDIntent = new Intent(this, validUserIDAction.class);
            validUserIDIntent.putExtra(EXTRA_USERID, userID);
            startActivity(validUserIDIntent);
        }
    }

    // invoked when user didn't check ID, asking the user to check userID first.
    public void askIDCheck(View view){
        Intent askIDCheckIntent = new Intent(this, askIDCheckAction.class);
        startActivity(askIDCheckIntent);
    }
}

