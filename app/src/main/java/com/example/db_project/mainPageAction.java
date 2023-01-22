package com.example.db_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class mainPageAction extends AppCompatActivity {
    public static final String EXTRA_USERID = "com.example.db_project.USERID";
    public static final String EXTRA_POST_COUNT = "com.example.db_project.postCount";

    private Bundle extraBundle;

    private post currPost;
    private String userID;
    private int postCount;
    private int numberOfPosts;
    private List<post> listOfPosts = new ArrayList<>();
    private int loveCount = 0;
    private int hateCount = 0;
    private int cclCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page_action);

        // get userID
        Intent pastIntent = getIntent();
        extraBundle = pastIntent.getExtras();
        userID = extraBundle.getString(EXTRA_USERID);

        // display userID
        TextView MPAuserIDText = findViewById(R.id.MPAuserID);
        MPAuserIDText.setText(userID);

        // get new post in DB
        syncFunctions.downloadPostRemoteDB(MainActivity.postDAOObject);
        listOfPosts = MainActivity.postDAOObject.getAll();
        numberOfPosts = listOfPosts.size();

        // load post to display, empty if no post exists.
        postCount = Integer.parseInt(extraBundle.getString(EXTRA_POST_COUNT));
        if(numberOfPosts == 0){
            currPost = new post();
            currPost.timestamp = "";
            currPost.userID = "";
            currPost.content = "There are no posts to load.";
            currPost.postID = Integer.MIN_VALUE;
        }
        else{
            currPost = listOfPosts.get(postCount);
        }

        // display post
        TextView MPApostWriterText = findViewById(R.id.MPApostWriter);
        MPApostWriterText.setText(currPost.userID);
        MPApostWriterText.setMovementMethod(new ScrollingMovementMethod());
        TextView MPApostTimestampText = findViewById(R.id.MPApostTimestamp);
        MPApostTimestampText.setText(currPost.timestamp);
        TextView MPApostContentText = findViewById(R.id.MPApostContent);
        MPApostContentText.setText(currPost.content);
        MPApostContentText.setMovementMethod(new ScrollingMovementMethod());

        // get reaction count, 0 if no post exists(by default)
        syncFunctions.downloadReactionRemoteDB(MainActivity.reactionDAOObject);
        if (currPost.postID != Integer.MIN_VALUE){
            loveCount = MainActivity.reactionDAOObject.getLoveReactionCount(currPost.postID);
            hateCount = MainActivity.reactionDAOObject.getHateReactionCount(currPost.postID);
            cclCount = MainActivity.reactionDAOObject.getCclReactionCount(currPost.postID);
        }

        // display reaction count
        TextView MPALoveCountText = findViewById(R.id.MPALoveCount);
        MPALoveCountText.setText(Integer.toString(loveCount));
        TextView MPAHateCountText = findViewById(R.id.MPAHateCount);
        MPAHateCountText.setText(Integer.toString(hateCount));
        TextView MPACclCountText = findViewById(R.id.MPACclCount);
        MPACclCountText.setText(Integer.toString(cclCount));
    }

    public void createPost(View view){
        Intent createPostIntent = new Intent(this, createPostAction.class);
        // put userID and current post Count
        createPostIntent.putExtras(extraBundle);
        startActivity(createPostIntent);
    }

    public void showPreviousPost(View view){
        if(postCount > 0){
            Intent showPreviousPostIntent = new Intent(this, mainPageAction.class);

            // put userID and post Count
            Bundle extraBundle = new Bundle();
            extraBundle.putString(EXTRA_USERID, userID);
            extraBundle.putString(EXTRA_POST_COUNT, Integer.toString(postCount-1));
            showPreviousPostIntent.putExtras(extraBundle);
            showPreviousPostIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(showPreviousPostIntent);
        }
    }

    public void showNextPost(View view){
        if(postCount < numberOfPosts -1){
            Intent showNextPostIntent = new Intent(this, mainPageAction.class);

            // put userID and post Count
            Bundle extraBundle = new Bundle();
            extraBundle.putString(EXTRA_USERID, userID);
            extraBundle.putString(EXTRA_POST_COUNT, Integer.toString(postCount+1));
            showNextPostIntent.putExtras(extraBundle);
            showNextPostIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(showNextPostIntent);
        }
    }

    public void likeReaction(View view){
        reaction newReaction = new reaction();
        if (currPost.postID != Integer.MIN_VALUE){
            // add type info of newReaction
            try{
                // gets most recent reaction
                reaction mostRecentReaction = MainActivity.reactionDAOObject.checkReaction(userID, currPost.postID);

                if (mostRecentReaction.type == 1){
                    newReaction.type = 0;
                }
                else{
                    newReaction.type = 1;
                }
            } catch (Exception e){
                // this triggers when there were no prior reaction, so we're inserting a new reaction
                newReaction.type = 1;
            } finally{
                // add other info of newReaction
                newReaction.userID = userID;
                newReaction.postID = currPost.postID;
                newReaction.timestamp = new Timestamp(System.currentTimeMillis()).toString();

                // sync DB
                syncFunctions.postReactionRemoteDB(syncFunctions.reactionToRemoteReaction(newReaction));
                syncFunctions.downloadReactionRemoteDB(MainActivity.reactionDAOObject);

                // start next intent
                Intent updateReactionNumber = new Intent(this, fakeAction.class);
                updateReactionNumber.putExtras(extraBundle);
                updateReactionNumber.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(updateReactionNumber);
            }
        }
    }

    public void hateReaction(View view){
        reaction newReaction = new reaction();
        if (currPost.postID != Integer.MIN_VALUE){
            // add type info of newReaction
            try{
                // gets most recent reaction
                reaction mostRecentReaction = MainActivity.reactionDAOObject.checkReaction(userID, currPost.postID);

                if (mostRecentReaction.type == 2){
                    newReaction.type = 0;
                }
                else{
                    newReaction.type = 2;
                }
            } catch (Exception e){
                // this triggers when there were no prior reaction, so we're inserting a new reaction
                newReaction.type = 2;
            } finally{
                // add other info of newReaction
                newReaction.userID = userID;
                newReaction.postID = currPost.postID;
                newReaction.timestamp = new Timestamp(System.currentTimeMillis()).toString();

                // sync DB
                syncFunctions.postReactionRemoteDB(syncFunctions.reactionToRemoteReaction(newReaction));
                syncFunctions.downloadReactionRemoteDB(MainActivity.reactionDAOObject);

                // start next intent
                Intent updateReactionNumber = new Intent(this, fakeAction.class);
                updateReactionNumber.putExtras(extraBundle);
                updateReactionNumber.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(updateReactionNumber);
            }
        }

    }

    public void cclReaction(View view) {
        reaction newReaction = new reaction();
        if (currPost.postID != Integer.MIN_VALUE){
            // add type info of newReaction
            try{
                // gets most recent reaction
                reaction mostRecentReaction = MainActivity.reactionDAOObject.checkReaction(userID, currPost.postID);

                if (mostRecentReaction.type == 3){
                    newReaction.type = 0;
                }
                else{
                    newReaction.type = 3;
                }
            } catch (Exception e){
                // this triggers when there were no prior reaction, so we're inserting a new reaction
                newReaction.type = 3;
            } finally{
                // add other info of newReaction
                newReaction.userID = userID;
                newReaction.postID = currPost.postID;
                newReaction.timestamp = new Timestamp(System.currentTimeMillis()).toString();

                // sync DB
                syncFunctions.postReactionRemoteDB(syncFunctions.reactionToRemoteReaction(newReaction));
                syncFunctions.downloadReactionRemoteDB(MainActivity.reactionDAOObject);

                // start next intent
                Intent updateReactionNumber = new Intent(this, fakeAction.class);
                updateReactionNumber.putExtras(extraBundle);
                updateReactionNumber.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(updateReactionNumber);
            }
        }

    }

    public void logout(View view){
        Intent logoutIntent = new Intent(this, MainActivity.class);
        startActivity(logoutIntent);
    }

    public void deleteAccount(View view){
        // remove related tuples from both DB
        syncFunctions.deleteReactionFromUserFromRemoteDB(userID, MainActivity.reactionDAOObject);
        syncFunctions.getUserPostAndDeleteRelatedReaction(userID, MainActivity.reactionDAOObject);
        syncFunctions.deletePostFromUserFromRemoteDB(userID, MainActivity.postDAOObject);
        syncFunctions.deleteUserFromRemoteDB(userID, MainActivity.userDAOObject);

        // return to start menu
        Intent deleteAccountIntent = new Intent(this, MainActivity.class);
        startActivity(deleteAccountIntent);
    }

    public void refresh(View view){
        Intent refresh = new Intent(this, mainPageAction.class);

        // put userID and initial post Count
        Bundle extraBundle = new Bundle();
        extraBundle.putString(EXTRA_USERID, userID);
        extraBundle.putString(EXTRA_POST_COUNT, Integer.toString(0));
        refresh .putExtras(extraBundle);

        startActivity(refresh);
    }
}