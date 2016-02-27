package com.example.akshay.mudra.Utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by akshay on 27/2/16.
 */
public class Utility {

    public static boolean isNetConnected(Context con) {
        try {
            ConnectivityManager cm = (ConnectivityManager) con.getSystemService(
                    Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()) {
                Log.d("msg", "Network available:true");
                return true;
            } else {
                Log.d("msg", "Network available:false");
                return false;
            }
        } catch (Exception e) {
            Log.e("msg","Exception " +e);
            return false;
        }
    }
}
