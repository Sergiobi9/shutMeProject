package com.example.shutmeproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.shutmeproject.Adapters.AppAdapter;
import com.example.shutmeproject.Bytes.BytesConverterManager;
import com.example.shutmeproject.Helpers.ByteUnitsEnum;
import com.example.shutmeproject.Helpers.InstaledApps;
import com.example.shutmeproject.Helpers.MyConnectivityManager;
import com.example.shutmeproject.Helpers.Permissions;
import com.example.shutmeproject.Model.AppInfo;
import com.example.shutmeproject.Traffic.TrafficManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AppAdapter.OnMyAppListener {

    private Context context;
    private MyConnectivityManager myConnectivityManager;
    private TrafficManager trafficManager;
    private BytesConverterManager bytesConverterManager;
    private Permissions permissions;
    private InstaledApps instaledApps;
    private ArrayList<AppInfo> appInfoArrayList;

    private RecyclerView mListView;
    private AppAdapter appAdapter;
    private AppAdapter.OnMyAppListener onMyAppListener;

    private static final String TAG = "MainActivityLOG";

    private ConnectivityManager.NetworkCallback networkCallback = (ConnectivityManager.NetworkCallback) new ConnectivityManager.NetworkCallback() {
        @Override
        public void onAvailable(@NonNull Network network) {
            super.onAvailable(network);
            Toast.makeText(context, "ON AVAILABLE", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onLosing(@NonNull Network network, int maxMsToLive) {
            super.onLosing(network, maxMsToLive);
            Toast.makeText(context, "ON LOSING", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onLost(@NonNull Network network) {
            super.onLost(network);
            Toast.makeText(context, "ON LOST", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onUnavailable() {
            super.onUnavailable();
            Toast.makeText(context, "ON UNAVAILABLE", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
            super.onCapabilitiesChanged(network, networkCapabilities);
            Toast.makeText(context, "ON CAPABILITIES CHANGED", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onLinkPropertiesChanged(@NonNull Network network, @NonNull LinkProperties linkProperties) {
            super.onLinkPropertiesChanged(network, linkProperties);
            Toast.makeText(context, "ON LINK PROPERTIES CHANGED", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onBlockedStatusChanged(@NonNull Network network, boolean blocked) {
            super.onBlockedStatusChanged(network, blocked);
            Toast.makeText(context, "ON BLOCKED STATUS CHANGED", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        context = getApplicationContext();
        onMyAppListener = this;

        initClasses();
        setUpWifiManager();
        askForPhonePermissions();

        getInstaledApps();
        initList();
    }

    private void initList(){
        mListView = findViewById(R.id.apps_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        mListView.setLayoutManager(mLayoutManager);

        appAdapter = new AppAdapter(appInfoArrayList, onMyAppListener, context);
        mListView.setAdapter(appAdapter);
    }

    private void askForPhonePermissions() {
        if (!permissions.checkModifyPhoneStatePermission(context)){
            permissions.askModifyPhoneStatePermission(MainActivity.this);
        }
    }

    private void initClasses(){
        myConnectivityManager = new MyConnectivityManager(context);
        trafficManager = new TrafficManager();
        bytesConverterManager = new BytesConverterManager();
        permissions = new Permissions();
        instaledApps = new InstaledApps();
    }

    private void setUpWifiManager(){

        NetworkRequest networkRequest = new NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .build();

        myConnectivityManager.connectivityManager.registerNetworkCallback(networkRequest, networkCallback);
    }

    /*private void getTotalDataToday(){
        double totalDataToday = trafficManager.getTotalRxGigaBytes();
    }*/

    private void getInstaledApps(){
        appInfoArrayList = instaledApps.getInstaledApps(context);
    }

    @Override
    public void onMyAppClicked(int position) {

    }
}