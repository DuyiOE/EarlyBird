package de.bachelorarbeit.duygu.earlybird;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.CompoundButton;


/**
 * Created by Duygu on 26.11.2016.
 */


public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String state = intent.getExtras().getString("extra");
        Log.e("AlarmActivity", "In the receiver with " + state);

        String q_id = intent.getExtras().getString("quote id");
        Log.e("The quote is", q_id);

        Intent serviceIntent = new Intent(context, AlarmService.class);
        serviceIntent.putExtra("extra", state);
        serviceIntent.putExtra("quote id", q_id);

        context.startService(serviceIntent);
    }


}