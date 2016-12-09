package com.gesture.sushma.gestureinmobile;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
//import android.support.v7.app.AppCompatActivity;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.hardware.SensorEventListener;
import android.app.Activity;

import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.util.Log;




public class MainActivity extends Activity implements SensorEventListener,
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {

    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 600;

    private TextView mGestureText;
    private TextView Accelerations;

    private GestureDetector mGestureDetector;
    private MotionEvent e1;
    private MotionEvent e2;
    private float velocityX;
    private  float velocityY;
    private static final String TAG = "GestureActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Log.d(TAG, "onCreate ...............................");


        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        mGestureText = (TextView) findViewById(R.id.gestureStatus);
        Accelerations = (TextView) findViewById(R.id.Accelerometerdata);



        // Create a GestureDetector
        mGestureDetector = new GestureDetector(this, this);
        // Attach listeners that'll be called for double-tap and related gestures
        mGestureDetector.setOnDoubleTapListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            detectMotion(sensorEvent);
        }

    }

    public void detectMotion(SensorEvent sensorEvent) {
        long now = System.currentTimeMillis();

        if ((now - lastUpdate) > 100) {
            lastUpdate = now;

            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            System.out.println("X = " + x);
            System.out.println("Y = " + y);
            System.out.println("Z = " + z);

           /* Accelerations.setText("X = " + x + "\n" +
                    "Y = " + x + "\n" +
                    "Z = " + x + "\n" );
                    */
        }
    }




    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        senSensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);

        return super.onTouchEvent(event);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        mGestureText.setText("onSingleTapConfirmed");
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        mGestureText.setText("onDoubleTap");
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        mGestureText.setText("onDoubleTapEvent");
        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        mGestureText.setText("onDown");
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        mGestureText.setText("onShowPress");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        mGestureText.setText("onSingleTapUp");
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        mGestureText.setText("onScroll");
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        mGestureText.setText("onLongPress");
    }

    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        mGestureText.setText("onFling " + e1.getX() + " - " + e2.getX());

        if (e1.getX() < e2.getX()) {
            Log.d(TAG, "Left to Right swipe performed");
        }

        if (e1.getX() > e2.getX()) {
            Log.d(TAG, "Right to Left swipe performed");
        }

        if (e1.getY() < e2.getY()) {
            Log.d(TAG, "Up to Down swipe performed");
        }

        if (e1.getY() > e2.getY()) {
            Log.d(TAG, "Down to Up swipe performed");
        }

        return true;
    }
}
