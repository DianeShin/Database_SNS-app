package com.example.db_project;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;
import androidx.annotation.NonNull;

@Entity(foreignKeys = {@ForeignKey(entity = user.class,
                                   parentColumns = "userID",
                                   childColumns = "userID")},
        indices = {@Index("postID")})

public class post {
    @PrimaryKey
    @NonNull
    public int postID;

    @ColumnInfo(name = "userID")
    public String userID;

    @ColumnInfo(name = "content")
    public String content;

    @ColumnInfo(name = "timestamp")
    public String timestamp;
}
