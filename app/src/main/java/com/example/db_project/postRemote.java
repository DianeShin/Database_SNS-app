package com.example.db_project;

import com.google.gson.annotations.SerializedName;

public class postRemote {
    @SerializedName("id")
    private int postID;

    @SerializedName("user_id")
    private String userID;

    @SerializedName("content")
    private String content;

    @SerializedName("stamp")
    private String timestamp;

    public postRemote(int postID, String userID, String content, String timestamp){
        this.postID = postID;
        this.userID = userID;
        this.content = content;
        this.timestamp = timestamp;
    }

    public int getPostID() {
        return postID;
    }

    public String getUserID() {
        return userID;
    }

    public String getContent() {
        return content;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
