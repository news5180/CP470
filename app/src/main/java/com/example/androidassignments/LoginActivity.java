package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "LoginActivity";
    private Button loginButton;
    private EditText editEmailLogin;
    private EditText editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(ACTIVITY_NAME, "In onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editEmailLogin = (EditText)findViewById(R.id.emailField);
        SharedPreferences SharedPref = getSharedPreferences("DefaultEmail", Context.MODE_PRIVATE);
        String defaultEmail = getResources().getString(R.string.defaultEmail);
        String emailText = SharedPref.getString(String.valueOf(R.string.savedEmail),defaultEmail);
        editEmailLogin.setText(emailText);
        loginButton = (Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailText = editEmailLogin.getText().toString();
                SharedPreferences ShardPref2 = getSharedPreferences("DefaultEmail",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = ShardPref2 .edit();
                editor.putString(String.valueOf(R.string.savedEmail),emailText);
                editor.commit();

                editPassword = (EditText)findViewById(R.id.passwordField);
                String password = editPassword.getText().toString();

                if(password != "" || password != null) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }
}