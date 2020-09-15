package com.example.shutmeproject.Fragments.Schedule;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.shutmeproject.Model.TimeTable;
import com.example.shutmeproject.Plans.Plans;
import com.example.shutmeproject.R;
import com.example.shutmeproject.TimePickerUtilz.SleepTimePicker;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.Duration;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Locale;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class AddScheduleFragment extends Fragment {

    private View view;
    private Context context;
    private final String MONDAY = "MONDAY", TUESDAY = "TUESDAY", WEDNESDAY = "WEDNESDAY", THURSDAY = "THURSDAY",
            FRIDAY = "FRIDAY", SATURDAY = "SATURDAY", SUNDAY = "SUNDAY", TAG = "AddScheduleFragment";
    private SleepTimePicker sleepTimePicker;
    private TextView bedTimeTV, wakeTimeTV, hoursTV, minutesTV;
    private String startTime = "9:00", endTime = "10:00";
    private int startHour = 9, startMinute = 0, endHour = 10, endMinute = 0;
    private Button scheduleNextBtn;
    private SharedPreferences sharedPreferences;

    private int identifier;

    private ArrayList<String> appPackages = new ArrayList<>();

    private LinearLayout monday, tuesday, wednesday, thursday, friday, saturday, sunday;
    private TextView mondayText, tuesdayText, wednesdayText, thursdayText, fridayText,
            saturdayText, sundayText;

    private EditText scheduleNameEditText;
    private TimeTable newTimeTable;
    private ArrayList<String> daysOfTheWeekSelected = new ArrayList<>();

    private RelativeLayout addScheduleFirstScreen, addScheduleSecondScreen;

    private String userPlan;

    public AddScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        sharedPreferences = context.getSharedPreferences("USER", Context.MODE_PRIVATE);
        userPlan = sharedPreferences.getString("userPlan", Plans.FREE_PLAN);
        AndroidThreeTen.init(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_schedule, container, false);
        appPackages.add("com.instagram.android");
        identifier = sharedPreferences.getInt("scheduleIdentifier", 1);

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    if (addScheduleSecondScreen.getVisibility() == View.VISIBLE){
                        addScheduleSecondScreen.setVisibility(View.GONE);
                        addScheduleFirstScreen.setVisibility(View.VISIBLE);
                        identifier--;
                     } else {
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new ScheduleMenuFragment());
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }

                    return true;
                }
                return false;
            }
        });

        initTimePicker();
        return view;
    }

    private void setTime(){
        switch (userPlan){
            case Plans.FREE_PLAN:
                startTime = "9:00";
                endTime = "10:00";
                startHour = 9;
                startMinute = 0;
                endHour = 10;
                endMinute = 0;
                break;
            case Plans.STARTER_PLAN:
                startTime = "9:00";
                endTime = "11:00";
                startHour = 9;
                startMinute = 0;
                endHour = 11;
                endMinute = 0;
                break;
            case Plans.PRO_PLAN:
                startTime = "9:00";
                endTime = "12:00";
                startHour = 9;
                startMinute = 0;
                endHour = 12;
                endMinute = 0;
                break;
            default:
                break;
        }
    }

    private void initTimePicker() {

        setTime();

        sleepTimePicker = (SleepTimePicker) view.findViewById(R.id.timePicker);
        sleepTimePicker.setTime(LocalTime.of(startHour,startMinute), LocalTime.of(endHour,endMinute));

        scheduleNextBtn = view.findViewById(R.id.add_schedule_accept_btn);
        scheduleNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        bedTimeTV = view.findViewById(R.id.tvBedTime);
        wakeTimeTV = view.findViewById(R.id.tvWakeTime);
        hoursTV = view.findViewById(R.id.tvHours);
        minutesTV = view.findViewById(R.id.tvMins);

        bedTimeTV.setText(sleepTimePicker.getBedTime().toString());
        wakeTimeTV.setText(sleepTimePicker.getWakeTime().toString());

        sleepTimePicker.setListener(new Function2<LocalTime, LocalTime, Unit>() {
            @Override
            public Unit invoke(LocalTime bedTime, LocalTime wakeTime) {
                handleUpdate(bedTime, wakeTime);
                return null;
            }
        });

        addScheduleFirstScreen = view.findViewById(R.id.add_schedule_first_screen);
        addScheduleSecondScreen = view.findViewById(R.id.add_schedule_second_screen);

        handleUpdate(sleepTimePicker.getBedTime(), sleepTimePicker.getWakeTime());
    }

    private void handleUpdate(LocalTime bedTime, LocalTime wakeTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a", Locale.US);

        bedTimeTV.setText(bedTime.format(formatter));
        wakeTimeTV.setText(wakeTime.format(formatter));

        LocalDateTime bedDateTime = bedTime.atDate(LocalDate.now());
        LocalDateTime wakeDateTime = wakeTime.atDate(LocalDate.now());

        startTime = bedDateTime.toString().split("T")[1];
        endTime = wakeDateTime.toString().split("T")[1];

        System.out.println(startTime);
        System.out.println(endTime);

        if (bedDateTime.getHour() >= wakeDateTime.getHour()) wakeDateTime = wakeDateTime.plusDays(1);
        Duration duration = Duration.between(bedDateTime, wakeDateTime);
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;

        if (hours == 24 && minutes != 0){
            hours = 0;
            hoursTV.setText(String.valueOf(hours));
        } else {
            hoursTV.setText(String.valueOf(hours));
        }

        minutesTV.setText(String.valueOf(minutes));
    }

    private void showDialog(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_select_days_of_week, null);
        dialog.setContentView(view);

        Button acceptBtn = (Button) view.findViewById(R.id.days_of_the_week_next_btn);
        scheduleNameEditText = view.findViewById(R.id.schedule_name);

        monday = view.findViewById(R.id.monday);
        mondayText = view.findViewById(R.id.monday_tv);

        tuesday = view.findViewById(R.id.tuesday);
        tuesdayText = view.findViewById(R.id.tuesday_tv);

        wednesday = view.findViewById(R.id.wednesday);
        wednesdayText = view.findViewById(R.id.wednesday_tv);

        thursday = view.findViewById(R.id.thursday);
        thursdayText = view.findViewById(R.id.thursday_tv);

        friday = view.findViewById(R.id.friday);
        fridayText = view.findViewById(R.id.friday_tv);

        saturday = view.findViewById(R.id.saturday);
        saturdayText = view.findViewById(R.id.saturday_tv);

        sunday = view.findViewById(R.id.sunday);
        sundayText = view.findViewById(R.id.sunday_tv);

        manageDialogClicks();

        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean canProceed = createScheduleTime();

                if (canProceed){
                    dialog.cancel();
                }
            }
        });

        dialog.show();
    }

    private boolean createScheduleTime(){
        String scheduleName = scheduleNameEditText.getText().toString();

        if (scheduleName.isEmpty()){
            Toast.makeText(context, "Please, name your time table", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (daysOfTheWeekSelected.size() == 0){
            Toast.makeText(context, "Please, choose at least one day o the week your time table", Toast.LENGTH_SHORT).show();
            return false;
        }

        newTimeTable = new TimeTable(identifier, scheduleName, startTime, endTime, daysOfTheWeekSelected, appPackages);

        identifier ++;
        sharedPreferences.edit().putInt("scheduleIdentifier", identifier).apply();

        Log.d(TAG, newTimeTable.toString());
        changeScreen();

        return true;
    }

    private void changeScreen(){
        addScheduleFirstScreen.setVisibility(View.GONE);
        addScheduleSecondScreen.setVisibility(View.VISIBLE);
    }

    private void manageDialogClicks(){
        monday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (daysOfTheWeekSelected.contains(MONDAY)){
                    daysOfTheWeekSelected.remove(MONDAY);
                    monday.setBackground(getResources().getDrawable(R.drawable.circle));
                    mondayText.setTextColor(getResources().getColor(R.color.gray));
                    mondayText.setTypeface(null, Typeface.NORMAL);
                }
                else {
                    daysOfTheWeekSelected.add(MONDAY);
                    monday.setBackground(getResources().getDrawable(R.drawable.circle_pressed));
                    mondayText.setTextColor(getResources().getColor(R.color.white));
                    mondayText.setTypeface(null, Typeface.BOLD);
                }
            }
        });

        tuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (daysOfTheWeekSelected.contains(TUESDAY)){
                    daysOfTheWeekSelected.remove(TUESDAY);
                    tuesday.setBackground(getResources().getDrawable(R.drawable.circle));
                    tuesdayText.setTextColor(getResources().getColor(R.color.gray));
                    tuesdayText.setTypeface(null, Typeface.NORMAL);
                }
                else {
                    daysOfTheWeekSelected.add(TUESDAY);
                    tuesday.setBackground(getResources().getDrawable(R.drawable.circle_pressed));
                    tuesdayText.setTextColor(getResources().getColor(R.color.white));
                    tuesdayText.setTypeface(null, Typeface.BOLD);
                }
            }
        });

        wednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (daysOfTheWeekSelected.contains(WEDNESDAY)){
                    daysOfTheWeekSelected.remove(WEDNESDAY);
                    wednesday.setBackground(getResources().getDrawable(R.drawable.circle));
                    wednesdayText.setTextColor(getResources().getColor(R.color.gray));
                    wednesdayText.setTypeface(null, Typeface.NORMAL);
                }
                else {
                    daysOfTheWeekSelected.add(WEDNESDAY);
                    wednesday.setBackground(getResources().getDrawable(R.drawable.circle_pressed));
                    wednesdayText.setTextColor(getResources().getColor(R.color.white));
                    wednesdayText.setTypeface(null, Typeface.BOLD);
                }
            }
        });

        thursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (daysOfTheWeekSelected.contains(THURSDAY)){
                    daysOfTheWeekSelected.remove(THURSDAY);
                    thursday.setBackground(getResources().getDrawable(R.drawable.circle));
                    thursdayText.setTextColor(getResources().getColor(R.color.gray));
                    thursdayText.setTypeface(null, Typeface.NORMAL);
                }
                else {
                    daysOfTheWeekSelected.add(THURSDAY);
                    thursday.setBackground(getResources().getDrawable(R.drawable.circle_pressed));
                    thursdayText.setTextColor(getResources().getColor(R.color.white));
                    thursdayText.setTypeface(null, Typeface.BOLD);
                }
            }
        });

        friday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (daysOfTheWeekSelected.contains(FRIDAY)){
                    daysOfTheWeekSelected.remove(FRIDAY);
                    friday.setBackground(getResources().getDrawable(R.drawable.circle));
                    fridayText.setTextColor(getResources().getColor(R.color.gray));
                    fridayText.setTypeface(null, Typeface.NORMAL);
                }
                else {
                    daysOfTheWeekSelected.add(FRIDAY);
                    friday.setBackground(getResources().getDrawable(R.drawable.circle_pressed));
                    fridayText.setTextColor(getResources().getColor(R.color.white));
                    fridayText.setTypeface(null, Typeface.BOLD);
                }
            }
        });

        saturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (daysOfTheWeekSelected.contains(SATURDAY)){
                    daysOfTheWeekSelected.remove(SATURDAY);
                    saturday.setBackground(getResources().getDrawable(R.drawable.circle));
                    saturdayText.setTextColor(getResources().getColor(R.color.gray));
                    saturdayText.setTypeface(null, Typeface.NORMAL);
                }
                else {
                    daysOfTheWeekSelected.add(SATURDAY);
                    saturday.setBackground(getResources().getDrawable(R.drawable.circle_pressed));
                    saturdayText.setTextColor(getResources().getColor(R.color.white));
                    saturdayText.setTypeface(null, Typeface.BOLD);
                }
            }
        });

        sunday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (daysOfTheWeekSelected.contains(SUNDAY)){
                    daysOfTheWeekSelected.remove(SUNDAY);
                    sunday.setBackground(getResources().getDrawable(R.drawable.circle));
                    sundayText.setTextColor(getResources().getColor(R.color.gray));
                    sundayText.setTypeface(null, Typeface.NORMAL);
                }
                else {
                    daysOfTheWeekSelected.add(SUNDAY);
                    sunday.setBackground(getResources().getDrawable(R.drawable.circle_pressed));
                    sundayText.setTextColor(getResources().getColor(R.color.white));
                    sundayText.setTypeface(null, Typeface.BOLD);
                }
            }
        });
    }
}