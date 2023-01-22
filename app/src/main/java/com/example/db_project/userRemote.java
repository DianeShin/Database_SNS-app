package com.example.db_project;

import com.google.gson.annotations.SerializedName;

public class userRemote {
    @SerializedName("id")
    private String userID;

    @SerializedName("name")
    private String name;

    @SerializedName("stamp")
    private String timestamp;

    public userRemote(String userID, String name, String timestamp){
        this.userID = userID;
        this.name = name;
        this.timestamp = timestamp;
    }

    public String getUserID(){
        return userID;
    }
    public String getName(){
        return name;
    }
    public String getTimestamp(){
        return timestamp;
    }
    public void setUserID(String userID) { this.userID = userID; }
    public void setName(String name) { this.name = name; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}
