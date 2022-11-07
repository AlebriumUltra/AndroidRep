package com.example.test;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import java.util.ArrayList;
import java.util.List;


public class ListActivity extends AppCompatActivity {
    SharedPreferences pref;
    DataBaseHandler dbHandler;
    int position;
    Intent mainIntent;
    Intent settingsIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle arguments = getIntent().getExtras();
        pref = getSharedPreferences(arguments.get("Username").toString(), MODE_PRIVATE);
        if(pref.contains("theme"))
        {
            if(pref.getBoolean("theme", false))
            {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            else
            {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);
        Log.i("ListActivity: ", "onCreate");
        position = -1;
        dbHandler = new DataBaseHandler(this);


        TextView loginText = (TextView) findViewById(R.id.loginText);
        Button logoutButton = (Button) findViewById(R.id.logoutButton);
        Button settingsButton = (Button) findViewById(R.id.settingsButton);
        Button addButton = (Button) findViewById(R.id.addButton);
        Button removeButton = (Button) findViewById(R.id.removeButton);
        EditText textField = (EditText) findViewById(R.id.enterField);
        ListView listViewer = (ListView) findViewById(R.id.textList);
        mainIntent = new Intent(this, MainActivity.class);
        settingsIntent = new Intent(this, SettingsActivity.class);

        loginText.setText(arguments.get("Username").toString());
        ArrayList<String> textList = new ArrayList<>();
        ArrayAdapter<String> textAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, textList);
        List<User> userList = dbHandler.getAllUsers();
        for(int i = 0; i < userList.size(); i++)
        {
            textList.add(userList.get(i).getLogin() + " " + userList.get(i).getPass());
        }

        listViewer.setAdapter(textAdapter);
        listViewer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                position = pos;
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!textField.getText().toString().matches("")) {
                    textList.add(textField.getText().toString());
                    textAdapter.notifyDataSetChanged();
                }
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position != -1)
                {
                    String text = textList.get(position);
                    String[] tokens = text.split(" ");
                    if(tokens.length == 2)
                    {
                        User user = new User(tokens[0], tokens[1]);
                        dbHandler.deleteUser(user);
                    }
                    textList.remove(position);
                    textAdapter.notifyDataSetChanged();
                }
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(mainIntent);
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsIntent.putExtra("Username", arguments.get("Username").toString());
                startActivity(settingsIntent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("ListActivity: ", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("ListActivity: ", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("ListActivity: ", "onPause");
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.i("ListActivity: ", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("ListActivity: ", "onDestroy");
    }
}