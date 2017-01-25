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
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;

import java.sql.Time;
import java.util.Calendar;

/**
 * Created by Duygu on 17.01.2017.
 */

public class WakeUpActivity extends Activity{
    AlarmManager alarmManager;
    int i = 0;
    Context context;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wake_up);
        this.context = this;

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Button stop_alarm = (Button) findViewById(R.id.stop_Alarm);
        Button show_Map = (Button) findViewById(R.id.show_Map);
        final Intent myIntent = new Intent(this.getApplicationContext(), AlarmReceiver.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        final String from;
        final String to;

        Intent i_Route = getIntent();
        Bundle extras = i_Route.getExtras();

        from = extras.getString("from");
        to = extras.getString("to");
        Log.e ("Startadress is ", from);
        Log.e ("Destination is ", to);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        builder.setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Early Bird")
                .setContentText("Alarm!");

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());



        if (from!=null){
            if(to!=null){

                show_Map.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        myIntent.putExtra("quote id", String.valueOf(i));
                        myIntent.putExtra("extra", "no");
                        sendBroadcast(myIntent);
                        alarmManager.cancel(pIntent);

                        //show the map
                        Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?saddr=" + from + "&daddr=" + to+ "&mode=d");
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                        finish();
                    }
                });
                stop_alarm.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        myIntent.putExtra("extra", "no");
                        myIntent.putExtra("quote id", String.valueOf(i));
                        sendBroadcast(myIntent);
                        alarmManager.cancel(pIntent);
                        WakeUpActivity.super.onDestroy();
                        finish();

                    }
                });
            }
        }else{
            stop_alarm.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    myIntent.putExtra("extra", "no");
                    myIntent.putExtra("quote id", String.valueOf(i));
                    sendBroadcast(myIntent);
                    alarmManager.cancel(pIntent);
                    WakeUpActivity.super.onDestroy();
                    finish();

                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.e("AlarmActivity", "on Destroy");
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    public void driveNow(){


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






