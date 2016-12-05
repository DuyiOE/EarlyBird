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

    String today;
    Calendar calendar;
    ToggleButton togglebutton;




    public String getStringCurrentDay(){

        String daysArray[] = {"errorDay","Sonntag","Montag","Dienstag", "Mittwoch","Donnerstag","Freitag", "Samstag"};
        int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        String dayofWeek = daysArray[day];
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



    public static void setAlarm(TimePicker timePicker, Calendar calendar,TextView alarmTextView, CharSequence tg){


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



            setAlarmText(hour, minute, alarmTextView,(String) tg);
    }


    public static TextView setAlarmText(int hour,int minute, TextView text,String tg) {
        String minuteS = String.valueOf(minute);
        if (minute < 10) {
            minuteS = "0" + String.valueOf(minute);
        }
        text.setText("Der Wecker klingelt am " + tg + " um: " + hour + ":" + minuteS);
        text.setVisibility(View.VISIBLE);

        return text;
    }

    public static void resetAlarm() {
    }



      /**   if (alarmToggleSO.isChecked()) {
         return alarmToggleSO;
         } else if (alarmToggleMO.isChecked() && day == 2) {
         return alarmToggleMO;
         } else if (alarmToggleDI.isChecked() && day == 3) {
         return alarmToggleDI;
         } else if (alarmToggleMI.isChecked() && day == 4) {
         return alarmToggleMI;
         } else if (alarmToggleDO.isChecked() && day == 5) {
         return alarmToggleDO;
         } else if (alarmToggleFR.isChecked() && day == 6) {
         return alarmToggleFR;
         } else if (alarmToggleSA.isChecked() && day == 7) {
         return alarmToggleSA;
         }
         }
         }else {
         for (day = AlarmDate.getIntCurrentDay() + 1; day >= 7; day++) {
         if (alarmToggleSO.isChecked() && day == 1) {
         return alarmToggleSO;
         } else if (alarmToggleMO.isChecked() && day == 2) {
         return alarmToggleMO;
         } else if (alarmToggleDI.isChecked() && day == 3) {
         return alarmToggleDI;
         } else if (alarmToggleMI.isChecked() && day == 4) {
         return alarmToggleMI;
         } else if (alarmToggleDO.isChecked() && day == 5) {
         return alarmToggleDO;
         } else if (alarmToggleFR.isChecked() && day == 6) {
         return alarmToggleFR;
         } else if (alarmToggleSA.isChecked() && day == 7) {
         return alarmToggleSA;
         }else return null;

         }
         }
         this.alarmTextView.setText("Kein Tag wurde ausgewählt!");
         return null;

        return alarmToggleDI;
    }
       **/
}
