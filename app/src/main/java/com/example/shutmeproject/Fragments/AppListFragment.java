package com.example.shutmeproject.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shutmeproject.Adapters.AppAdapter;
import com.example.shutmeproject.Bytes.BytesConverterManager;
import com.example.shutmeproject.Helpers.InstaledApps;
import com.example.shutmeproject.Helpers.MyConnectivityManager;
import com.example.shutmeproject.Helpers.Permissions;
import com.example.shutmeproject.MainActivity;
import com.example.shutmeproject.Model.AppInfo;
import com.example.shutmeproject.R;
import com.example.shutmeproject.Traffic.TrafficManager;

import java.util.ArrayList;

public class AppListFragment extends Fragment implements AppAdapter.OnMyAppListener {

    private View view;
    private Context context;
    private MyConnectivityManager myConnectivityManager;
    private TrafficManager trafficManager;
    private BytesConverterManager bytesConverterManager;
    private Permissions permissions;
    private InstaledApps instaledApps;
    private ArrayList<AppInfo> appInfoArrayList;
    private ArrayList<AppInfo> copyAppInfoArrayList;

    private RecyclerView mListView;
    private AppAdapter appAdapter;
    private AppAdapter.OnMyAppListener onMyAppListener;

    private EditText seacherEditText;

    private static final String TAG = "AppListFragment";

    private ConnectivityManager.NetworkCallback networkCallback = (ConnectivityManager.NetworkCallback) new ConnectivityManager.NetworkCallback() {
        @Override
        public void onAvailable(@NonNull Network network) {
            super.onAvailable(network);
            //Toast.makeText(context, "ON AVAILABLE", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onLosing(@NonNull Network network, int maxMsToLive) {
            super.onLosing(network, maxMsToLive);
            //Toast.makeText(context, "ON LOSING", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onLost(@NonNull Network network) {
            super.onLost(network);
            //Toast.makeText(context, "ON LOST", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onUnavailable() {
            super.onUnavailable();
            //Toast.makeText(context, "ON UNAVAILABLE", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
            super.onCapabilitiesChanged(network, networkCapabilities);
            //Toast.makeText(context, "ON CAPABILITIES CHANGED", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onLinkPropertiesChanged(@NonNull Network network, @NonNull LinkProperties linkProperties) {
            super.onLinkPropertiesChanged(network, linkProperties);
            //Toast.makeText(context, "ON LINK PROPERTIES CHANGED", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onBlockedStatusChanged(@NonNull Network network, boolean blocked) {
            super.onBlockedStatusChanged(network, blocked);
            //Toast.makeText(context, "ON BLOCKED STATUS CHANGED", Toast.LENGTH_SHORT).show();
        }
    };


    public AppListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_app_list, container, false);

        context = getActivity().getApplicationContext();
        onMyAppListener = this;

        initClasses();
        setUpWifiManager();
        askForPhonePermissions();

        getInstaledApps();
        initList();
        initSearcher();

        return view;
    }

    private void initSearcher(){
        seacherEditText = view.findViewById(R.id.searcher);
        seacherEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(TAG, String.valueOf(charSequence.length()));

                refreshAppList(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void refreshAppList(String inputSearch){

    }

    private void initList(){
        mListView = view.findViewById(R.id.apps_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        mListView.setLayoutManager(mLayoutManager);

        appAdapter = new AppAdapter(appInfoArrayList, onMyAppListener, context);
        mListView.setAdapter(appAdapter);
    }

    private void askForPhonePermissions() {
        if (!permissions.checkModifyPhoneStatePermission(context)){
            permissions.askModifyPhoneStatePermission(getActivity());
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
        copyAppInfoArrayList = appInfoArrayList;
    }

    @Override
    public void onMyAppClicked(int position) {
        String name = appInfoArrayList.get(position).getName();
        String packageName = appInfoArrayList.get(position).getPackageName();

        try {
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
            context.startActivity(intent);
        } catch (Exception e){
            Toast.makeText(context, "Could not open " + name, Toast.LENGTH_SHORT).show();
        }

    }
}