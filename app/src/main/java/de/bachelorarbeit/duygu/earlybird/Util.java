package de.bachelorarbeit.duygu.earlybird;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
/**
 * Created by Duygu on 14.01.2017.
 */

public class Util {
        public static final class Operations {
            private Operations() throws InstantiationException {
                throw new InstantiationException("This class is not for instantiation");
            }
            /**
             * Checks to see if the device is online before carrying out any operations.
             *
             * @return
             */
            public static boolean isOnline(Context context) {
                ConnectivityManager cm =
                        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = cm.getActiveNetworkInfo();
                return netInfo != null && netInfo.isConnectedOrConnecting();
            }
        }
        private Util() throws InstantiationException {
            throw new InstantiationException("This class is not for instantiation");
        }
}

