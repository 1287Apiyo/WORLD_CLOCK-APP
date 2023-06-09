package com.example.myworldclock;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class MainActivity extends AppCompatActivity {

    private EditText editTextTimeZone;
    private Button buttonSearch;
    private ImageView alarmImageView;
    private ImageView stopwatchImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTimeZone = findViewById(R.id.editTextTimeZone);
        buttonSearch = findViewById(R.id.buttonAdd);
        alarmImageView = findViewById(R.id.imageView3);
        stopwatchImageView = findViewById(R.id.imageView2);

        buttonSearch.setOnClickListener(v -> {
            String inputTimeZone = editTextTimeZone.getText().toString();
            String convertedTime = convertTimeToTimeZone(inputTimeZone);
            Toast.makeText(MainActivity.this, convertedTime, Toast.LENGTH_SHORT).show();
        });

        alarmImageView.setOnClickListener(v -> {
            // Start the alarmPage activity
            Intent intent = new Intent(MainActivity.this, alarm.class );
            startActivity(intent);
        });

        stopwatchImageView.setOnClickListener(v -> {
            // Start the StopwatchPage activity
            Intent intent = new Intent(MainActivity.this,stopwatch.class);
            startActivity(intent);
        });
    }

    private String convertTimeToTimeZone(String inputTimeZone) {
        // Get the current time in the default time zone
        LocalDateTime currentTime = LocalDateTime.now();

        // Create a formatter for displaying the converted time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try {
            // Get the ZoneId for the input time zone
            ZoneId inputZoneId = ZoneId.of(inputTimeZone);

            // Convert the current time to the input time zone
            ZonedDateTime convertedTime = currentTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(inputZoneId);

            // Format the converted time and return it as a string
            return "Converted Time: " + convertedTime.format(formatter);
        } catch (Exception e) {
            e.printStackTrace();
            return "Invalid Time Zone";
        }
        }}