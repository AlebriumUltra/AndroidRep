package com.example.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class MainActivity extends AppCompatActivity {

    SharedPreferences activityPref; // "Локальный preferences для сохранения логина и пароля в рамках одной активити с авторизацией
    private String usernameStringKey = "USERNAME";
    private String passwordStringKey = "PASSWORD";
    private Button loginButton;
    private EditText usernameText;
    private EditText passwordText;
    private Button registerButton;
    Intent nextIntent;
    DataBaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        Log.i("MainActivity: ", "onCreate");

        setContentView(R.layout.authorization_users);

        dbHandler = new DataBaseHandler(this);

        loginButton = (Button) findViewById(R.id.LoginButton);
        registerButton = (Button) findViewById(R.id.registerButton);
        usernameText = (EditText) findViewById(R.id.UsernameField);
        passwordText = (EditText) findViewById(R.id.PasswordField);
        nextIntent = new Intent(this, ListActivity.class); // Интент для вызова второй activity
        activityPref = this.getPreferences(Context.MODE_PRIVATE);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!usernameText.getText().toString().matches("") && !passwordText.getText().toString().matches(""))
                {
                    User user = new User(usernameText.getText().toString(), passwordText.getText().toString());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if(dbHandler.checkUser(new User(usernameText.getText().toString(), passwordText.getText().toString())))
                            {
                                nextIntent.putExtra("Username", usernameText.getText().toString());
                                startActivity(nextIntent); // Вызываем вторую активность
                            }
                            else
                            {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast toast = Toast.makeText(getApplicationContext(), "Wrong password or user doesn't exist!", Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();
                                    }
                                });
                            }
                        }
                    }).start();


                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!usernameText.getText().toString().matches("") && !passwordText.getText().toString().matches("")) {
                    User user = new User(usernameText.getText().toString(), passwordText.getText().toString());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if(!dbHandler.checkLogin(user)) {
                                dbHandler.addUser(user);
                                nextIntent.putExtra("Username", usernameText.getText().toString());
                                startActivity(nextIntent); // Вызываем вторую активность
                            }
                            else
                            {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast toast = Toast.makeText(getApplicationContext(), "User already exist!", Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();
                                    }
                                });
                            }
                        }
                    }).start();
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