package de.bachelorarbeit.duygu.earlybird;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import java.util.Calendar;

/**
 * Created by Duygu on 26.11.2016.
 */
public class AlarmActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private static AlarmActivity inst;
    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private TimePicker alarmTimePicker;
    private TextView alarmTextView;
    private AlarmReceiver alarm;
    private Context context;
    Spinner spinner;
    int i = 0;

    public static AlarmActivity instance() {
        return inst;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        this.context = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //alarm = new AlarmReceiver();
        alarmTextView = (TextView) findViewById(R.id.infoText);
        final Intent myIntent = new Intent(this.context, AlarmReceiver.class);

        // Get the alarm manager service
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // set the alarm to the time that you picked
        final Calendar calendar = Calendar.getInstance();
        //calendar.add(Calendar.SECOND, 3);
        alarmTimePicker = (TimePicker) findViewById(R.id.TimePickerAlarm);

        ToggleButton alarmToggleMO = (ToggleButton) findViewById(R.id.toggleButtonMO);
        /** ToggleButton alarmToggleDI = (ToggleButton) findViewById(R.id.toggleButtonDI);
         ToggleButton alarmToggleMI = (ToggleButton) findViewById(R.id.toggleButtonMI);
         ToggleButton alarmToggleDO = (ToggleButton) findViewById(R.id.toggleButtonDO);
         ToggleButton alarmToggleFR = (ToggleButton) findViewById(R.id.toggleButtonFR);
         ToggleButton alarmToggleSA = (ToggleButton) findViewById(R.id.toggleButtonSA);
         ToggleButton alarmToggleSO = (ToggleButton) findViewById(R.id.toggleButtonSO);
         **/
        alarmToggleMO.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)

            public void onClick(View v) {

                calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getHour());
                calendar.set(Calendar.MINUTE, alarmTimePicker.getMinute());

                final int hour = alarmTimePicker.getHour();
                final int minute = alarmTimePicker.getMinute();

                String minute_string = String.valueOf(minute);
                String hour_string = String.valueOf(hour);

                if (minute < 10) {
                    minute_string = "0" + String.valueOf(minute);
                }

                if (hour > 12) {
                    hour_string = String.valueOf(hour - 12);
                }

                myIntent.putExtra("extra", "yes");
                myIntent.putExtra("quote id", String.valueOf(i));
                pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                setAlarmText("Alarm set to " + hour_string + ":" + minute_string);
            }


        });

      /**  Button stop_alarm = (Button) findViewById(R.id.stop_Alarm);
        stop_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myIntent.putExtra("extra", "no");
                myIntent.putExtra("quote id", String.valueOf(i));
                sendBroadcast(myIntent);

                alarmManager.cancel(pendingIntent);
                setAlarmText("Alarm canceled");

                //setAlarmText("ID is " + i);
            }
        });


**/

    }
    public void setSupportActionBar(Toolbar toolbar) {
    }

    public void onToggleClicked(View view) {
        if (((ToggleButton) view).isChecked()) {
            Log.d("AlarmActivtiy", "Alarm On");
            Calendar calendar = Calendar.getInstance();
            if (android.os.Build.VERSION.SDK_INT >= 23) {
                calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getHour());
                calendar.set(Calendar.MINUTE, alarmTimePicker.getMinute());
            } else {
                calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());
            }
            Intent myIntent = new Intent(AlarmActivity.this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, 0, myIntent, 0);
            alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.cancel(pendingIntent);
            setAlarmText("");
            Log.d("AlarmActivity", "Alarm Off");
        }
    }

    public void setAlarmText(String alarmText) {
        alarmTextView.setText(alarmText);
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.e("AlarmActivity", "on Destroy");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        //Toast.makeText(parent.getContext(), "Spinner item 3!" + id, Toast.LENGTH_SHORT).show();
        i = (int) id;
    }


}