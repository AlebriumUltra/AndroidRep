package com.example.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
}