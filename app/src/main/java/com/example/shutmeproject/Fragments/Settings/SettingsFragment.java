package com.example.shutmeproject.Fragments.Settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.VerifiedInputEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.shutmeproject.Fragments.Schedule.AddScheduleFragment;
import com.example.shutmeproject.R;

public class SettingsFragment extends Fragment {

    private View view;
    private static final String TAG = "SettingsFragment";

    private Button hiMomBtn, termsAndConditionsBtn ,privacyPolicyBtn;

    public SettingsFragment() {
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
        view = inflater.inflate(R.layout.fragment_settings, container, false);

        initView();

        return view;
    }

    private void initView(){
        hiMomBtn = view.findViewById(R.id.hi_mom_btn);
        hiMomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container, new HiMomFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        termsAndConditionsBtn = view.findViewById(R.id.terms_and_conditions_btn);
        termsAndConditionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container, new TermsAndConditionsFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        privacyPolicyBtn = view.findViewById(R.id.privacy_policy_btn);
        privacyPolicyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container, new PrivacyPolicyFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}