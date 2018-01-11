package com.example.apoorvaagupta.livewhiteboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class JoinNewSession extends AppCompatActivity {

    EditText etJoinSessionId;
    Button btnJoin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_new_session);

        etJoinSessionId = findViewById(R.id.etJoinSessionId);
        btnJoin = findViewById(R.id.btnJoin);

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etJoinSessionId.getText().toString().trim().equalsIgnoreCase("")) {
                    etJoinSessionId.setError("This field can not be blank");
                }

                //send request to the server and it is successful send intent to teh canvas activity.
            }
        });
    }
}
