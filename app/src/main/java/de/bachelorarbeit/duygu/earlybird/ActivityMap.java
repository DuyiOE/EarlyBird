package de.bachelorarbeit.duygu.earlybird;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Duygu on 09.01.2017.
 */

public class ActivityMap extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        final String start = intent.getExtras().getString("str_from");
        Log.e("AlarmActivity", "In the receiver with " + start);
        final String end = intent.getExtras().getString("str_to");


        Log.e("AlarmActivity", "In the receiver with " + end);
        Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?saddr=" + start + "&daddr=" + end + "&mode=d");
        final Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        mapIntent.putExtra("extra", start);
        mapIntent.putExtra("quote id", end);

        context.startService(mapIntent);


        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(5000);

                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    context.stopService(mapIntent);

                }
            }
        };
        timerThread.start();
    }
    }

    //startActivityForResult(mapIntent, );
    //onActivityResult(PLACE_PICKER_START, AlarmActivity.RESULT_OK, getIntent());

    /**
     * Will be called with a result from the PlacePicker
     *
     * @param resultCode  Gives Ok or Fail
     * @param requestCode The calling code in order to distinguish selecting start or destination
     * @param data        Intent data

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
        }
        if (requestCode == RESULT_OK) {
            choosenPl = PlaceAutocomplete.getPlace(this, data);
            String adress = String.format("Place: %s", choosenPl.getAddress());
            Log.e("The adress is: ", adress);
        } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
            Status status = PlaceAutocomplete.getStatus(this, data);
            // TODO: Handle the error.
            Log.i(TAG, status.getStatusMessage());

        } else if (resultCode == RESULT_CANCELED) {
            // The user canceled the operation.
        }
     */


