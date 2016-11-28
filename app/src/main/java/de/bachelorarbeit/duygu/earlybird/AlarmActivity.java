package de.bachelorarbeit.duygu.earlybird;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import java.util.Calendar;

/**
 * Created by Duygu on 26.11.2016.
 */
public class AlarmActivity extends Activity {


    private static AlarmActivity inst;
    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private TimePicker alarmTimePicker;

    private TextView alarmTextView;

    public static AlarmActivity instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        alarmTimePicker = (TimePicker) findViewById(R.id.TimePickerAlarm);
        alarmTextView = (TextView) findViewById(R.id.textView);
        ToggleButton alarmToggleMO = (ToggleButton) findViewById(R.id.toggleButtonMO);
       /** ToggleButton alarmToggleDI = (ToggleButton) findViewById(R.id.toggleButtonDI);
        ToggleButton alarmToggleMI = (ToggleButton) findViewById(R.id.toggleButtonMI);
        ToggleButton alarmToggleDO = (ToggleButton) findViewById(R.id.toggleButtonDO);
        ToggleButton alarmToggleFR = (ToggleButton) findViewById(R.id.toggleButtonFR);
        ToggleButton alarmToggleSA = (ToggleButton) findViewById(R.id.toggleButtonSA);
        ToggleButton alarmToggleSO = (ToggleButton) findViewById(R.id.toggleButtonSO);
        **/ alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    }

    public void onToggleClicked(View view) {
        if (((ToggleButton) view).isChecked()) {
            Log.d("MainActivtiy", "Alarm On");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
            calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());
            Intent myIntent = new Intent(AlarmActivity.this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, 0, myIntent, 0);
            alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.cancel(pendingIntent);
            setAlarmText("");
            Log.d("MainActivity", "Alarm Off");
        }
    }

    public void setAlarmText(String alarmText) {
        alarmTextView.setText(alarmText);
    }
}
