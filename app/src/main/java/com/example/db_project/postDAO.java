package com.example.db_project;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface postDAO {
    @Query("SELECT * FROM post ORDER BY timestamp DESC")
    List<post> getAll();

    @Query("SELECT COUNT(*) FROM post")
    int getPostCount();

    @Query("SELECT postID FROM post")
    List<Integer> loadPostID();

    @Insert
    void insertPosts(post...posts);

    @Query("DELETE FROM post WHERE userID LIKE :userID")
    void deletePostFromUser(String userID);
}
