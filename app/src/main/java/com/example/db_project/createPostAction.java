package com.example.db_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

public class createPostAction extends AppCompatActivity {
    public static final String EXTRA_USERID = "com.example.db_project.USERID";

    private Context context;
    private String userID;
    private Bundle extraBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post_action);

        context = getApplicationContext();

        Intent pastIntent = getIntent();
        extraBundle = pastIntent.getExtras();
        userID = extraBundle.getString(EXTRA_USERID);

    }

    public void createPost(View view){
        // set post timestamp
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // set post content
        EditText postContentEditText = (EditText) findViewById(R.id.MPAPostInput);
        String postContent = postContentEditText.getText().toString();

        // set post id
        List<Integer> postIDList = MainActivity.postDAOObject.loadPostID();
        int postID;
        if (postIDList.isEmpty()) postID = 0;
        else postID = Collections.max(postIDList) + 1;


        // insert new post to db
        post newPost = new post();
        newPost.postID = postID;
        newPost.content = postContent;
        newPost.userID = userID;
        newPost.timestamp = timestamp.toString();

        // sync DB
        syncFunctions.postPostRemoteDB(syncFunctions.postToRemotePost(newPost));
        syncFunctions.downloadPostRemoteDB(MainActivity.postDAOObject);

        // start next action
        Intent createPostIntent = new Intent(this, createPostSuccessAction.class);
        createPostIntent.putExtras(extraBundle);
        startActivity(createPostIntent);

    }
}