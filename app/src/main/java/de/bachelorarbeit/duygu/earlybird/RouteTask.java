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
 * Created by Duygu on 11.01.2017.
 */
/*The instance of this class is called by "AlarmActivty",to get the time taken reach the destination from Google Direction Matrix API in background.
  This class contains interface "Route" to call the function setDouble(String) defined in "AlarmActivity.class" to display the result.*/
public class RouteTask extends AsyncTask<String, Void, String> {


    ProgressDialog pd;
    Context rContext;
    Double lat_lng;
    RouteS route1;

    //constructor is used to get the context.
    public RouteTask(Context rContext) {
        this.rContext = rContext;
        route1 = (RouteS) rContext;
    }

    //This function is executed before before "doInBackground(String...params)" is executed to dispaly the progress dialog
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(rContext);
        pd.setMessage("Loading");
        pd.setCancelable(false);
        pd.show();
        String route1;
    }

    //This function is executed after the execution of "doInBackground(String...params)" to dismiss the dispalyed progress dialog and call "setDouble(Double)" defined in "MainActivity.java"
    @Override
    protected void onPostExecute(String lat_lang) {
        super.onPostExecute(lat_lang);
        if (lat_lang != null) {
            route1.showRoute(lat_lang);
            pd.dismiss();
        } else
            Toast.makeText(rContext, "Versuches es nochmal! Prüfe deine Eingabe auf Fehler.", Toast.LENGTH_SHORT).show();
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
                JSONArray array_routes = root.getJSONArray("routes");
                Log.d("JSON", "array_routes:" + array_routes.getJSONObject(0));
                JSONObject object_routes = array_routes.getJSONObject(0);
                Log.d("JSON", "object_routes:" + object_routes);
                JSONArray array_legs = object_routes.getJSONArray("legs");
                Log.d("JSON", "array_legs:" + array_legs);
                JSONObject object_legs = array_legs.getJSONObject(0);
                Log.d("JSON", "object_legs:" + object_legs);

                JSONObject object_end = object_legs.getJSONObject("end_location");
                Log.d("JSON", "end_location:" + object_end);
                JSONObject object_start = object_legs.getJSONObject("start_location");
                Log.d("JSON", "start_location:" + object_start);

                return object_end.getString("value") + "," + object_start.getString("value");
                //Fehler bei der Ausgabe, kommt nicht an die Zieadresse ran TODO
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


    interface RouteS {
        void showRoute(String min);
    }

}