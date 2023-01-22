package com.example.db_project;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface userDAO {
    @Query("SELECT * FROM user")
    List<user> getAll();

    @Query("SELECT * FROM user WHERE userID = :userIDInput")
    user getUser(String userIDInput);

    @Query("SELECT userID FROM user")
    List<String> loadID();

    @Insert
    void insertUsers(user...users);

    @Delete
    void deleteUsers(user...users);
}
