package com.example.apoorvaagupta.livewhiteboard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MAIN";

    TextView startNewSession, joinExistingSession, createNewDrawing, openExistingDrawing;

    Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SocketHandler app = (SocketHandler) getApplication();
        socket = app.getSocket();

        startNewSession = findViewById(R.id.tvStartNewSession);
        joinExistingSession = findViewById(R.id.tvJoinExistingSession);
        createNewDrawing = findViewById(R.id.tvCreateNewDrawing);
        openExistingDrawing = findViewById(R.id.tvOpenSavedDrawings);

        startNewSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send request to the server for creating a new room
                //the id returned by the server will be put in the bundle and sent with the intent to the new activity.
                Log.d(TAG, "onClick: " + "Button Working");
                socket.connect();
                socket.emit("createSession");
                socket.on("createdSession", new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        Log.d(TAG, "call: HERE");
                        JSONObject jsonObject = (JSONObject) args[0];
                        try {
                            Boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                Intent i = new Intent(MainActivity.this, Drawing.class);
                                i.putExtra("sessionId", jsonObject.getString("sessionId"));
                                startActivity(i);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

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
                Intent i = new Intent(MainActivity.this, SavedDrawings.class);
                startActivity(i);
            }
        });
    }
}
