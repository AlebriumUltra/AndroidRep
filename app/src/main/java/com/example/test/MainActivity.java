package com.example.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("MainActivity: ", "onCreate");


        setContentView(R.layout.authorization_users);

        Button loginButton = (Button) findViewById(R.id.LoginButton);
        EditText usernameText = (EditText) findViewById(R.id.UsernameField);
        EditText passwordText = (EditText) findViewById(R.id.PasswordField);
        Intent nextIntent = new Intent(this, ListActivity.class);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (usernameText.getText().toString().equals("")) {
                    startActivity(nextIntent);
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("MainActivity: ", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("MainActivity: ", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("MainActivity: ", "onPause");
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.i("MainActivity: ", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("MainActivity: ", "onDestroy");
    }

}