package de.bachelorarbeit.duygu.earlybird;

import android.app.Activity;
import android.app.AlarmManager;
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
import android.widget.TextView;

import de.bachelorarbeit.duygu.earlybird.de.bachelorarbeit.duygu.earlybird.ui.AlarmData;

/**
 * Created by Duygu on 17.01.2017.
 */

public class WakeUpActivity extends Activity implements DistanceTask.Distance{
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
        final String departure_time;
        TextView wake_up_infoText= (TextView)findViewById(R.id.wake_up_infoText);
        final int minute;
        final boolean earlier;
        final int minute_befor;

        //final String mode;

        Intent i_Route = getIntent();
        Bundle extras = i_Route.getExtras();

        //if mode is chosen, in pro version
        //mode = extras.getString("mode");
        //Log.e ("Mode is ", mode);
        to = extras.getString("to");
        Log.e ("Destination is ", to);
        from = extras.getString("from");
        Log.e ("Startadress is ", from);
        departure_time = extras.getString("departure_time");
        Log.e ("Time of departure is ", departure_time);

        //// TODO: 27.01.2017
        minute=22; //duration
        earlier=true;//late, yes or no
        minute_befor=12; // 12 minutes late

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        builder.setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Early Bird")
                .setContentText("Alarm!");

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());

        AlarmData.setInfoText( wake_up_infoText, minute, earlier, minute_befor);

        if (from!=null){
            if(to!=null){

                show_Map.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        myIntent.putExtra("quote id", String.valueOf(i));
                        myIntent.putExtra("extra", "no");
                        sendBroadcast(myIntent);
                        alarmManager.cancel(pIntent);

                        //in Backround
                        // get the Distance
                        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?+" +
                                "origins=" + from +
                                "&destinations=" + to +
                                "&mode=d"+
                                "&language=de-DE"+
                                "&avoid=tolls"+
                                "&key=AIzaSyDj5-Q_k24sZQPipJLFlqRM72rsY7PfFmY";
                        new DistanceTask(WakeUpActivity.this).execute(url);

                        //show the map
                        Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?+" +
                                "saddr=" + from +
                                "&daddr=" + to+
                                "&mode=d");
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

    @Override
    public void setDouble( String result) {
        String res[] = result.split(",");
        Double min = Double.parseDouble(res[0]) / 60;
        int hr = (int) (min / 60);
        int minut = (int) (min % 60);
        int dist = Integer.parseInt(res[1]) / 1000;
        Log.i("Fahrtzeit:", hr + " Stunden " + minut + " Minuten");
        Log.i("Entfernung:", + dist + " kilometers");


    }

/*
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

        */
}






