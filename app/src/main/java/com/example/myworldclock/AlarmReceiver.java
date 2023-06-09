package com.example.myworldclock;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Play the alarm sound
        Uri alarmSoundUri = intent.getParcelableExtra("alarmSoundUri");
        if (alarmSoundUri != null) {
            Ringtone ringtone = RingtoneManager.getRingtone(context, alarmSoundUri);
            ringtone.play();
        }
    }
}
