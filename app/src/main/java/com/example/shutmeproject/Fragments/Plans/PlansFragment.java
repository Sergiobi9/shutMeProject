package com.example.shutmeproject.Fragments.Plans;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.shutmeproject.Plans.Plans;
import com.example.shutmeproject.R;

public class PlansFragment extends Fragment {

    private Context context;
    private SharedPreferences sharedPreferences;
    private String planSelected = "";
    private String userPlan = "";

    private static final String TAG = "PlansFragment";

    private LinearLayout freePlan, starterPlan, proPlan;
    private View view;

    public PlansFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        sharedPreferences = context.getSharedPreferences("USER", Context.MODE_PRIVATE);
        userPlan = sharedPreferences.getString("userPlan", Plans.FREE_PLAN);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_plans, container, false);

        initView();

        return view;
    }

    private void initView(){
        freePlan = view.findViewById(R.id.free_plan_box);
        freePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                planSelected = Plans.FREE_PLAN;
                Log.d(TAG, "user plan is " + userPlan + ", and plan selected " + planSelected);
                changePlan();
            }
        });
        starterPlan = view.findViewById(R.id.starter_plan_box);
        starterPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                planSelected = Plans.STARTER_PLAN;
                Log.d(TAG, "user plan is " + userPlan + ", and plan selected " + planSelected);
                changePlan();
            }
        });
        proPlan = view.findViewById(R.id.pro_plan_box);
        proPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                planSelected = Plans.PRO_PLAN;
                Log.d(TAG, "user plan is " + userPlan + ", and plan selected " + planSelected);
                changePlan();
            }
        });
    }

    private void changePlan(){
        if (planSelected.equals(userPlan)){
            Toast.makeText(context, "You already have this plan", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Plan updated from " + userPlan + " to " + planSelected, Toast.LENGTH_SHORT).show();
            userPlan = planSelected;
            sharedPreferences.edit().putString("userPlan", userPlan).apply();

        }
    }
}