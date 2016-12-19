package de.bachelorarbeit.duygu.earlybird;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.text.method.TimeKeyListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;
import java.util.Date;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;

import de.bachelorarbeit.duygu.earlybird.de.bachelorarbeit.duygu.earlybird.ui.AlarmDate;

/**
 * Created by Duygu on 26.11.2016.
 */
public class AlarmActivity extends Activity implements AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private static final String TAG = AlarmActivity.class.getSimpleName();

    private static final int ERROR_NO_DAY_SET = -1;

    /* PlacePicker IDs */
    public static final int PLACE_PICKER_START = 1000;
    public static final int PLACE_PICKER_DEST = 1001;


    private ToggleButton alarmToggleMO;
    private ToggleButton alarmToggleDI;
    private ToggleButton alarmToggleMI;
    private ToggleButton alarmToggleDO;
    private ToggleButton alarmToggleFR;
    private ToggleButton alarmToggleSA;
    private ToggleButton alarmToggleSO;

    private TextView startAddressTV;
    private TextView destAddressTV;

    private Place start;
    private Place destination;


    private static AlarmActivity inst;
    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    TimePicker alarmTimePicker;
    TimePicker desTimePicker;
    private TextView alarmTextView;
    private Context context;
    int i = 0;
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
        // set the alarm to the time that you picked
        alarmTimePicker = (TimePicker) findViewById(R.id.TimePickerAlarm);
        alarmTimePicker.setIs24HourView(true);
        //Time choosen for Destination
        desTimePicker = (TimePicker) findViewById(R.id.TimePickerDes);
        desTimePicker.setIs24HourView(true);
        // Get the alarm manager service,
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // CHANGES FROM HERE....

        // Find all toggle-buttons
        alarmToggleMO = (ToggleButton) findViewById(R.id.toggleButtonMO);
        alarmToggleDI = (ToggleButton) findViewById(R.id.toggleButtonDI);
        alarmToggleMI = (ToggleButton) findViewById(R.id.toggleButtonMI);
        alarmToggleDO = (ToggleButton) findViewById(R.id.toggleButtonDO);
        alarmToggleFR = (ToggleButton) findViewById(R.id.toggleButtonFR);
        alarmToggleSA = (ToggleButton) findViewById(R.id.toggleButtonSA);
        alarmToggleSO = (ToggleButton) findViewById(R.id.toggleButtonSO);

        // set all toggle-buttons to the same listener (this) -> @see onCheckedChanged (line 275)
        alarmToggleMO.setOnCheckedChangeListener(this);
        alarmToggleDI.setOnCheckedChangeListener(this);
        alarmToggleMI.setOnCheckedChangeListener(this);
        alarmToggleDO.setOnCheckedChangeListener(this);
        alarmToggleFR.setOnCheckedChangeListener(this);
        alarmToggleSA.setOnCheckedChangeListener(this);
        alarmToggleSO.setOnCheckedChangeListener(this);

        // THIS WILL BE IN THE NEW METHOD: @see -> setAlarm (line 441)
                /*if (isChecked) {
                    AlarmDate.setAlarm(alarmTimePicker, calendar, alarmTextView,setStringAlarmDay(alarmToggleSA));
                    if(AlarmDate.getIntCurrentDay()==1) {
                        myIntent.putExtra("extra", "yes");
                        myIntent.putExtra("quote id", String.valueOf(i));
                        pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                    }
                }
            }
        });*/
        startAddressTV = (TextView) findViewById(R.id.startAddressTV);
        startAddressTV.setOnClickListener(this);

        destAddressTV = (TextView) findViewById(R.id.destAddressTV);
        destAddressTV.setOnClickListener(this);


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



    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        /* Called when ever a button got changed.
        * This will
        * - remove all alarms and
        * - find the new next alarm time.
        * - Then set the alarm to this time and day!
        *
        * Steps in detail:
        * - Determine set alarmTime
        * - Check if alarmTime is today
        *   if alarmTime is today -> See if it is in the future!
        *       if alarmTime is in the future
        *           setAlarmTo(AlarmTime, today)
        *       else
        *           determine next checked day
        *   else (must be another day)
        *       determine next checked day
        *
        *   setAlarmTo(AlarmTime, daysInFuture)
        **/

        int dayOfTheWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);

        /* Step 1: Prepare hour and minute of the alarm. */
        int alarmDaysInFuture;  // Delta for "how many days in the future"
        int alarmHour;          // Hour for the alarm
        int alarmMinute;        // Minute for the alarm

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // TODO: Set the alarmPicker to a resonable time (e.g. last set alarm)
            alarmHour = alarmTimePicker.getHour();
            alarmMinute = alarmTimePicker.getMinute();
        } else {    // deprecated call for Android API < 23
            alarmHour = alarmTimePicker.getCurrentHour();
            alarmMinute = alarmTimePicker.getCurrentMinute();
        }
        Log.i(TAG, "Alarm time set to: " + alarmHour + " : " + alarmMinute);

        /* Step 2: Check if today was set, and if not, how many days in the future it is. */
        if (dayIsChecked(dayOfTheWeek)) {   // Today was checked -> Check if time passed
            if (! timePassedToday(alarmHour, alarmMinute)) {
                Log.i(TAG, "Alarm time did not pass today: Set alarm to today");
                alarmDaysInFuture = 0;
            } else {
                alarmDaysInFuture = getDaysToNextCheckedDay();
                Log.i(TAG, "Alarm time already passed today. Next Alarm is " + alarmDaysInFuture + " days in the future");
            }

        } else {                            // Today was not checked. See which day is next available
            alarmDaysInFuture = getDaysToNextCheckedDay();
            Log.i(TAG, "Alarm is " + alarmDaysInFuture + " days in the future");
        }


        /* Step 3: Notify the user about the next set alarm! */
        if (alarmDaysInFuture == ERROR_NO_DAY_SET) {    // No day was set
            Toast.makeText(this, getString(R.string.alarm_set_no_day), Toast.LENGTH_SHORT).show();
            return;

        } else if (alarmDaysInFuture == 0) {            // Alarm is today
            Toast.makeText(this,
                    String.format(getString(R.string.alarm_set_time_today), alarmHour, alarmMinute),
                    Toast.LENGTH_SHORT).show();

        } else {                                        // Alarm is another day
            Toast.makeText(this,
                    String.format(getString(R.string.alarm_set_time_other_day), alarmHour, alarmMinute, alarmDaysInFuture ),
                    Toast.LENGTH_SHORT).show();
        }
        final Intent myIntent = this.getIntent();

        /* Step 4: Set alarm to time with x days in future */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setAlarm(alarmHour, alarmMinute, alarmDaysInFuture);
        }
    }

    /** Determines whether the current day is checked.
     *
     * @return boolean true if the button for today was checked */
    private boolean dayIsChecked(int dayOfTheWeek){
        switch (dayOfTheWeek) {
            case Calendar.MONDAY:                   // #2
                return alarmToggleMO.isChecked();
            case Calendar.TUESDAY:                  // #3
                return alarmToggleDI.isChecked();
            case Calendar.WEDNESDAY:                // #4
                return alarmToggleMI.isChecked();
            case Calendar.THURSDAY:                 // #5
                return alarmToggleDO.isChecked();
            case Calendar.FRIDAY:                   // #6
                return alarmToggleFR.isChecked();
            case Calendar.SATURDAY:                 // #7
                return alarmToggleSA.isChecked();
            case Calendar.SUNDAY:                   // #1 (no index 0 for days!)
                return alarmToggleSO.isChecked();
        }
        Log.w(TAG, "Checking day " + dayOfTheWeek + " is no valid day!");
        return false;
    }

    /** Determines whether today already passed a given time (hour and minute)
     *
     *  <strong>NOTE: This does not check the day (nor month or year)
     *  It only tells if a certain hour and minute already passed today. </strong>
     *
     * @param hour hour to check
     * @param minute minute to check
     * @return boolean true if given time already passed today
     */
    private boolean timePassedToday(int hour, int minute){
        Calendar time = Calendar.getInstance();
        time.set(Calendar.HOUR_OF_DAY, hour);
        time.set(Calendar.MINUTE, minute);
        return time.before(Calendar.getInstance());
    }

    /** Gets the days until the next selected day.
     *
     * Get the current day of the week (as defined in Calendar)
     * iterate over 7 days (max) in order to find the next day that was checked.
     *
     * first day that was checked will be returned.
     *
     * @return int amount of days until next selected day
     */
    private int getDaysToNextCheckedDay(){
        int today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);

        Log.d(TAG, "Today is calendar day " + today);

        /* Starting with tomorrow, we may increment and get the day number calculated % 7 (days of the week).
        * This gives the option to start over at the first day if we roll over a week.
        *
        * e.g. day 8 is the same as day (8 % 7) which is day 1
        * We also have to check until in 8 days since we start tomorrow
        * (so in order to get todays date we roll over one week)
        *
        * NOTE:
        * -> Adding +1 to the result is important since the first day of the week starts with index 1
        * So first day would be day 1 not 0, last day is 7 not 6 (week is 1..7 no 0..6)
        * -> modulo 7 is required (to start roll over into a new week) so day 8 = day 1 again
        * -> int i = 1 guarantees starting with the NEXT DAY (since we checked today earlier)
        */
        for (int i = 0; i < 7; i++){    // for every day (up to 7 days) starting from tomorrow
            int checkWeekDay = ((today + i) % 7) + 1;
            Log.d(TAG, "Checking day " + checkWeekDay  + " (which is in " + (i+1) + " days)");

            if (dayIsChecked(checkWeekDay)){
                Log.i(TAG, "Next selected week day: " + checkWeekDay);
                return i + 1;   // Return the first checked day
            }
        }

        Log.w(TAG, "No day was selected. Returning " + ERROR_NO_DAY_SET + " in order to signal this error");
        return ERROR_NO_DAY_SET;
    }

    /** Sets the alarm for a specified hour and minute at a certain day in the week
     *
     * <strong> daysInFuture has to be a positive value in order to set a calendar date in the future.
     * If the date is the current day, daysInFuture has to be 0.</strong>
     *
     * uses: https://developer.android.com/reference/java/util/Calendar.html#roll(int,%20int)
     *
     * @param hour hour of the alarm in 24 hour format
     * @param min minute of the alarm
     * @param daysInFuture days
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setAlarm(int hour, int min, int daysInFuture) {
        final Intent myIntent = new Intent(this.context, AlarmReceiver.class);
        Calendar alarmTime = Calendar.getInstance(); // no need for a field variable

        if (android.os.Build.VERSION.SDK_INT >= 23) {
            alarmTime.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getHour());
            alarmTime.set(Calendar.MINUTE, alarmTimePicker.getMinute());
        } else {
            alarmTime.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());
            alarmTime.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
        }
        Log.d(TAG, "Calendar day before rolling: " + alarmTime.get(Calendar.DAY_OF_YEAR));
        alarmTime.roll(Calendar.DAY_OF_YEAR, daysInFuture);
        Log.d(TAG, "Calendar day: " + alarmTime.get(Calendar.DAY_OF_YEAR));

        /* TODO: Set the alarm here.
        * My first guess would be setting the date plus adding "daysInFuture" days (or x times 24 hours...)
        * If the day is today these days are 0 so adding this multiplication should never hurt!
        */
        int today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        int i= 7;//setting the right day
        if (today + daysInFuture- i==0){
            i=0;
        }
        //Alarm is ringing today, if it is set today and time hasn't passed.
        if (dayIsChecked(today) && timePassedToday(hour, min) == false) {
            AlarmDate.setAlarmText(hour, min, alarmTextView, "heute");
            myIntent.putExtra("extra", "yes");
            myIntent.putExtra("quote id", "0");
            pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime.getTimeInMillis(), pendingIntent);
            //Alarm is ringing today in one week, if it is set today and time has passed
        }else if (dayIsChecked(today)&& timePassedToday(hour, min)== true) {
            AlarmDate.setAlarmText(hour, min, alarmTextView, dayIsCheckedString(today));
            int day = getDaysToNextCheckedDay();
            Log.e("AlarmActivity", String.valueOf(day));
            myIntent.putExtra("extra", "yes");
            myIntent.putExtra("quote id", "0");
            pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime.getTimeInMillis() + (day * 24), pendingIntent);
        } else { //Alarm is ringing on nest checked day of the week, if it isn't set today.
            AlarmDate.setAlarmText(hour, min, alarmTextView, dayIsCheckedString(today + daysInFuture-i));
            int day = getDaysToNextCheckedDay();
            Log.e("AlarmActivity", String.valueOf(day));
            myIntent.putExtra("extra", "yes");
            myIntent.putExtra("quote id", "0");
            pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime.getTimeInMillis() + (day * 24), pendingIntent);
            Log.e("AlarmActivity", String.valueOf(alarmTime.getTimeInMillis() + (day * 24)));
        }

        // Stop the Alarm
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
    private String dayIsCheckedString(int dayOfTheWeek) {

        switch (dayOfTheWeek) {
            case Calendar.MONDAY:
                return "Montag";
            case Calendar.TUESDAY:
                return "Dienstag";
            case Calendar.WEDNESDAY:
                return "Mittwoch";
            case Calendar.THURSDAY:
                return "Donnerstag";
            case Calendar.FRIDAY:
                return "Freitag";
            case Calendar.SATURDAY:
                return "Samstag";
            case Calendar.SUNDAY:
                return "Sonntag";
        }

        Log.w(TAG, "Checking day " + dayOfTheWeek + " is no valid day!");
        return "";
    }

    /** Click listener for Start and Destination TextView
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (v == startAddressTV) {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            try {
                Intent i = builder.build(this);
                this.startActivityForResult(i, AlarmActivity.PLACE_PICKER_START);
            } catch (Exception e) {
                Log.w(TAG, "Something went wrong with the place picker!" + e);
            }
        } else if (v == destAddressTV) {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            try {
                Intent i = builder.build(this);
                startActivityForResult(i, AlarmActivity.PLACE_PICKER_DEST);
            } catch (Exception e) {
                Log.w(TAG, "Something went wrong with the place picker!" + e);
            }
        }
    }

    /** Will be called with a result from the PlacePicker
     *
     * @param resultCode Gives Ok or Fail
     * @param requestCode The calling code in order to distinguish selecting start or destination
     * @param data Intent data */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            Log.d(TAG, "Problem picking a place. Try again!");
            return;
        }

        if (requestCode == AlarmActivity.PLACE_PICKER_START) {
            start = PlacePicker.getPlace(getBaseContext(), data);
            startAddressTV.setText(start.getAddress());

        } else if (requestCode == AlarmActivity.PLACE_PICKER_DEST) {
            destination = PlacePicker.getPlace(getBaseContext(), data);
            destAddressTV.setText(destination.getAddress());
        }
    }

}

