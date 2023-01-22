package com.example.db_project;

import android.content.Context;
import android.util.Log;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class syncFunctions {

    public static void downloadUserRemoteDB(userDAO userDAOObject) {
        Call<List<userRemote>> call = RetrofitClient.getApi().getAllUsers();
        call.enqueue(new Callback<List<userRemote>>() {

            @Override
            public void onResponse(Call<List<userRemote>> call, Response<List<userRemote>> response) {
                List<userRemote> userRemoteInRemoteDB = response.body();
                if(userRemoteInRemoteDB != null){
                    // userRemote to user
                    List<user> userInRemoteDB = userRemoteInRemoteDB.stream().map(syncFunctions::userRemoteToUser).collect(Collectors.toList());

                    userInRemoteDB.forEach(elem -> {
                        try{
                            userDAOObject.insertUsers(elem);
                        } catch(Exception ignored){ ; }
                        }
                    );
                }
            }

            @Override
            public void onFailure(Call<List<userRemote>> call, Throwable t) { }

        });
    }



    public static void downloadPostRemoteDB(postDAO postDAOObject) {
        Call<List<postRemote>> call = RetrofitClient.getApi().getAllPosts();
        call.enqueue(new Callback<List<postRemote>>() {

            @Override
            public void onResponse(Call<List<postRemote>> call, Response<List<postRemote>> response) {
                List<postRemote> postRemoteInRemoteDB = response.body();
                if(postRemoteInRemoteDB != null){
                    List<post> postInRemoteDB = postRemoteInRemoteDB.stream().map(syncFunctions::postRemoteToPost).collect(Collectors.toList());

                    postInRemoteDB.forEach(elem -> {
                                try{
                                    postDAOObject.insertPosts(elem);
                                } catch(Exception e){ ; }
                            }
                    );
                }
            }

            @Override
            public void onFailure(Call<List<postRemote>> call, Throwable t) { }

        });
    }


    public static void downloadReactionRemoteDB(reactionDAO reactionDAOObject) {
        Call<List<reactionRemote>> call = RetrofitClient.getApi().getAllReactions();
        call.enqueue(new Callback<List<reactionRemote>>() {

            @Override
            public void onResponse(Call<List<reactionRemote>> call, Response<List<reactionRemote>> response) {
                List<reactionRemote> reactionRemoteInRemoteDB = response.body();
                if(reactionRemoteInRemoteDB != null){
                    List<reaction> reactionInRemoteDB = reactionRemoteInRemoteDB.stream().map(syncFunctions::reactionRemoteToReaction).collect(Collectors.toList());

                    reactionInRemoteDB.forEach(elem -> {
                                try{
                                    reactionDAOObject.insertReactions(elem);
                                } catch(Exception e){ ; }
                            }
                    );
                }
            }

            @Override
            public void onFailure(Call<List<reactionRemote>> call, Throwable t) { }
        });
    }

        // reactions that the user made
    public static void deleteReactionFromUserFromRemoteDB(String userID, reactionDAO reactionDAOObject){
        Call<ResponseBody> call = RetrofitClient.getApi().deleteReactionFromUser("eq." + userID);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) { }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) { }
        });
        reactionDAOObject.deleteAllReactions();
        syncFunctions.downloadReactionRemoteDB(reactionDAOObject);
    }

    public static void getUserPostAndDeleteRelatedReaction(String userID, reactionDAO reactionDAOObject){
        Call<List<postRemote>> call = RetrofitClient.getApi().getPostFromUser("eq." + userID);
        call.enqueue(new Callback<List<postRemote>>() {
            @Override
            public void onResponse(Call<List<postRemote>> call, Response<List<postRemote>> response) {
                List<postRemote> result = response.body();
                for(postRemote elem : result){
                    deleteReactionForUserPostFromRemoteDB(elem.getPostID(), reactionDAOObject);
                }
            }

            @Override
            public void onFailure(Call<List<postRemote>> call, Throwable t) { }

        });
        reactionDAOObject.deleteAllReactions();
        syncFunctions.downloadReactionRemoteDB(reactionDAOObject);
    }
    // reactions that were made on user's post
    public static void deleteReactionForUserPostFromRemoteDB(int postID, reactionDAO reactionDAOObject){
        Call<ResponseBody> call = RetrofitClient.getApi().deleteReactionFromPostFromUser("eq."+ String.valueOf(postID));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) { }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) { }

        });
        reactionDAOObject.deleteReactionForPost(postID);
    }
    // the posts that the user made
    public static void deletePostFromUserFromRemoteDB(String userID, postDAO postDAOObject){
        Call<ResponseBody> call = RetrofitClient.getApi().deletePostFromUser("eq." + userID);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
        postDAOObject.deletePostFromUser(userID);
    }
    // user itself
    public static void deleteUserFromRemoteDB(String userID, userDAO userDAOObject){
        Call<ResponseBody> call = RetrofitClient.getApi().deleteUser("eq." + userID);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) { }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) { }
        });
        userDAOObject.deleteUsers(userDAOObject.getUser(userID));
    }

    public static void postUserRemoteDB(userRemote userToInsert) {
        Call<ResponseBody> call = RetrofitClient.getApi().insertUser(userToInsert);
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }

        });
    }

    public static void postPostRemoteDB(postRemote postToInsert) {
        Call<ResponseBody> call = RetrofitClient.getApi().insertPost(postToInsert);
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }

        });
    }

    public static void postReactionRemoteDB(reactionRemote reactionToInsert) {
        Call<ResponseBody> call = RetrofitClient.getApi().insertReaction(reactionToInsert);
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }

        });
    }

    public static user userRemoteToUser(userRemote userInput){
        user resultUser = new user();
        resultUser.name = userInput.getName();
        resultUser.userID = userInput.getUserID();
        resultUser.timestamp = userInput.getTimestamp();
        return resultUser;
    }

    public static userRemote userToRemoteUser(user userInput){
        return new userRemote(userInput.userID, userInput.name, userInput.timestamp);
    }

    public static post postRemoteToPost(postRemote postRemote) {
        post resultPost = new post();

        resultPost.postID = postRemote.getPostID();
        resultPost.userID = postRemote.getUserID();
        resultPost.content = postRemote.getContent();
        resultPost.timestamp = postRemote.getTimestamp();

        return resultPost;
    }

    public static postRemote postToRemotePost(post postInput){
        return new postRemote(postInput.postID, postInput.userID, postInput.content, postInput.timestamp);
    }

    private static reaction reactionRemoteToReaction(reactionRemote reactionRemote) {
        reaction resultReaction = new reaction();

        resultReaction.userID = reactionRemote.getUserID();
        resultReaction.postID = reactionRemote.getPostID();
        resultReaction.type = reactionRemote.getType();
        resultReaction.timestamp = reactionRemote.getTimestamp();

        return resultReaction;
    }

    public static reactionRemote reactionToRemoteReaction(reaction reactionInput){
        return new reactionRemote(reactionInput.userID, reactionInput.postID, reactionInput.type, reactionInput.timestamp);
    }
}

