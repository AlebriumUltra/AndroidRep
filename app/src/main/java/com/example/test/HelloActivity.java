package com.example.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class HelloActivity extends Activity {
    private int counter1 = 0;
    private int counter2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hello_activity);

        TextView countViewer = (TextView) findViewById(R.id.countViewer);
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    counter2++;
                    countViewer.setText(String.valueOf(counter1) + " " + String.valueOf(counter2));
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    counter1++;
                    countViewer.setText(String.valueOf(counter1) + " " + String.valueOf(counter2));
            }

        });
    }
}