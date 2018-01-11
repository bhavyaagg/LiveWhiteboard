package com.example.apoorvaagupta.livewhiteboard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView startNewSession, joinExistingSession, createNewDrawing, openExistingDrawing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startNewSession = findViewById(R.id.tvStartNewSession);
        joinExistingSession = findViewById(R.id.tvJoinExistingSession);
        createNewDrawing = findViewById(R.id.tvCreateNewDrawing);
        openExistingDrawing = findViewById(R.id.tvOpenSavedDrawings);

        startNewSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send request to the server for creating a new room
                //the id returned by the server will be put in the bundle and sent with the intent to the new activity.
            }
        });
        joinExistingSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, JoinNewSession.class);
                startActivity(i);

            }
        });
        createNewDrawing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Drawing.class);
                startActivity(i);
            }
        });
        openExistingDrawing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
