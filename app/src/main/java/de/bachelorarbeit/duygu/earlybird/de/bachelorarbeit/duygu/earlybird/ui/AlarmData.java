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
    public static int getRandomMinuteSmall(){
        int randomMinutes = (int) (Math.random()*10);
        return randomMinutes;

    }

    public static int getRandomDurationMinute(){
        int minL = getRandomMinute();
        int minS = getRandomMinuteSmall();
        int i=getRandomMinute();
        if(i%2==0){
            return minL-minS;
        }else{
            return minL+minS;
        }

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
        if(minute>=00) {
            text.setText("Nach derzeitiger Verkehrslage beträgt deine Vorbereitungszeit " + minuteS + " Minuten.");
            text.setVisibility(View.VISIBLE);
            return text;
        } else
            text.setText("Die Vorbereitungszeit konnte nicht errechnet werden. Es wurde keine Route ausgewählt oder der Ankunftszeitpunkt liegt vor der Weckzeit!");
            text.setVisibility(View.VISIBLE);
            return text;
    }

    public static TextView setInfoText(TextView text, TextView text2, String hr_duration,String min_duration, boolean earlier,String lag_time) {

        if (earlier== true) {
            text.setText("Du wurdest " + lag_time + " Minuten früher geweckt, da es auf deiner Route zu Verzögerungen kommt.");
            text.setVisibility(View.VISIBLE);
            text2.setText("Nach derzeitiger Verkehrslage, sind es " + hr_duration+" Stunde/n und "+ min_duration + " Minuten Fahrtzeit.");
            text2.setVisibility(View.VISIBLE);
        }else{
            text.setText("Du wurdest Pünktlich geweckt! Es gibt keine Verzögerungen auf deiner Route.");
            text.setVisibility(View.VISIBLE);
            text2.setText("Nach derzeitiger Verkehrslage, sind es " + hr_duration+" Stunde/n und "+ min_duration  + " Minuten Fahrtzeit.");
            text2.setVisibility(View.VISIBLE);
        }
        return text;
    }




}
