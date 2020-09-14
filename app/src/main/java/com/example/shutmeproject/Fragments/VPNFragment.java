package com.example.shutmeproject.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.VpnService;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.shutmeproject.R;
import com.example.shutmeproject.Service.VPNService;

import static android.app.Activity.RESULT_OK;

public class VPNFragment extends Fragment {

    private View view;
    private Context context;
    private Button startVPNServiceBtn, stopVPNServiceBtn;

    public VPNFragment() {
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
        view = inflater.inflate(R.layout.fragment_vpn, container, false);

        initView();
        return view;
    }

    private void initView(){
        context = getContext();

        startVPNServiceBtn = view.findViewById(R.id.startVPNService);
        stopVPNServiceBtn = view.findViewById(R.id.stopVPNService);
        stopVPNServiceBtn.setVisibility(View.GONE);

        startVPNServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startVPNService();
                stopVPNServiceBtn.setVisibility(View.VISIBLE);
            }
        });

        stopVPNServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopVPNService();
                stopVPNServiceBtn.setVisibility(View.GONE);
            }
        });
    }

    private void startVPNService(){
        Intent intent = VpnService.prepare(context);
        if (intent != null) {
            startActivityForResult(intent, 0);
        } else {
            onActivityResult(0, RESULT_OK, null);
        }
    }

    @Override
    public void onActivityResult(int request, int result, Intent data) {
        if (result == RESULT_OK) {
            Intent intent = new Intent(context, VPNService.class);
            intent.setAction(VPNService.ACTION_CONNECT);
            ContextCompat.startForegroundService(context, intent);
        }
    }

    private void stopVPNService(){
        Intent intent = new Intent(context, VPNService.class);
        intent.setAction(VPNService.ACTION_DISCONNECT);
        ContextCompat.startForegroundService(context, intent);

    }
}