package com.example.shutmeproject.Fragments.Schedule;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.shutmeproject.Fragments.Settings.HiMomFragment;
import com.example.shutmeproject.Fragments.Settings.PrivacyPolicyFragment;
import com.example.shutmeproject.Fragments.Settings.TermsAndConditionsFragment;
import com.example.shutmeproject.Fragments.VPNFragment;
import com.example.shutmeproject.MainActivity;
import com.example.shutmeproject.Plans.Plans;
import com.example.shutmeproject.R;

public class ScheduleMenuFragment extends Fragment {

    private View view;
    private Context context;
    private static final String TAG = "ScheduleMenuFragment";
    private static final String ADD_SCHEDULE = "ADD_SCHEDULE", LIST_SCHEDULE = "LIST_SCHEDULE";

    private LinearLayout addNewScheduleBtn, listScheduleBtn;


    public ScheduleMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_schedule_menu, container, false);

        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    return true;
                }
                return false;
            }
        });

        initView();

        return view;
    }

    private void initView(){
        addNewScheduleBtn = view.findViewById(R.id.add_new_schedule);
        addNewScheduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSection(ADD_SCHEDULE);
            }
        });

        listScheduleBtn = view.findViewById(R.id.list_schedule);
        listScheduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSection(LIST_SCHEDULE);
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
            case ADD_SCHEDULE:
                fragmentTransaction.replace(R.id.main_container, new AddScheduleFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case LIST_SCHEDULE:
                fragmentTransaction.replace(R.id.main_container, new ScheduleListFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}