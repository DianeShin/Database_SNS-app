package com.example.db_project;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;
import androidx.annotation.NonNull;

@Entity(foreignKeys = {@ForeignKey(entity = user.class,
                                   parentColumns = "userID",
                                   childColumns = "userID"),
                       @ForeignKey(entity = post.class,
                                   parentColumns = "postID",
                                   childColumns = "postID")},
        primaryKeys = {"userID","postID","timestamp"})
public class reaction {
    @NonNull
    @ColumnInfo(name = "userID")
    public String userID;

    @NonNull
    @ColumnInfo(name = "postID")
    public int postID;

    @ColumnInfo(name = "type")
    public int type;

    @NonNull
    @ColumnInfo(name = "timestamp")
    public String timestamp;
}
