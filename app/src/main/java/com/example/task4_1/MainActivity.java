package com.example.task4_1;


import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView display, user_hint;
    private ImageButton start, pause, stop;
    private EditText input;
    private Chronometer chronometer;
    private boolean running;
    private long pauseOffset;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.textView2);
        user_hint = findViewById(R.id.textView4);
        start = findViewById(R.id.imageButton);
        pause = findViewById(R.id.imageButton2);
        stop = findViewById(R.id.imageButton3);
        input = findViewById(R.id.editTextTextPersonName2);
        chronometer = findViewById(R.id.chronometer);
        
    }



    public void startChronometer(View v){
        if (!running){
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
        }
    }
    public void pauseChronometer(View v){
        if (running){
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        }
    }
    public void stopChronometer(View v){
        updatedisplay(chronometer.getText());
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
        running = false;
        // reset timer
        pauseOffset = 0;
        chronometer.stop();
        chronometer.setBase(SystemClock.elapsedRealtime());
    }
    private void updatedisplay(CharSequence time) {

        if (input.getText().toString().equals(""))
            display.setText("You spent " + time.toString() + " on your workout last time.");
        else display.setText("You spent " + time.toString() + " on " + input.getText().toString() + " last time.");
    }



}