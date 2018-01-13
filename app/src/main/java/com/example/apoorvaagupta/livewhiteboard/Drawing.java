package com.example.apoorvaagupta.livewhiteboard;

import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.database.sqlite.SQLiteDatabase;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class Drawing extends AppCompatActivity {
    public static final String TAG = "drawing";

    ImageButton ibBlack, ibRed, ibGreen, ibBlue, ibEraser, ibSave, ibClear;
    CanvasView drawingCanvas;

    Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);
        drawingCanvas = findViewById(R.id.drawing_canvas);

        ibBlack = findViewById(R.id.ibBlack);
        ibRed = findViewById(R.id.ibRed);
        ibGreen = findViewById(R.id.ibGreen);
        ibBlue = findViewById(R.id.ibBlue);
        ibEraser = findViewById(R.id.ibEraser);
        ibSave = findViewById(R.id.ibSave);
        ibClear = findViewById(R.id.ibClear);

        drawingCanvas = findViewById(R.id.drawing_canvas);

        DatabaseHandler dbHelper = new DatabaseHandler(this);

        final SQLiteDatabase writeDb = dbHelper.getWritableDatabase();
        final SQLiteDatabase readDb = dbHelper.getReadableDatabase();



        Log.d(TAG, "onCreate: " + getIntent());
        Intent i = getIntent();
        Log.d(TAG, "onCreate: " + i.hasExtra("sessionId"));
        if (i.hasExtra("sessionId")) {
            socket = ((SocketHandler) getApplication()).getSocket();
            drawingCanvas.setEmitTo("drawingInSession");
            drawingCanvas.setSessionId(i.getStringExtra("sessionId"));
            Log.d(TAG, "onCreate: Before");
            socket.on("drawingInSession", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.d(TAG, "onCreate: In");
                    drawingCanvas.drawFromServer((JSONObject) args[0]);
                }
            });
            Toast.makeText(this, i.getStringExtra("sessionId"), Toast.LENGTH_SHORT).show();
        }

        if(i.hasExtra("drawingId")){
            Log.d(TAG, "onCreate: *******"+ i.getIntExtra("drawingId",1));
            Log.d(TAG, "onCreate: " +(DrawingsTable.getDrawing(i.getIntExtra("drawingId",1),readDb)).getId() );
            Log.d(TAG, "onCreate: "+ (DrawingsTable.getDrawing(i.getIntExtra("drawingId",1),readDb)).getName());
            byte[] bitmapdata = (DrawingsTable.getDrawing(i.getIntExtra("drawingId",1),readDb)).getDrawing();
            try {
                    Bitmap dbitmap = deserialize(bitmapdata);
                    Log.d(TAG, "onClick:"+ dbitmap);
                    drawingCanvas.drawFromBitmap(dbitmap);
//                    Log.d(TAG, "onClick: called update" );
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
        }

        ibSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bitmap bitmap = drawingCanvas.getBitmap();

//                Log.d(TAG, "onClick: " + bitmap);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] bitmapdata = stream.toByteArray();

                DrawingsTable.insertDrawing("Drawing",bitmapdata, writeDb);

//                try {
//                    Bitmap dbitmap = deserialize(bitmapdata);
////                    Log.d(TAG, "onClick:"+ dbitmap);
//                    drawingCanvas.drawFromBitmap(dbitmap);
////                    Log.d(TAG, "onClick: called update" );
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                }

//                String path = Environment.getExternalStorageDirectory().getAbsolutePath();
//                Log.d(TAG, "onClick: " + path);
//                File file = new File(path+"/image.png");
//                FileOutputStream ostream;
//                try {
//                    file.createNewFile();
//                    ostream = new FileOutputStream(file);
//                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, ostream);
//                    ostream.flush();
//                    ostream.close();
//                    Toast.makeText(getApplicationContext(), "image saved", Toast.LENGTH_LONG).show();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
//                }


//                bitmap =  Bitmap.createBitmap (content.getWidth(), content.getHeight(), Bitmap.Config.RGB_565);;
//                Canvas canvas = new Canvas(mBitmap);
//                v.draw(canvas);
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                mBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                byte[] bitmapdata = stream.toByteArray();


            }
        });

        ibBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingCanvas.changeColor(Color.BLACK);
            }
        });

        ibRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingCanvas.changeColor(Color.RED);
            }
        });

        ibBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingCanvas.changeColor(Color.BLUE);
            }
        });

        ibGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingCanvas.changeColor(Color.GREEN);
            }
        });

        ibEraser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingCanvas.setEraser();
            }
        });

        ibClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingCanvas.clearCanvas();
            }
        });


    }

    private static Bitmap deserialize(byte[] data) throws IOException, ClassNotFoundException {
//        ByteArrayInputStream in = new ByteArrayInputStream(data);
//        ObjectInputStream is = null;
//        try {
//            is = new ObjectInputStream(in);
//            Log.d(TAG, "deserialize: "+ is);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return (Bitmap) is.readObject();

        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        return bitmap;
    }
}
