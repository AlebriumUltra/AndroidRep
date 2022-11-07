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
    SwitchCompat themeSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        dbHandler = new DataBaseHandler(this);

        Bundle arguments = getIntent().getExtras();
        pref = getSharedPreferences(arguments.get("Username").toString(), MODE_PRIVATE);
        backIntent = new Intent(this, ListActivity.class);



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
                themeSwitch.setChecked(true);
            }
            else
            {
                themeSwitch.setChecked(false);
            }
        }

        themeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    }
                }
            });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backIntent.putExtra("Username", arguments.get("Username").toString());
                startActivity(backIntent);
            }
        });


        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!oldPasswordField.getText().toString().matches("") && !newPasswordField.getText().toString().matches(""))
                {
                        if(dbHandler.checkUser(new User(loginText1.getText().toString(), oldPasswordField.getText().toString())))
                        {
                            dbHandler.changePassword(loginText1.getText().toString(), newPasswordField.getText().toString());
                            Toast toast = Toast.makeText(getApplicationContext(), "Change password success!", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                        else
                        {
                            Toast toast = Toast.makeText(getApplicationContext(), "Wrong old password!", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                }
                else
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Empty fields!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
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
