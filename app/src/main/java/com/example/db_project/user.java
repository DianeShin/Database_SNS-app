package com.example.db_project;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index("userID")})

public class user {
    @PrimaryKey
    @NonNull
    public String userID;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "timestamp")
    public String timestamp;

}
