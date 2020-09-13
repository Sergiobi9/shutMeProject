package com.example.shutmeproject.Helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.util.Log;

public class MyConnectivityManager {

    public ConnectivityManager connectivityManager;
    private static final String TAG = "MyConnectivityManager";

    public MyConnectivityManager(Context context) {
        this.connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public boolean isWifiEnabled(){
        boolean isWifiConnected = false;
        for (Network network : connectivityManager.getAllNetworks()) {
            NetworkInfo networkInfo = connectivityManager.getNetworkInfo(network);
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                isWifiConnected = networkInfo.isConnected();
            }

        }
        Log.d(TAG, "Wifi connected: " + isWifiConnected);
        return isWifiConnected;
    }
}
