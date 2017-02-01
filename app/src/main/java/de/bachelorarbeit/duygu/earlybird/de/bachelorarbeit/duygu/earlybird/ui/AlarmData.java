package de.bachelorarbeit.duygu.earlybird.de.bachelorarbeit.duygu.earlybird.ui;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Duygu on 30.11.2016.
 */

public class AlarmData {




    public static int getRandomMinute(){
        int randomMinutes = (int) (Math.random()*60);
        return randomMinutes;

    }

    public static String removeSpace(String value) {
        String ValueWithoutSign = String.valueOf(value);
        ValueWithoutSign = ValueWithoutSign.replace(" ", "");
        ValueWithoutSign = ValueWithoutSign.replace("-", "");
        ValueWithoutSign = ValueWithoutSign.replace(",", "");
        return ValueWithoutSign;
    }


    public static TextView setPrepText(TextView text, int minute) {
        String minuteS = String.valueOf(minute);
        if (minute < 10) {
            minuteS = "0" + String.valueOf(minute);
        }
        if(minute>=0) {
            text.setText("Nach derzeitiger Verkehrslage beträgt deine Vorbereitungszeit " + minuteS + " Minuten.");
            text.setVisibility(View.VISIBLE);
            return text;
        } else
            text.setText("Es wurde keine Route ausgewählt! Vorbereitungszeit konnte nicht errechnet werden.");
            text.setVisibility(View.VISIBLE);
            return text;
    }

    public static TextView setInfoText(TextView text, TextView text2, String duration, boolean earlier,String lag_time) {

        if (earlier== true) {
            text.setText("Du wurdest " + lag_time + " Minuten früher geweckt, da es auf deiner Route zu Verzögerungen kommt.");
            text.setVisibility(View.VISIBLE);
            text2.setText("Nach derzeitiger Verkehrslage, sind es " + duration + " Fahrtzeit.");
            text2.setVisibility(View.VISIBLE);
        }else{
            text.setText("Du wurdest zur eingestellten Weckzeit geweckt. Es gibt keine Verzögerungen auf deiner Route.");
            text.setVisibility(View.VISIBLE);
            text2.setText("Nach derzeitiger Verkehrslage, sind es " + duration + " Fahrtzeit.");
            text2.setVisibility(View.VISIBLE);
        }
        return text;
    }




}
