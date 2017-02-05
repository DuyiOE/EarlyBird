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
import android.widget.Toast;

import de.bachelorarbeit.duygu.earlybird.de.bachelorarbeit.duygu.earlybird.util.AlarmData;

/**
 * Created by Duygu on 17.01.2017.
 * This class need the intents start address, destination address,
 * time lag which is random for now, from the AlarmActivity to start the WakeUp- Window
 * The user can cancel the alarm or look for the route with Google Maps
 *
 */

public class WakeUpActivity extends Activity {
    AlarmManager alarmManager;

    Context context;
    private String duration_hr;
    private String duration_min;
    private boolean earlier;
    private String time_lag;
    TextView wake_up_infoText;
    TextView wake_up_infoText2;
    int i =0;






    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wake_up);
        this.context = this;
        wake_up_infoText= (TextView)findViewById(R.id.wake_up_infoText);
        wake_up_infoText2 = (TextView)findViewById(R.id.wake_up_infoText2);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Button stop_alarm = (Button) findViewById(R.id.stop_Alarm);
        final Button show_Map = (Button) findViewById(R.id.show_Map);
        final Intent myIntent = new Intent(this.getApplicationContext(), AlarmReceiver.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        final String str_from;
        final String str_to;






        Intent i_Route = getIntent();
        Bundle extras = i_Route.getExtras();


        str_from = extras.getString("str_from");
        Log.e ("Start address is ", str_from);
        str_to = extras.getString("str_to");
        Log.e ("Destination is ", str_to);
        time_lag = extras.getString("time_lag");
        Log.e ("Random time ", time_lag);
        duration_hr = extras.getString("duration_hr");
        Log.e ("Duration hour is ", duration_hr);
        duration_min = extras.getString("duration_min");
        Log.e ("Duration Minute is ", duration_min);


        if(time_lag=="0"){
            earlier=false;
        }else {
            earlier = true;
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        builder.setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Early Bird")
                .setContentText("Alarm!");

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());

        if (str_from!=null){
            if(str_to!=null){
                //url request Gooole API, getting the duration now


                Log.i("Fahrtzeit:", duration_hr + " Stunden " +duration_min + " Minuten");
                AlarmData.setInfoText(wake_up_infoText,wake_up_infoText2, duration_hr,
                        duration_min, earlier, time_lag);

                show_Map.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        myIntent.putExtra("quote id", String.valueOf(i));
                        myIntent.putExtra("extra", "no");
                        sendBroadcast(myIntent);
                        alarmManager.cancel(pIntent);

                        //show the map
                        Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?" +
                                "saddr=" + str_from +
                                "&daddr=" + str_to+
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

                    Toast.makeText(context, "Es wurde keine Route eingeben. Tragen Sie Ihre Route bei Google Maps ein!",Toast.LENGTH_LONG).show();
                    AlarmData.setInfoText(wake_up_infoText,wake_up_infoText2, String.valueOf(duration_hr),
                            String.valueOf(duration_min), false, time_lag);
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



}






