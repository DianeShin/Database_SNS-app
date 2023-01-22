package com.example.db_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class fakeAction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake_action);
        Intent pastIntent = getIntent();
        Bundle extraBundle = pastIntent.getExtras();
        Intent updateReactionNumber = new Intent(this, mainPageAction.class);
        updateReactionNumber.putExtras(extraBundle);
        updateReactionNumber.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(updateReactionNumber);
    }
}