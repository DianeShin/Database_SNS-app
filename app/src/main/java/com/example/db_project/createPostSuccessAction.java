
package com.example.db_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class createPostSuccessAction extends AppCompatActivity {
    public static final String EXTRA_USERID = "com.example.db_project.USERID";
    public static final String EXTRA_POST_COUNT = "com.example.db_project.postCount";
    private Bundle extraBundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post_success_action);
        Intent pastIntent = getIntent();
        extraBundle = pastIntent.getExtras();
    }
    public void returnMainPage(View view){
        Intent returnMainPageIntent = new Intent(this, mainPageAction.class);
        Bundle newBundle = new Bundle();
        newBundle.putString(EXTRA_USERID, extraBundle.getString(EXTRA_USERID));
        newBundle.putString(EXTRA_POST_COUNT, String.valueOf(Integer.parseInt(extraBundle.getString(EXTRA_POST_COUNT))+1));
        // put bundle
        returnMainPageIntent.putExtras(newBundle);

        startActivity(returnMainPageIntent);
    }
}