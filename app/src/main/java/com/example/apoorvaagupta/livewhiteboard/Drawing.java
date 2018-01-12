package com.example.apoorvaagupta.livewhiteboard;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Drawing extends AppCompatActivity {
public static final String TAG = "drawing";

ImageButton ibBlack, ibRed, ibGreen, ibBlue, ibEraser, ibSave, ibClear;
CanvasView drawingCanvas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);

        ibBlack = findViewById(R.id.ibBlack);
        ibRed = findViewById(R.id.ibRed);
        ibGreen = findViewById(R.id.ibGreen);
        ibBlue = findViewById(R.id.ibBlue);
        ibEraser = findViewById(R.id.ibEraser);
        ibSave = findViewById(R.id.ibSave);
        ibClear = findViewById(R.id.ibClear);

        drawingCanvas = findViewById(R.id.drawing_canvas);
        ibSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bitmap bitmap = drawingCanvas.getBitmap();

//                Log.d(TAG, "onClick: " + bitmap);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] bitmapdata = stream.toByteArray();

                drawingCanvas.clearCanvas();

                try {
                    Bitmap dbitmap = deserialize(bitmapdata);
//                    Log.d(TAG, "onClick:"+ dbitmap);
                    drawingCanvas.drawFromBitmap(dbitmap);
//                    Log.d(TAG, "onClick: called update" );
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

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
