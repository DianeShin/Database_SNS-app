package com.example.db_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class createAccountSuccessAction extends AppCompatActivity {

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_success_action);
    }

    public void returnMainPage(View view){
        Intent returnMainPageIntent = new Intent(this, MainActivity.class);
        startActivity(returnMainPageIntent);
    }

    public void loginNow(View view){
        Intent loginNowIntent = new Intent(this, loginAction.class);
        startActivity(loginNowIntent);
    }
}