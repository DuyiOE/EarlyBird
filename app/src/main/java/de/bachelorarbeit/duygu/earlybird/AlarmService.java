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
        Log.e("AlarmActivity", "In service");
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
                .setContentText("Click me!")
                .setSmallIcon(R.drawable.logo)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .build();

        String state = intent.getExtras().getString("extra");

        Log.e("", state);

        assert state != null;
         switch (state) {
                    case "no":
                        startId = 0;
                        break;
                    case "yes":
                        startId = 1;
                        break;
                     default:
                startId = 1;
                break;
        }

        //
        String quoteID = intent.getExtras().getString("quote id");
        Log.e("Service: quote id is ", quoteID);

        if (!this.isRunning && startId == 1) {
            Log.e("if there was no sound ", " and you want start");

            assert quoteID != null;
            if (quoteID.equals("0")) {

                int min = 1;
                int max = 10;

                Random r = new Random();
                int random_number = r.nextInt(max - min + 1) + min;
                Log.e("random number is ", String.valueOf(random_number));

                if (random_number == 1) {
                    mMediaPlayer = MediaPlayer.create(this, R.raw.frametraxx_endless_love);
                } else if (random_number == 2) {
                    mMediaPlayer = MediaPlayer.create(this, R.raw.dadadeedeedum);
                } else if (random_number == 3) {
                    mMediaPlayer = MediaPlayer.create(this, R.raw.dental_forum_band);
                } else if (random_number == 4) {
                    mMediaPlayer = MediaPlayer.create(this, R.raw.frametraxx_endless_love);
                } else if (random_number == 5) {
                    mMediaPlayer = MediaPlayer.create(this, R.raw.frametraxx_my_love);
                } else if (random_number == 6) {
                    mMediaPlayer = MediaPlayer.create(this, R.raw.frametraxx_pure_colours);
                } else if (random_number == 7) {
                    mMediaPlayer = MediaPlayer.create(this, R.raw.frametraxx_silent_spot);
                } else if (random_number == 8) {
                    mMediaPlayer = MediaPlayer.create(this, R.raw.interceptor);
                } else if (random_number == 9) {
                    mMediaPlayer = MediaPlayer.create(this, R.raw.white_wolf);
                } else if (random_number == 10) {
                    mMediaPlayer = MediaPlayer.create(this, R.raw.psychedelic_rain);
                } else {
                    mMediaPlayer = MediaPlayer.create(this, R.raw.white_wolf);
                }
            }

            mMediaPlayer.start();

            mNM.notify(0, mNotify);

            this.isRunning = true;
            this.startId = 0;

        } else if (!this.isRunning && startId == 0) {
            Log.e("if there was no sound ", " and you want end");

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


        Log.e("AlarmActivity", "In the service");

        return START_NOT_STICKY;
    }


        public void onDestroy() {
        Log.e("JSLog", "on destroy called");
        super.onDestroy();

        this.isRunning = false;
    }


}