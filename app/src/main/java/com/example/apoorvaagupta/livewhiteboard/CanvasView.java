package com.example.apoorvaagupta.livewhiteboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.Socket;

/**
 * Created by apoorvaagupta on 11/01/18.
 */

public class CanvasView extends View {
    public static final String TAG = "hey there";

    public int width;
    public int height;
    private Bitmap bitmap;
    private Canvas canvas;
    private Path path;
    Context context;
    private Paint paint;
    private Socket socket;
    private float mX = 0, mY = 0;
    private static final float TOLERANCE = 5;

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.setDrawingCacheEnabled(true);
        this.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        path = new Path();

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(4f);

    }

    public Bitmap getBitmap(){
        this.destroyDrawingCache();
        this.buildDrawingCache();
        return this.getDrawingCache();
    }

    public void setDimensions(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void drawFromBitmap(Bitmap bitmap) {
        Log.d(TAG, "updateCanvas: " + bitmap);
        Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        this.bitmap = mutableBitmap;
//        canvas.drawBitmap(mutableBitmap,0,0,paint);
//        canvas = new Canvas(mutableBitmap);
        Log.d(TAG, "updateCanvas: ");
        Log.d(TAG, "drawFromBitmap: "+ this.bitmap);
//        super.onDraw(canvas);
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "onSizeChanged: " + bitmap);
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
//        if (this.bitmap != null) {
            Log.d(TAG, "onDraw: " + bitmap);
            canvas.drawBitmap(this.bitmap, 0, 0, paint);
        Log.d(TAG, "onDraw: drawn" + bitmap);
//            this.bitmap = null;
//            Log.d(TAG, "onDraw: " + bitmap);
//        }

        canvas.drawPath(path, paint);


    }

    public void clearCanvas() {
        path.reset();
        invalidate();
    }

    // when ACTION_DOWN start touch according to the x,y values
    private void startTouch(float x, float y) {
//        try {
//            socket.emit("drawing", getJsonObject(x,y));
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        path.moveTo(x, y);
        mX = x;
        mY = y;
    }

    // when ACTION_MOVE move touch according to the x,y values
    private void moveTouch(float x, float y) {
//        try {
//            socket.emit("drawing", getJsonObject(x,y));
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOLERANCE || dy >= TOLERANCE) {

            path.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }

    // when ACTION_UP stop touch
    private void upTouch(float x, float y) {
        Log.d("UPTOUCH", "upTouch: " + x + " " + y);

//        try {
//            socket.emit("drawing", getJsonObject(x,y));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        path.lineTo(mX, mY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                moveTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                upTouch(x, y);
                invalidate();
                break;
        }
        return true;
    }

    private JSONObject getJsonObject(float x, float y) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("x0", mX / getWidth());
        jsonObject.put("y0", mY / getHeight());
        jsonObject.put("x1", x / getWidth());
        jsonObject.put("y1", y / getHeight());

        return jsonObject;
    }

}
