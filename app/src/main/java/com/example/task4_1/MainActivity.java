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

public class MainActivity extends AppCompatActivity {

    private TextView display, user_hint;
    private ImageButton start, pause, stop;
    private EditText input;
    private Chronometer chronometer;
    private boolean running;
    private long pauseOffset;
    private long current;
    String displayWorkout, workout;


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
        loadData();
        pauseOffset = 0;
        
        if (savedInstanceState != null) {
            pauseOffset = savedInstanceState.getLong("pauseOffset");
            running = savedInstanceState.getBoolean("running");
            current = savedInstanceState.getLong("CURRENT");

            if (running == true) {
                chronometer.start();
                chronometer.setBase(SystemClock.elapsedRealtime() - current);
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
        outState.putLong("CURRENT", SystemClock.elapsedRealtime() - chronometer.getBase());
        outState.putString("display", displayWorkout);
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
        workout = input.getText().toString();
        updatedisplay(chronometer.getText());
        saveData();
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
        running = false;
        // reset chronometer
        pauseOffset = 0;
        chronometer.stop();
        chronometer.setBase(SystemClock.elapsedRealtime());

    }
    
    private void updatedisplay(CharSequence time) {

        if (input.getText().toString().equals(""))
            display.setText("You spent " + time.toString() + " on your workout last time.");
        else display.setText("You spent " + time.toString() + " on " + input.getText().toString() + " last time.");
    }

    
    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreference", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.commit();
    }
    
    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreference", MODE_PRIVATE);
    }



}