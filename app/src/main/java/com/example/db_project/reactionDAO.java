package com.example.db_project;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


@Dao
public interface reactionDAO {
    @Query("SELECT * FROM reaction WHERE userID = :userIDInput AND postID = :postIDInput ORDER BY timestamp DESC LIMIT 1")
    reaction checkReaction(String userIDInput, int postIDInput);

    @Query("SELECT COUNT(*) FROM (SELECT type, MAX(timestamp) FROM reaction WHERE postID = :postIDInput GROUP BY userID) WHERE type LIKE '1'")
    int getLoveReactionCount(int postIDInput);

    @Query("SELECT COUNT(*) FROM (SELECT type, MAX(timestamp) FROM reaction WHERE postID = :postIDInput GROUP BY userID) WHERE type LIKE '2'")
    int getHateReactionCount(int postIDInput);

    @Query("SELECT COUNT(*) FROM (SELECT type, MAX(timestamp) FROM reaction WHERE postID = :postIDInput GROUP BY userID) WHERE type LIKE '3'")
    int getCclReactionCount(int postIDInput);

    @Query("DELETE FROM reaction")
    void deleteAllReactions();

    @Insert
    void insertReactions(reaction...reactions);

    @Query("DELETE FROM reaction WHERE postID = :postID")
    void deleteReactionForPost(int postID);
}
