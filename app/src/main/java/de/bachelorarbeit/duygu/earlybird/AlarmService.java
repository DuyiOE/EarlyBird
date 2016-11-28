package de.bachelorarbeit.duygu.earlybird;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import java.util.Random;

/**
 * Created by Duygu on 26.11.2016.
 */

public class AlarmService extends Service {

    private boolean isRunning;
    private Context context;
    MediaPlayer mMediaPlayer;
    private int startId;

    @Override
    public IBinder onBind(Intent intent) {
        Log.e("MyActivity", "In the Richard service");
        return null;
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        final NotificationManager mNM = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        Intent intent1 = new Intent(this.getApplicationContext(), AlarmActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent1, 0);

        Notification mNotify = new Notification.Builder(this)
                .setContentTitle("Richard Dawkins is talking" + "!")
                .setContentText("Click me!")
                .setSmallIcon(R.drawable.logo)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .build();

        String state = intent.getExtras().getString("extra");

        Log.e("what is going on here  ", state);

        assert state != null;
        switch (state) {
            case "no":
                startId = 0;
                break;
            case "yes":
                startId = 1;
                break;
            default:
                startId = 0;
                break;
        }

        // get richard's thing
        String quoteID = intent.getExtras().getString("quote id");
        Log.e("Service: quote id is ", quoteID);

        if (!this.isRunning && startId == 1) {
            Log.e("if there was not sound ", " and you want start");

            assert quoteID != null;
            if (quoteID.equals("0")) {

                int min = 1;
                int max = 10;

                Random r = new Random();
                int random_number = r.nextInt(max - min + 1) + min;
                Log.e("random number is ", String.valueOf(random_number));

                if (random_number == 1) {
                    mMediaPlayer = MediaPlayer.create(this, R.raw.frametraxx_Endless_Love);
                } else if (random_number == 2) {
                    mMediaPlayer = MediaPlayer.create(this, R.raw.Dadadeedeedum);
                } else if (random_number == 3) {
                    mMediaPlayer = MediaPlayer.create(this, R.raw.Dental_Forum_Band);
                } else if (random_number == 4) {
                    mMediaPlayer = MediaPlayer.create(this, R.raw.frametraxx_Endless_Love);
                } else if (random_number == 5) {
                    mMediaPlayer = MediaPlayer.create(this, R.raw.frametraxx_My_Love);
                } else if (random_number == 6) {
                    mMediaPlayer = MediaPlayer.create(this, R.raw.frametraxx_Pure_Colours);
                } else if (random_number == 7) {
                    mMediaPlayer = MediaPlayer.create(this, R.raw.frametraxx_Silent_Spot);
                } else if (random_number == 8) {
                    mMediaPlayer = MediaPlayer.create(this, R.raw.Interceptor);
                } else if (random_number == 9) {
                    mMediaPlayer = MediaPlayer.create(this, R.raw.White_Wolf);
                } else if (random_number == 10) {
                    mMediaPlayer = MediaPlayer.create(this, R.raw.Psychedelic_Rain);
                } else {
                    mMediaPlayer = MediaPlayer.create(this, R.raw.White_Wolf);
                }
            } else if (quoteID.equals("1")) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.frametraxx_Endless_Love);
            } else if (quoteID.equals("2")) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.Dadadeedeedum);
            } else if (quoteID.equals("3")) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.Dental_Forum_Band);
            } else if (quoteID.equals("4")) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.frametraxx_Endless_Love);
            } else if (quoteID.equals("5")) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.frametraxx_My_Love);
            } else if (quoteID.equals("6")) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.frametraxx_Pure_Colours);
            } else if (quoteID.equals("7")) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.frametraxx_Silent_Spot);
            } else if (quoteID.equals("8")) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.Interceptor);
            } else if (quoteID.equals("9")) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.White_Wolf);
            } else if (quoteID.equals("10")) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.Psychedelic_Rain);
            } else {
                mMediaPlayer = MediaPlayer.create(this, R.raw.White_Wolf);
            }


            mMediaPlayer.start();

            mNM.notify(0, mNotify);

            this.isRunning = true;
            this.startId = 0;

        } else if (!this.isRunning && startId == 0) {
            Log.e("if there was not sound ", " and you want end");

            this.isRunning = false;
            this.startId = 0;

        } else if (this.isRunning && startId == 1) {
            Log.e("if there is sound ", " and you want start");

            this.isRunning = true;
            this.startId = 0;

        } else {
            Log.e("if there is sound ", " and you want end");

            mMediaPlayer.stop();
            mMediaPlayer.reset();

            this.isRunning = false;
            this.startId = 0;
        }


        Log.e("MyActivity", "In the service");

        return START_NOT_STICKY;
    }


    @Override
    public void onDestroy() {
        Log.e("JSLog", "on destroy called");
        super.onDestroy();

        this.isRunning = false;
    }


}