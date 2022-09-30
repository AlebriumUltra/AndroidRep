package com.example.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class ListActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);
        Log.i("ListActivity: ", "onCreate");

        Button addButton = (Button)findViewById(R.id.addButton);
        Button removeButton = (Button)findViewById(R.id.removeButton);
        EditText textField = (EditText)findViewById(R.id.enterField);
        ListView listViewer = (ListView)findViewById(R.id.textList);

        ArrayList<String> textList = new ArrayList<>();
        ArrayAdapter<String> textAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, textList);
        listViewer.setAdapter(textAdapter);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textList.add(textField.getText().toString());
                textAdapter.notifyDataSetChanged();
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!textList.isEmpty())
                {
                    textList.remove(textList.size() - 1);
                    textAdapter.notifyDataSetChanged();
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
        Log.i("ListActivity: ", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("ListActivity: ", "onDestroy");
    }
}