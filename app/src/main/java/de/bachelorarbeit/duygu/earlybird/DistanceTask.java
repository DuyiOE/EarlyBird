package de.bachelorarbeit.duygu.earlybird;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Duygu on 10.01.2017.
 */

/*The instance of this class is called by "Alarmctivty",to get the time taken reach the destination from Google Distance Matrix API in background.
  This class contains interface "Geo" to call the function setDouble(String) defined in "AlarmActivity.class" to display the result.*/
public class DistanceTask extends AsyncTask<String, Void, String> {
    ProgressDialog pd;
    Context mContext;
    Distance distance1;

    //constructor is used to get the context.
    public DistanceTask(Context mContext) {
        this.mContext = mContext;
        distance1 = (Distance) mContext;

    }

    //This function is executed before before "doInBackground(String...params)" is executed to dispaly the progress dialog
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(mContext);
        pd.setMessage("Loading");
        pd.setCancelable(false);
        pd.show();

    }

    //This function is executed after the execution of "doInBackground(String...params)"
    // to dismiss the dispalyed progress dialog and call "setDouble(Double)" defined in "AlarmActivity.java"
    @Override
    protected void onPostExecute(String aDouble) {
        super.onPostExecute(aDouble);
        if (aDouble != null) {
            distance1.setDouble(aDouble);
            pd.dismiss();
        } else
            Toast.makeText(mContext, "Versuches es nochmal. Prüfe deine Eingabe auf Fehler.", Toast.LENGTH_SHORT).show();
        pd.cancel();
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statuscode = con.getResponseCode();
            if (statuscode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();
                while (line != null) {
                    sb.append(line);
                    line = br.readLine();
                }
                String json = sb.toString();
                Log.d("JSON", json);
                JSONObject root = new JSONObject(json);
                JSONArray array_rows = root.getJSONArray("rows");
                Log.d("JSON", "array_rows:" + array_rows);
                JSONObject object_rows = array_rows.getJSONObject(0);
                Log.d("JSON", "object_rows:" + object_rows);
                JSONArray array_elements = object_rows.getJSONArray("elements");
                Log.d("JSON", "array_elements:" + array_elements);
                JSONObject object_elements = array_elements.getJSONObject(0);
                Log.d("JSON", "object_elements:" + object_elements);

                JSONObject object_duration = object_elements.getJSONObject("duration");
                JSONObject object_distance = object_elements.getJSONObject("distance");

                Log.v("JSON", "object_duration:" + object_duration);
                Log.v("JSON", "object_distance:" + object_distance);

                return object_duration.getString("value") + "," + object_distance.getString("value");

            }
        } catch (MalformedURLException e) {
            Log.d("error", "error1");
        } catch (IOException e) {
            Log.d("error", "error2");
        } catch (JSONException e) {
            Log.d("error", "error3");
        }


        return null;
    }


    interface Distance {
        void setDouble(String min);

    }

}