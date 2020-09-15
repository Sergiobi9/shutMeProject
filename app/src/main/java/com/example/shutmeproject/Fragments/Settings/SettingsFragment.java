package com.example.shutmeproject.Fragments.Settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
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
    private static final String HI_MOM = "HI_MOM", CONDITIONS = "CONDITIONS", PRIVACY = "PRIVACY";

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
                openSection(HI_MOM);

            }
        });

        termsAndConditionsBtn = view.findViewById(R.id.terms_and_conditions_btn);
        termsAndConditionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSection(CONDITIONS);
            }
        });

        privacyPolicyBtn = view.findViewById(R.id.privacy_policy_btn);
        privacyPolicyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSection(PRIVACY);
            }
        });
    }

    private void openSection(String section){
        if (getFragmentManager() == null){
            Log.d(TAG, "Fragment manager is null WTF");
            return;
        }

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        switch (section){
            case HI_MOM:
                fragmentTransaction.replace(R.id.main_container, new HiMomFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case CONDITIONS:
                fragmentTransaction.replace(R.id.main_container, new TermsAndConditionsFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case PRIVACY:
                fragmentTransaction.replace(R.id.main_container, new PrivacyPolicyFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            default:
                break;
        }
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