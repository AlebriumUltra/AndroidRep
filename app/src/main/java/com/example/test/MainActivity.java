package com.example.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    SharedPreferences activityPref; // "Локальный preferences для сохранения логина и пароля в рамках одной активити с авторизацией
    private String usernameStringKey = "USERNAME";
    private String passwordStringKey = "PASSWORD";
    private Button loginButton;
    private EditText usernameText;
    private EditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("MainActivity: ", "onCreate");



        setContentView(R.layout.authorization_users);

        loginButton = (Button) findViewById(R.id.LoginButton);
        usernameText = (EditText) findViewById(R.id.UsernameField);
        passwordText = (EditText) findViewById(R.id.PasswordField);
        Intent nextIntent = new Intent(this, ListActivity.class); // Интент для вызова второй activity
        activityPref = this.getPreferences(Context.MODE_PRIVATE);
        if(activityPref.contains(usernameStringKey) && activityPref.contains(passwordStringKey))
        {
            usernameText.setText(activityPref.getString(usernameStringKey, ""));
            passwordText.setText(activityPref.getString(passwordStringKey, ""));
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!usernameText.getText().toString().matches("") && !passwordText.getText().toString().matches("")) {
                    nextIntent.putExtra("Username", usernameText.getText().toString()); // Передаем данные из первой активности во вторую
                    SharedPreferences.Editor editor = activityPref.edit();
                    editor.putString(usernameStringKey, usernameText.getText().toString());
                    editor.putString(passwordStringKey, passwordText.getText().toString());
                    editor.apply();
                    startActivity(nextIntent); // Вызываем вторую активность
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("MainActivity: ", "onStart"); // Переопределенные методы с выводом сообщений в системный журнал
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