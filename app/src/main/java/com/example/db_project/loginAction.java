package com.example.db_project;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.*;

import java.util.List;
import java.util.stream.Collectors;

import javax.xml.transform.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class loginAction extends AppCompatActivity {
    public static final String EXTRA_USERID = "com.example.db_project.USERID";
    public static final String EXTRA_POST_COUNT = "com.example.db_project.postCount";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // default
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_action);

    }
    //invoked when press login button.
    public void checkLoginInfo(View view) throws ClassNotFoundException, SQLException {


        // get userID and name input
        EditText IDEditText = (EditText) findViewById(R.id.LAuserIDInput);
        String idInput = IDEditText.getText().toString();
        EditText nameEditText = (EditText) findViewById(R.id.LANameInput);
        String nameInput = nameEditText.getText().toString();

        // sync remoteDB and localDB
        syncFunctions.downloadUserRemoteDB(MainActivity.userDAOObject);

        // find matching login info
        List<user> allUserList = MainActivity.userDAOObject.getAll();
        for (user userIterate : allUserList){
            if(userIterate.userID.equals(idInput)){
                if(userIterate.name.equals(nameInput)){
                    Intent loginSuccess = new Intent(this, mainPageAction.class);

                    // put userID and initial post Count
                    Bundle extraBundle = new Bundle();
                    extraBundle.putString(EXTRA_USERID, idInput);
                    extraBundle.putString(EXTRA_POST_COUNT, Integer.toString(0));
                    loginSuccess .putExtras(extraBundle);

                    startActivity(loginSuccess);
                    return;
                }
            }
        }

        // invoked only when login info not found.
        Intent loginFail = new Intent(this, loginFailAction.class);
        startActivity(loginFail);
    }
}
