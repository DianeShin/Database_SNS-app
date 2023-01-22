package com.example.db_project;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {user.class, post.class, reaction.class}, version = 1)
public abstract class database extends RoomDatabase {
    public abstract userDAO userDAOMethod();
    public abstract postDAO postDAOMethod();
    public abstract reactionDAO reactionDAOMethod();


}
