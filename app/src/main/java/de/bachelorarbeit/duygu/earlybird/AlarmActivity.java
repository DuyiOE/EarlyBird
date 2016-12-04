package de.bachelorarbeit.duygu.earlybird;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import java.util.Calendar;

import de.bachelorarbeit.duygu.earlybird.de.bachelorarbeit.duygu.earlybird.ui.AlarmDate;

/**
 * Created by Duygu on 26.11.2016.
 */
public class AlarmActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,CompoundButton.OnCheckedChangeListener {


    private static AlarmActivity inst;
    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    TimePicker alarmTimePicker;
    TimePicker desTimePicker;
    private TextView alarmTextView;
    private AlarmReceiver alarm;
    private Context context;
    Spinner spinner;
    int i = 0;
    final Calendar calendar = Calendar.getInstance();

    private ToggleButton toggleButton;

    public static AlarmActivity instance() {
        return inst;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        this.context = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Intent myIntent = new Intent(this.context, AlarmReceiver.class);


        //alarm = new AlarmReceiver();
        alarmTextView = (TextView) findViewById(R.id.infoText);
        // set the alarm to the time that you picked
        alarmTimePicker = (TimePicker) findViewById(R.id.TimePickerAlarm);
        alarmTimePicker.setIs24HourView(true);
        //Time choosen for Destination
        desTimePicker = (TimePicker) findViewById(R.id.TimePickerDes);
        desTimePicker.setIs24HourView(true);
        // Get the alarm manager service,
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        getNextCheckedToggleBtn().setOnCheckedChangeListener(this);
        this.onCheckedChanged(getNextCheckedToggleBtn(), true);
        myIntent.putExtra("extra", "yes");
        myIntent.putExtra("quote id", String.valueOf(i));
        pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmDate.setAlarmText(alarmTimePicker.getHour(), alarmTimePicker.getMinute(), alarmTextView);


        Button stop_alarm = (Button) findViewById(R.id.stop_Alarm);
        stop_alarm.setOnClickListener(new View.OnClickListener() {
            Context context;

            @Override
            public void onClick(View v) {
                final Intent myIntent = new Intent(this.context, AlarmReceiver.class);
                myIntent.putExtra("extra", "no");
                myIntent.putExtra("quote id", String.valueOf(i));
                sendBroadcast(myIntent);
                alarmManager.cancel(pendingIntent);
                alarmTextView.setText("Alarm canceled!");
                //setAlarmText("ID is " + i);
            }
        });

    }


    public ToggleButton getNextCheckedToggleBtn() {

        final ToggleButton alarmToggleMO = (ToggleButton) findViewById(R.id.toggleButtonMO);
        final ToggleButton alarmToggleDI = (ToggleButton) findViewById(R.id.toggleButtonDI);
        final ToggleButton alarmToggleMI = (ToggleButton) findViewById(R.id.toggleButtonMI);
        final ToggleButton alarmToggleDO = (ToggleButton) findViewById(R.id.toggleButtonDO);
        final ToggleButton alarmToggleFR = (ToggleButton) findViewById(R.id.toggleButtonFR);
        final ToggleButton alarmToggleSA = (ToggleButton) findViewById(R.id.toggleButtonSA);
        final ToggleButton alarmToggleSO = (ToggleButton) findViewById(R.id.toggleButtonSO);

        for (int day=AlarmDate.getIntCurrentDay(); day>=6; day++){
            if (alarmToggleSO.isChecked()&& day == 1) {
                return alarmToggleSO;
            } else if(alarmToggleMO.isChecked()&& day ==2) {
                return alarmToggleMO;
            } else if (alarmToggleDI.isChecked() && day==3){
                return alarmToggleDI;
            } else if (alarmToggleMI.isChecked()&& day==4) {
                return alarmToggleMI;
            } else if (alarmToggleDO.isChecked() && day==5){
                return alarmToggleDO;
            } else if (alarmToggleFR.isChecked()&& day==6) {
                return alarmToggleFR;
            } else if (alarmToggleSA.isChecked()&& day==7) {
                return alarmToggleSA;
            }
            }
            this.alarmTextView.setText("Keine Tag wurde ausgewählt!");
            return null;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        // AlarmDate.resetAlarm();
        AlarmDate.setAlarm(alarmTimePicker,calendar);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    public void setSupportActionBar(Toolbar toolbar) {
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
