package com.example.test;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.content.SharedPreferences;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import java.util.ArrayList;


public class ListActivity extends AppCompatActivity {

    Bundle arguments;
    Button addButton;
    Button removeButton;
    Button cancelButton;
    EditText textField;
    ListView listViewer;
    ArrayList<String> textList;
    ArrayAdapter<String> textAdapter;
    Intent cancelIntent;

    SwitchCompat themeSwitch;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        arguments = getIntent().getExtras();
        pref = getSharedPreferences(arguments.get("Username").toString(), MODE_PRIVATE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);
        Log.i("ListActivity: ", "onCreate");

        addButton = (Button) findViewById(R.id.addButton);
        removeButton = (Button) findViewById(R.id.removeButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        textField = (EditText) findViewById(R.id.enterField);
        listViewer = (ListView) findViewById(R.id.textList);
        cancelIntent = new Intent(this, MainActivity.class);
        themeSwitch = (SwitchCompat) findViewById(R.id.themeSwitch);

        if(pref.contains("theme"))
        {
            if(pref.getBoolean("theme", false))
            {
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                themeSwitch.setChecked(true);
            }
            else
            {
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        }

        textList = new ArrayList<>();
        textAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, textList);
        textList.add(arguments.get("Username").toString());

        listViewer.setAdapter(textAdapter);
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
                if (!textList.isEmpty()) {
                    textList.remove(textList.size() - 1);
                    textAdapter.notifyDataSetChanged();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(cancelIntent);
            }
        });

        themeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked)
                {
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                else
                {
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
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
        SharedPreferences.Editor e = pref.edit();
        e.putBoolean("theme", themeSwitch.isChecked());
        e.apply();
        Log.i("ListActivity: ", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("ListActivity: ", "onDestroy");
    }
}