package de.bachelorarbeit.duygu.earlybird;

import android.annotation.SuppressLint;
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
import android.text.Spanned;
import android.text.method.TimeKeyListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.bachelorarbeit.duygu.earlybird.de.bachelorarbeit.duygu.earlybird.ui.AlarmDate;

import static android.R.id.edit;

/**
 * Created by Duygu on 26.11.2016.
 */
public class AlarmActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    @SuppressLint("StaticFieldLeak")
    private static AlarmActivity inst;
    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    TimePicker alarmTimePicker;
    TimePicker desTimePicker;
    private TextView alarmTextView;
    private Context context;
    int i = 0;
    final Calendar calendar = Calendar.getInstance();
    private EditText prepTime;

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
        Date dateObject;

        prepTime = (EditText) findViewById(R.id.editText_PrepTime);
        prepTime.setKeyListener(new TimeKeyListener() {
            public final char[] CHARS = new char[] {
                    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
            };

            @Override
            protected char[] getAcceptedChars() {
                return CHARS;
            }
        });

        //alarm = new AlarmReceiver();
        alarmTextView = (TextView) findViewById(R.id.infoText);
        final Intent myIntent = new Intent(this.context, AlarmReceiver.class);
        // set the alarm to the time that you picked
        alarmTimePicker = (TimePicker) findViewById(R.id.TimePickerAlarm);
        alarmTimePicker.setIs24HourView(true);
        //Time choosen for Destination
        desTimePicker = (TimePicker) findViewById(R.id.TimePickerDes);
        desTimePicker.setIs24HourView(true);
        // Get the alarm manager service,
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


        final ToggleButton alarmToggleMO = (ToggleButton) findViewById(R.id.toggleButtonMO);
        final ToggleButton alarmToggleDI = (ToggleButton) findViewById(R.id.toggleButtonDI);
        final ToggleButton alarmToggleMI = (ToggleButton) findViewById(R.id.toggleButtonMI);
        final ToggleButton alarmToggleDO = (ToggleButton) findViewById(R.id.toggleButtonDO);
        final ToggleButton alarmToggleFR = (ToggleButton) findViewById(R.id.toggleButtonFR);
        final ToggleButton alarmToggleSA = (ToggleButton) findViewById(R.id.toggleButtonSA);
        final ToggleButton alarmToggleSO = (ToggleButton) findViewById(R.id.toggleButtonSO);

        alarmToggleMO.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @TargetApi(Build.VERSION_CODES.M)
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if(AlarmDate.getIntCurrentDay()==2) {
                        AlarmDate.setAlarm(alarmTimePicker, calendar, alarmTextView,alarmToggleMO.getText());
                        myIntent.putExtra("extra", "yes");
                        myIntent.putExtra("quote id", String.valueOf(i));
                        pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                    }
                }
            }
        });
        alarmToggleDI.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @TargetApi(Build.VERSION_CODES.M)
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if(AlarmDate.getIntCurrentDay()==3) {
                        AlarmDate.setAlarm(alarmTimePicker, calendar, alarmTextView,alarmToggleDI.getText());
                        myIntent.putExtra("extra", "yes");
                        myIntent.putExtra("quote id", String.valueOf(i));
                        pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                    }
                }
            }
        });
        alarmToggleMI.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @TargetApi(Build.VERSION_CODES.M)
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if(AlarmDate.getIntCurrentDay()==4) {
                        AlarmDate.setAlarm(alarmTimePicker, calendar, alarmTextView,alarmToggleMI.getText());
                        myIntent.putExtra("extra", "yes");
                        myIntent.putExtra("quote id", String.valueOf(i));
                        pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                    }
                }
            }
        });
        alarmToggleDO.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @TargetApi(Build.VERSION_CODES.M)
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if(AlarmDate.getIntCurrentDay()==5) {
                        AlarmDate.setAlarm(alarmTimePicker, calendar, alarmTextView,alarmToggleDO.getText());
                        myIntent.putExtra("extra", "yes");
                        myIntent.putExtra("quote id", String.valueOf(i));
                        pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                    }
                }
            }
        });
        alarmToggleFR.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @TargetApi(Build.VERSION_CODES.M)
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if(AlarmDate.getIntCurrentDay()==6) {
                        AlarmDate.setAlarm(alarmTimePicker, calendar, alarmTextView,alarmToggleFR.getText());
                        myIntent.putExtra("extra", "yes");
                        myIntent.putExtra("quote id", String.valueOf(i));
                        pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                    }
                }
            }
        });
        alarmToggleSA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @TargetApi(Build.VERSION_CODES.M)
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if(AlarmDate.getIntCurrentDay()==7) {
                        AlarmDate.setAlarm(alarmTimePicker, calendar, alarmTextView,alarmToggleSA.getText());
                        myIntent.putExtra("extra", "yes");
                        myIntent.putExtra("quote id", String.valueOf(i));
                        pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                    }
                }
            }
        });
        alarmToggleSO.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @TargetApi(Build.VERSION_CODES.M)
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if(AlarmDate.getIntCurrentDay()==1) {
                        AlarmDate.setAlarm(alarmTimePicker, calendar, alarmTextView,alarmToggleSO.getText());
                        myIntent.putExtra("extra", "yes");
                        myIntent.putExtra("quote id", String.valueOf(i));
                        pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                    }
                }
            }
        });

            Button stop_alarm = (Button) findViewById(R.id.stop_Alarm);
            stop_alarm.setOnClickListener(new View.OnClickListener(){
                Context context;

                @Override
                public void onClick (View v){
                myIntent.putExtra("extra", "no");
                myIntent.putExtra("quote id", String.valueOf(i));
                sendBroadcast(myIntent);
                alarmManager.cancel(pendingIntent);
                alarmTextView.setText("Alarm canceled!");
                alarmManager.cancel(pendingIntent);
                //setAlarmText("ID is " + i);
                }
        });

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



    @Override
    public void onPause() {
        super.onPause();
    }

}
