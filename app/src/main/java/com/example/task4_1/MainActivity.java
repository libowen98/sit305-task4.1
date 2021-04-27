package com.example.task4_1;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView display, user_hint;
    private ImageButton start, pause, stop;
    private EditText input;
    private Chronometer chronometer;
    private boolean running;
    private long pauseOffset;
    private long time;
    private SharedPreferences SharedPreferences;
    private SharedPreferences.Editor Editor;
    String workType,workOut,spendTime;



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

        // Load data
        SharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        Editor = SharedPreferences.edit();
        pauseOffset = 0;

        workType = SharedPreferences.getString("type", "");
        time = SharedPreferences.getLong("time", 0);
        //long hours = (time % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (time % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (time % (1000 * 60)) / 1000;
        spendTime = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        display.setText("You spent " + spendTime + " on " + workType + " last time.");
        
        if (savedInstanceState != null) {
            pauseOffset = savedInstanceState.getLong("pauseOffset",0);
            running = savedInstanceState.getBoolean("running", true);
            long getBaseTime = savedInstanceState.getLong("getBaseTime", 0);

            if (running == true) {
                chronometer.start();
                chronometer.setBase(SystemClock.elapsedRealtime() - (SystemClock.elapsedRealtime() - getBaseTime));
            }
            else {
                chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                chronometer.stop();
            }
        }
    }
    
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("pauseOffset", pauseOffset);
        outState.putBoolean("running", running);
        outState.putLong("getBaseTime", chronometer.getBase());
    }

    // Start
    public void startChronometer(View view) {
        if (!running) {
            running = true;
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
        }
    }

    // Pause 
    public void pauseChronometer(View view) {
        if (running) {
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        }
    }

    public void stopChronometer(View view) {
        /*workOut = input.getText().toString();
        updatedisplay(chronometer.getText());
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
        running = false;
        // reset chronometer
        pauseOffset = 0;
        chronometer.stop();
        chronometer.setBase(SystemClock.elapsedRealtime());*/

        if (!running) {
            chronometer.stop();
            // Make totalTime equal to pauseOffset in pauseClick (Or we can say the value after clicking the Pause button)
            long totalTime = pauseOffset;
            Editor.putLong("time", totalTime);
            Editor.putString("type", input.getText().toString());
            Editor.apply();
            workOut = input.getText().toString();
            updatedisplay(chronometer.getText());
            pauseOffset = 0;
            chronometer.setBase(SystemClock.elapsedRealtime());

        } else {
            Toast.makeText(MainActivity.this, "You need to click the pause at first!", Toast.LENGTH_SHORT).show();
        }

    }
    
    private void updatedisplay(CharSequence time) {

        if (input.getText().toString().equals(""))
            display.setText("You spent " + time + " on your workTime last time.");
        else display.setText("You spent " + time + " on " + workOut + " last time.");
    }

    
    /*public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreference", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.commit();
    }
    
    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreference", MODE_PRIVATE);
    }*/



}