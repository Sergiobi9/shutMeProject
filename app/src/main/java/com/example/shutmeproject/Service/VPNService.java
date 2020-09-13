package com.example.shutmeproject.Service;

import android.Manifest;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.VpnService;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;

import com.example.shutmeproject.Aplication.MyApplication;
import com.example.shutmeproject.Helpers.ToyVPNConnection;
import com.example.shutmeproject.MainActivity;
import com.example.shutmeproject.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Set;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class VPNService extends VpnService {

    private static final int notificationId = 23;
    private SharedPreferences sharedPreferences;
    private String[] appPackages;
    private static final String TAG = "VPNService";

    private Handler mHandler;
    private static class Connection extends Pair<Thread, ParcelFileDescriptor> {
        public Connection(Thread thread, ParcelFileDescriptor pfd) {
            super(thread, pfd);
        }
    }

    private final AtomicReference<Thread> mConnectingThread = new AtomicReference<>();
    private final AtomicReference<Connection> mConnection = new AtomicReference<>();
    private AtomicInteger mNextConnectionId = new AtomicInteger(1);
    private PendingIntent mConfigureIntent;


    public VPNService(){}

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startForeground(notificationId, getNotification());
        sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);

        createVPN();

           /* final Timer t = new Timer();
            t.schedule(new TimerTask() {
                @Override
                public void run() {

                }
            }, 0, 15000);*/
    }

    private String[] getPackageNames(){
        Gson gson = new Gson();
        String json = sharedPreferences.getString("appsPackageNameArrayList", null);
        Type type = new TypeToken<String[]>() {}.getType();
        return gson.fromJson(json, type);
    }

    private void createVPN(){
        //appPackages = getPackageNames();
        appPackages = new String[1];
        appPackages[0] = "es.burgerking.android";

        // Loop through the app packages in the array and confirm that the app is
        // installed before adding the app to the allowed list.
        VpnService.Builder builder = new VpnService.Builder();

        PackageManager packageManager = getPackageManager();
        for (String appPackage: appPackages) {
            try {
                packageManager.getPackageInfo(appPackage, 0);
                builder.addAllowedApplication(appPackage);
                //builder.addDisallowedApplication(appPackage);
            } catch (PackageManager.NameNotFoundException e) {
                // The app isn't installed.
            }
        }

        // Complete the VPN interface config.
        ParcelFileDescriptor localTunnel = builder
                .addAddress("2001:db8::1", 64)
                .addRoute("::", 0)
                .establish();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    private Notification getNotification() {
        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Notification notification = new NotificationCompat.Builder(getApplicationContext(), MyApplication.CHANNEL_ID)
                .setContentTitle("ShutMe")
                .setContentText("This is a service in background")
                .setSmallIcon(R.drawable.logo_icon)
                .setSound(alarmSound)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        return notification;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disconnect();
    }

    private void disconnect() {
        stopForeground(true);
    }

}
