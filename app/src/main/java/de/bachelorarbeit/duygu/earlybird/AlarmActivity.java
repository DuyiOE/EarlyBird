package de.bachelorarbeit.duygu.earlybird;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.location.places.Place;

import java.util.Calendar;

import de.bachelorarbeit.duygu.earlybird.de.bachelorarbeit.duygu.earlybird.ui.AlarmDate;


/**
 * Created by Duygu on 26.11.2016.
 */
public class AlarmActivity extends Activity implements AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener, DistanceTask.Distance, RouteTask.RouteS {

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

    private Place start;
    private Place destination;
    int PLACE_PICKER_REQUEST = 1;
    private static final int REQUEST_PLACE_PICKER = 1;

    private static AlarmActivity inst;
    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    TimePicker alarmTimePicker;
    TimePicker arrivelTimePicker;
    private TextView alarmTextView;
    private Context context;
    int i = 0;

    //private EditText prepTime;

    Button btn_getPublic;
    Button btn_getCar;
    String str_from;
    String str_to;
    TextView tv_result1, tv_result2;
    private EditText destET;
    private EditText startET;
    Double start_lat;
    Double start_lng;
    Switch onOff;



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

        initialize();



        alarmTimePicker.setIs24HourView(true);
        //Time choosen for Destination
        arrivelTimePicker.setIs24HourView(true);
        // Get the alarm manager service,
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        onOff.setChecked(true);
        // set the alarm to the time that you picked
        onOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("Switch State=", "" + isChecked);
                if (onOff.isChecked()) {
                    alarmToggleDI.setClickable(true);
                    alarmToggleMO.setClickable(true);
                    alarmToggleDI.setClickable(true);
                    alarmToggleMI.setClickable(true);
                    alarmToggleDO.setClickable(true);
                    alarmToggleFR.setClickable(true);
                    alarmToggleSA.setClickable(true);
                    alarmToggleSO.setClickable(true);
                }
                Log.v("Switch State=", "" + isChecked);
                if (!onOff.isChecked()) {
                    alarmToggleMO.setClickable(false);
                    alarmToggleDI.setClickable(false);
                    alarmToggleMI.setClickable(false);
                    alarmToggleDO.setClickable(false);
                    alarmToggleFR.setClickable(false);
                    alarmToggleSA.setClickable(false);
                    alarmToggleSO.setClickable(false);
                    alarmToggleMO.setChecked(false);
                    alarmToggleDI.setChecked(false);
                    alarmToggleMI.setChecked(false);
                    alarmToggleDO.setChecked(false);
                    alarmToggleFR.setChecked(false);
                    alarmToggleSA.setChecked(false);
                    alarmToggleSO.setChecked(false);

                    Toast.makeText(context, "Alarm is off.", Toast.LENGTH_SHORT).show();
                }

            }
        });


        // set all toggle-buttons to the same listener (this) -> @see onCheckedChanged (line 275)
        alarmToggleMO.setOnCheckedChangeListener(this);
        alarmToggleDI.setOnCheckedChangeListener(this);
        alarmToggleMI.setOnCheckedChangeListener(this);
        alarmToggleDO.setOnCheckedChangeListener(this);
        alarmToggleFR.setOnCheckedChangeListener(this);
        alarmToggleSA.setOnCheckedChangeListener(this);
        alarmToggleSO.setOnCheckedChangeListener(this);
       /* prepTime.setKeyListener(new TimeKeyListener() {
            public final char[] CHARS = new char[]{
                    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
            };

            @Override
            protected char[] getAcceptedChars() {
                return CHARS;
            }
        });
        */

        /**Called when butten get clicked.
         *  This will
         * - make a URL Rsponse for Google MAP Distance Matrix
         * - find the new next alarm time.
         */

        btn_getCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str_from_for_maps = startET.getText().toString();
                String str_to_for_maps = destET.getText().toString();
                //Removing "space" and "-" from Address Text
                str_from = removeSpace(startET.getText().toString());
                str_to = removeSpace(destET.getText().toString());

                // get the Distance
                String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + str_from + "&destinations=" + str_to + "&mode=driving&language=de-DE&avoid=tolls&key=AIzaSyDj5-Q_k24sZQPipJLFlqRM72rsY7PfFmY";
                new DistanceTask(AlarmActivity.this).execute(url);

                // get the Route
                String urlR = "https://maps.googleapis.com/maps/api/directions/json?origin=" + str_from + "&destination=" + str_to + "&waypoints=" + str_from + "|" + str_to + "&key=AIzaSyAAD5gqRAcoj8bImHJzmSEJpxbS05KK6Cg";
                new RouteTask(AlarmActivity.this).execute(urlR);

                //show the map
                Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?saddr=" + str_from_for_maps + "&daddr=" + str_to_for_maps + "&mode=d");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

 /*TODO funktionniert nicht, laut Consolse Fehler in der XML Datei acticity_map
                @Override
                public void onClick(final View v) {
                    if("".equals(startET.getText().toString().trim())) {
                        Toast.makeText(AlarmActivity.this, "Enter the starting point", Toast.LENGTH_SHORT).show();
                    }
                    else if("".equals(destET.getText().toString().trim())) {
                        Toast.makeText(AlarmActivity.this, "Enter the destination point", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Intent mapIntent = new Intent(AlarmActivity.this, MapActivity.class);
                        mapIntent.putExtra("FROM", startET.getText().toString().trim());
                        mapIntent.putExtra("TO", destET.getText().toString().trim());
                        AlarmActivity.this.startActivity(mapIntent);
                    }
                }



    **/
        btn_getPublic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Removing "space" and "-" from Address Text
                String str_to_for_maps = destET.getText().toString();
                String str_from_for_maps = startET.getText().toString();
                str_from = removeSpace(startET.getText().toString());
                str_to = removeSpace(destET.getText().toString());
                // get the Distance
                String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + str_from + "&destinations=" + str_to + "&mode=transit&language=de-DE&avoid=tolls&key=AIzaSyDj5-Q_k24sZQPipJLFlqRM72rsY7PfFmY";
                new DistanceTask(AlarmActivity.this).execute(url);

                // get the Route
                String urlR = "https://maps.googleapis.com/maps/api/directions/json?origin=" + str_from + "&destination=" + str_to + "&waypoints=" + str_from + "|" + str_to + "&key=AIzaSyAAD5gqRAcoj8bImHJzmSEJpxbS05KK6Cg";
                new RouteTask(AlarmActivity.this).execute(urlR);

                //show the map
                Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?saddr=" + str_from_for_maps + "&daddr=" + str_to_for_maps + "&mode=transit");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);

                /*Intent mapIntent = new Intent(getApplicationContext(), MapActivity.class);
                mapIntent.setPackage("com.google.android.apps.maps");
                mapIntent.putExtra("FROM",str_from);
                mapIntent.putExtra("TO",str_to);
                startActivity(mapIntent);
                */

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


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
        *Check if the arrival time is befor alarm time
        * if arrival time > than alarmtime
        *   setAlarmTo(AlarmTime, daysInFuture)
        *   else makeToast
        **/

        int dayOfTheWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);

        /* Step 1: Prepare hour and minute of the alarm. */
        int alarmDaysInFuture;  // Delta for "how many days in the future"
        int alarmHour;          // Hour for the alarm
        int alarmMinute;        // Minute for the alarm
        int arrHour;            // Hour for the Destination time
        int arrMinute;         // Minute for the Destination time




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // TODO: Set the alarmPicker to a resonable time (e.g. last set alarm)
            alarmHour = alarmTimePicker.getHour();
            alarmMinute = alarmTimePicker.getMinute();
        } else {    // deprecated call for Android API < 23
            alarmHour = alarmTimePicker.getCurrentHour();
            alarmMinute = alarmTimePicker.getCurrentMinute();
        }
        Log.i(TAG, "Alarm time set to: " + alarmHour + " : " + alarmMinute);

         /*Step 2 Prepare Time of Destination
       */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // TODO: Set the alarmPicker to a resonable time (e.g. last set alarm)
            arrHour = arrivelTimePicker.getHour();
            arrMinute = arrivelTimePicker.getMinute();
        } else {    // deprecated call for Android API < 23
            arrHour = arrivelTimePicker.getCurrentHour();
            arrMinute = arrivelTimePicker.getCurrentMinute();
        }

        Log.i(TAG, "Arrival time time set to: " + arrHour + " : " + arrMinute);
        /* Step 3: Check if today was set, and if not, how many days in the future it is. */
                if (dayIsChecked(dayOfTheWeek)) {   // Today was checked -> Check if time passed
                    if (!timePassedToday(alarmHour, alarmMinute)) {
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


        /* Step 4: Notify the user about the next set alarm! */
                if (alarmDaysInFuture == ERROR_NO_DAY_SET) {    // No day was set
                    Toast.makeText(this, getString(R.string.alarm_set_no_day), Toast.LENGTH_SHORT).show();
                    return;

                } else if (alarmDaysInFuture == 0) {            // Alarm is today
                    Toast.makeText(this,
                            String.format(getString(R.string.alarm_set_time_today), alarmHour, alarmMinute),
                            Toast.LENGTH_SHORT).show();

                } else {                                        // Alarm is another day
                    Toast.makeText(this,
                            String.format(getString(R.string.alarm_set_time_other_day), alarmHour, alarmMinute, alarmDaysInFuture),
                            Toast.LENGTH_SHORT).show();
                }


        /* Step 5: Set alarm to time with x days in future */
                    if (arrHour >= alarmHour) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            setAlarm(alarmHour, alarmMinute, alarmDaysInFuture);
                        }

                    } else {
                        Toast.makeText(this, "The arrival time is before the alarm time. Pleace Check your Alarm.",
                                Toast.LENGTH_SHORT).show();
                    }



    }



    /**
     * Determines whether the current day is checked.
     *
     * @return boolean true if the button for today was checked
     */
    private boolean dayIsChecked(int dayOfTheWeek) {
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

    /**
     * Determines whether today already passed a given time (hour and minute)
     * <p>
     * <strong>NOTE: This does not check the day (nor month or year)
     * It only tells if a certain hour and minute already passed today. </strong>
     *
     * @param hour   hour to check
     * @param minute minute to check
     * @return boolean true if given time already passed today
     */
    private boolean timePassedToday(int hour, int minute) {
        Calendar time = Calendar.getInstance();
        time.set(Calendar.HOUR_OF_DAY, hour);
        time.set(Calendar.MINUTE, minute);
        return time.before(Calendar.getInstance());
    }

    /**
     * Gets the days until the next selected day.
     * <p>
     * Get the current day of the week (as defined in Calendar)
     * iterate over 7 days (max) in order to find the next day that was checked.
     * <p>
     * first day that was checked will be returned.
     *
     * @return int amount of days until next selected day
     */
    private int getDaysToNextCheckedDay() {
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
        for (int i = 0; i < 7; i++) {    // for every day (up to 7 days) starting from tomorrow
            int checkWeekDay = ((today + i) % 7) + 1;
            Log.d(TAG, "Checking day " + checkWeekDay + " (which is in " + (i + 1) + " days)");

            if (dayIsChecked(checkWeekDay)) {
                Log.i(TAG, "Next selected week day: " + checkWeekDay);
                return i + 1;   // Return the first checked day
            }
        }

        Log.w(TAG, "No day was selected. Returning " + ERROR_NO_DAY_SET + " in order to signal this error");
        return ERROR_NO_DAY_SET;
    }

    /**
     * Sets the alarm for a specified hour and minute at a certain day in the week
     * <p>
     * <strong> daysInFuture has to be a positive value in order to set a calendar date in the future.
     * If the date is the current day, daysInFuture has to be 0.</strong>
     * <p>
     * uses: https://developer.android.com/reference/java/util/Calendar.html#roll(int,%20int)
     *  @param hour         hour of the alarm in 24 hour format
     * @param min          minute of the alarm
     * @param daysInFuture days
     *
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setAlarm(int hour, int min, int daysInFuture) {
        final Intent myIntent = new Intent(this.context, AlarmReceiver.class);
        final Intent myIntent2 = new Intent(this.context, WakeUpActivity.class);
        //final Intent myIntent3 = this.getIntent();

        //Notifikation that the Alarm clock is on. Turned back to AlarmAktivity if clicked
        Intent intent1 = new Intent(this.context, AlarmActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent1, 0);

        Notification mNotify = new Notification.Builder(this)
                .setContentText("Early Bird alarm clock is on!")
                .setSmallIcon(R.drawable.logo)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .build();


        final NotificationManager mNM = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

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
        int i = 7;//setting the right day
        if (today + daysInFuture - i == 0) {
            i = 0;
        }
        //Alarm is ringing today, if it is set today and time hasn't passed.
        if (dayIsChecked(today) && timePassedToday(hour, min) == false) {
            AlarmDate.setAlarmText(hour, min, alarmTextView, "heute");
            myIntent.putExtra("extra", "yes");
            myIntent.putExtra("quote id", "0");
            pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime.getTimeInMillis(), pendingIntent);
            mNM.notify(0, mNotify);
            //Alarm is ringing today in one week, if it is set today and time has passed
        } else if (dayIsChecked(today) && timePassedToday(hour, min) == true) {
            AlarmDate.setAlarmText(hour, min, alarmTextView, dayIsCheckedString(today));
            int day = getDaysToNextCheckedDay();
            Log.e("AlarmActivity", String.valueOf(day));
            myIntent.putExtra("extra", "yes");
            myIntent.putExtra("quote id", "0");
            pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime.getTimeInMillis() + (day * 24), pendingIntent);
            mNM.notify(0, mNotify);
        } else { //Alarm is ringing on nest checked day of the week, if it isn't set today.
            AlarmDate.setAlarmText(hour, min, alarmTextView, dayIsCheckedString(today + daysInFuture - i));
            int day = getDaysToNextCheckedDay();
            Log.e("AlarmActivity", String.valueOf(day));
            myIntent.putExtra("extra", "yes");
            myIntent.putExtra("quote id", "0");
            pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime.getTimeInMillis() + (day * 24), pendingIntent);
            Log.e("AlarmActivity", String.valueOf(alarmTime.getTimeInMillis() + (day * 24)));
            mNM.notify(0, mNotify);
        }



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

    public static String removeSpace(String value) {
        String ValueWithoutSign = String.valueOf(value);
        ValueWithoutSign = ValueWithoutSign.replace(" ", "");
        ValueWithoutSign = ValueWithoutSign.replace("-", "");
        ValueWithoutSign = ValueWithoutSign.replace(",", "");
        return ValueWithoutSign;
    }

    /**
     * Click listener for Start and Destination TextView
     *
     * @param
     * @Override public void onClick(View v) {
     * if (v == startET) {
     * // Uri gmmIntentUri = Uri.parse("geo:52.5149161,13.4016983?mode=d");
     * final PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
     * <p>
     * try {
     * startActivity(new Intent( AlarmActivity.this,MapActivity.class));
     * startET.setText(str_from);
     * } catch (Exception e) {
     * Log.w(TAG, "Something went wrong with the place picker!" + e);
     * }
     * } else if (v == destET) {
     * PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
     * try {
     * startActivity(new Intent( AlarmActivity.this,MapActivity.class));
     * } catch (Exception e) {
     * Log.w(TAG, "Something went wrong with the place picker!" + e);
     * }
     * }
     * }
     */
    @Override
    public void setDouble(String result) {
        String res[] = result.split(",");
        Double min = Double.parseDouble(res[0]) / 60;
        int hr = (int) (min / 60);
        int minut = (int) (min % 60);
        int dist = Integer.parseInt(res[1]) / 1000;
        Toast.makeText(context, "Momentane Fahrtzeit beträgt: " + hr + " Stunden " + minut + " Minuten", Toast.LENGTH_LONG).show();
        Toast.makeText(context, "Die Entfernung beträgt: " + dist + " kilometers", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRoute(String lat_lng) {
        String lal[] = lat_lng.split(",");
        start_lat = Double.parseDouble(lal[0]);
        start_lng = Double.parseDouble(lal[1]);
        Log.d(TAG, "Start Lat,Lng: " + start_lat + "," + start_lng);
    }

    public void initialize() {

        // prepTime = (EditText) findViewById(R.id.editText_PrepTime);
        //alarm = new AlarmReceiver();
        alarmTextView = (TextView) findViewById(R.id.infoText);
        alarmTimePicker = (TimePicker) findViewById(R.id.TimePickerAlarm);
        arrivelTimePicker = (TimePicker) findViewById(R.id.TimePickerDes);

        onOff = (Switch) findViewById(R.id.switchONOFF);
        // Find all toggle-buttons
        alarmToggleMO = (ToggleButton) findViewById(R.id.toggleButtonMO);
        alarmToggleDI = (ToggleButton) findViewById(R.id.toggleButtonDI);
        alarmToggleMI = (ToggleButton) findViewById(R.id.toggleButtonMI);
        alarmToggleDO = (ToggleButton) findViewById(R.id.toggleButtonDO);
        alarmToggleFR = (ToggleButton) findViewById(R.id.toggleButtonFR);
        alarmToggleSA = (ToggleButton) findViewById(R.id.toggleButtonSA);
        alarmToggleSO = (ToggleButton) findViewById(R.id.toggleButtonSO);

        startET = (EditText) findViewById(R.id.editText_startAddress);
        destET = (EditText) findViewById(R.id.editText_destAddress);
        btn_getCar = (Button) findViewById(R.id.btn_get_car);
        btn_getPublic = (Button) findViewById(R.id.btn_get_public);
        // tv_result1= (TextView) findViewById(R.id.textView_result1);
        // tv_result2=(TextView) findViewById(R.id.textView_result2);


    }


}
