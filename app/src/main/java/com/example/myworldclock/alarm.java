package com.example.myworldclock;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.myworldclock.AlarmReceiver;

import java.util.Calendar;

public class alarm extends Activity {

    private TimePicker timePicker;
    private CheckBox checkboxMonday, checkboxTuesday, checkboxWednesday, checkboxThursday,
            checkboxFriday, checkboxSaturday, checkboxSunday;
    private Button setAlarmButton, selectSoundButton;
    private Uri alarmSoundUri;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_page);

        // Initialize views
        timePicker = findViewById(R.id.timePicker);
        checkboxMonday = findViewById(R.id.checkbox_monday);
        checkboxTuesday = findViewById(R.id.checkbox_tuesday);
        checkboxWednesday = findViewById(R.id.checkbox_wednesday);
        checkboxThursday = findViewById(R.id.checkbox_thursday);
        checkboxFriday = findViewById(R.id.checkbox_friday);
        checkboxSaturday = findViewById(R.id.checkbox_saturday);
        checkboxSunday = findViewById(R.id.checkbox_sunday);
        setAlarmButton = findViewById(R.id.setAlarmButton);
        selectSoundButton = findViewById(R.id.selectSoundButton);

        // Initialize AlarmManager
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        selectSoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the sound selection dialog
                Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Alarm Sound");
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, alarmSoundUri);
                startActivityForResult(intent, 0);
            }
        });

        setAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get selected alarm time
                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();

                // Get selected days
                boolean monday = checkboxMonday.isChecked();
                boolean tuesday = checkboxTuesday.isChecked();
                boolean wednesday = checkboxWednesday.isChecked();
                boolean thursday = checkboxThursday.isChecked();
                boolean friday = checkboxFriday.isChecked();
                boolean saturday = checkboxSaturday.isChecked();
                boolean sunday = checkboxSunday.isChecked();

                // Set the alarm
                setAlarm(hour, minute, monday, tuesday, wednesday, thursday, friday, saturday, sunday);

                // Show toast message indicating alarm is set
                Toast.makeText(alarm.this, "Alarm set for " + hour + ":" + minute, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 0) {
            alarmSoundUri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
        }
    }

    private void setAlarm(int hour, int minute, boolean monday, boolean tuesday, boolean wednesday,
                          boolean thursday, boolean friday, boolean saturday, boolean sunday) {
        // Create a calendar instance and set the selected alarm time
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        // Check the selected days and set the alarm for each selected day
        if (monday) {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            scheduleAlarm(calendar);
        }
        if (tuesday) {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
            scheduleAlarm(calendar);
        }
        if (wednesday) {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
            scheduleAlarm(calendar);
        }
        if (thursday) {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
            scheduleAlarm(calendar);
        }
        if (friday) {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
            scheduleAlarm(calendar);
        }
        if (saturday) {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
            scheduleAlarm(calendar);
        }
        if (sunday) {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            scheduleAlarm(calendar);
        }
    }

    private void scheduleAlarm(Calendar calendar) {
        // Create a new PendingIntent that will launch the AlarmReceiver
        Intent intent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Set the alarm using AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }
}