package com.example.test;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

public class SettingsActivity extends AppCompatActivity {

    SharedPreferences pref;
    DataBaseHandler dbHandler;
    Intent backIntent;
    Intent logoutIntent;
    SwitchCompat themeSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        dbHandler = new DataBaseHandler(this);

        Bundle arguments = getIntent().getExtras();
        pref = getSharedPreferences(arguments.get("Username").toString(), MODE_PRIVATE);
        backIntent = new Intent(this, ListActivity.class);
        logoutIntent = new Intent(this, MainActivity.class);

        Button deleteUser = (Button)findViewById(R.id.deleteUser);
        Button backButton = (Button)findViewById(R.id.backButton1);
        Button changePasswordButton = (Button) findViewById(R.id.changePasswordButton);
        EditText oldPasswordField = (EditText) findViewById(R.id.oldPasswordField);
        EditText newPasswordField = (EditText) findViewById(R.id.newPasswordField);

        TextView loginText1 = (TextView) findViewById(R.id.loginText1);
        loginText1.setText(arguments.get("Username").toString());

        themeSwitch = (SwitchCompat)findViewById(R.id.themeSwitch1);

        if(pref.contains("theme"))
        {
            if(pref.getBoolean("theme", false))
            {
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                themeSwitch.setChecked(true);
            }
            else
            {
                themeSwitch.setChecked(false);
            }
        }

        themeSwitch.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backIntent.putExtra("Username", arguments.get("Username").toString());
                startActivity(backIntent);
            }
        });

        deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String userLogin = loginText1.getText().toString();
                        dbHandler.deleteUser(userLogin);
                        startActivity(logoutIntent);
                        finish();
                    }
                }).start();
            }
        });

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!oldPasswordField.getText().toString().matches("") && !newPasswordField.getText().toString().matches(""))
                {
                    User user = new User(loginText1.getText().toString(), oldPasswordField.getText().toString());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if(dbHandler.checkUser(user))
                            {
                                dbHandler.changePassword(loginText1.getText().toString(), newPasswordField.getText().toString());
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Change password success!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            else
                            {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Wrong old password!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }).start();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Empty fields!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor e = pref.edit();
        e.putBoolean("theme", themeSwitch.isChecked());
        e.apply();
    }
}
