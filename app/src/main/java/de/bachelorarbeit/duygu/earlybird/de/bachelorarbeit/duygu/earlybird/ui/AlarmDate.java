package de.bachelorarbeit.duygu.earlybird.de.bachelorarbeit.duygu.earlybird.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import java.util.Calendar;
import java.util.Date;

import de.bachelorarbeit.duygu.earlybird.AlarmActivity;
import de.bachelorarbeit.duygu.earlybird.R;

/**
 * Created by Duygu on 30.11.2016.
 */

public class AlarmDate {




    public static String getStringCurrentDay(){

        String daysArray[] = {"errorDay","Sonntag","Montag","Dienstag", "Mittwoch","Donnerstag","Freitag", "Samstag"};
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        String dayofWeek= daysArray[day];
        Log.d("AlarmActivity", dayofWeek );
        Log.d("AlarmActivity",String.valueOf(day));
        return dayofWeek;


    }

    public static int getIntCurrentDay(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        Log.d("AlarmActivity",String.valueOf(day));
        return day;



    }


    public static void setAlarm(TimePicker timePicker, Calendar calendar){


            if (android.os.Build.VERSION.SDK_INT >= 23) {
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                calendar.set(Calendar.MINUTE, timePicker.getMinute());
            } else {
                calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());

            }
            final int hour = timePicker.getCurrentHour();
            final int minute = timePicker.getCurrentMinute();

            String hour_string = String.valueOf(hour);
            Log.e("AlarmActivity", hour_string);
            String minute_string = String.valueOf(minute);
            Log.e("AlarmActivity", minute_string);


    }


    public static TextView setAlarmText(int hour,int minute, TextView text) {

        text.setText("Der Wecker klingelt am "+ AlarmDate.getStringCurrentDay()+" um: " + hour + ":" +  minute);
        text.setVisibility(View.VISIBLE);

        return text;
    }

    public static void resetAlarm() {
    }

}
