package de.bachelorarbeit.duygu.earlybird;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by Duygu on 17.01.2017.
 */

public class WakeUpActivity extends Activity{
    AlarmManager alarmManager;
    private PendingIntent pIntent;
    private Intent intentW;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wake_up);



        stopAlarm();
        diveNow();

    }

    private void stopAlarm() {


        Button stop_alarm = (Button) findViewById(R.id.stop_Alarm);

        stop_alarm.setOnClickListener(new View.OnClickListener() {
            Context context;

            @Override
            public void onClick(View v) {

            }
        });
    }
        public void diveNow(){


        Intent intentW = new Intent(this.getApplicationContext(), MapActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intentW, 0);

        final NotificationManager mNM = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);


        Notification mNotify = new Notification.Builder(this)
                .setContentText("Drive now!")
                .setSmallIcon(R.drawable.logo)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .build();

        mNM.notify(0, mNotify);
        }
}






