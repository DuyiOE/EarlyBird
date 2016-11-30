package de.bachelorarbeit.duygu.earlybird.de.bachelorarbeit.duygu.earlybird.ui;

import android.util.Log;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

import de.bachelorarbeit.duygu.earlybird.R;

/**
 * Created by Duygu on 30.11.2016.
 */

public class AlarmDate {

    // set the alarm to the time that you picked
    final Calendar calendar = Calendar.getInstance();

    public String getCurrentDay(){

        String daysArray[] = {"errorDay","Sunday","Monday","Tuesday", "Wednesday","Thursday","Friday", "Saturday"};
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        String dayofWeek= daysArray[day];
        Log.d("AlarmActivity", dayofWeek );
        Log.d("AlarmActivity",String.valueOf(day));
        return dayofWeek;


    }
}
