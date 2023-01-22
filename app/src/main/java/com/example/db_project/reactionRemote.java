package com.example.db_project;

import com.google.gson.annotations.SerializedName;

public class reactionRemote {
    @SerializedName("user_id")
    private String userID;

    @SerializedName("post_id")
    private int postID;

    @SerializedName("type")
    private int type;

    @SerializedName("stamp")
    private String timestamp;

    public reactionRemote(String userID, int postID, int type, String timestamp){
        this.userID = userID;
        this.postID = postID;
        this.type = type;
        this.timestamp = timestamp;
    }

    public String getUserID() {
        return userID;
    }

    public int getPostID() {
        return postID;
    }

    public int getType() {
        return type;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
