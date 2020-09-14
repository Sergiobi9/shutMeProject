package com.example.shutmeproject.Fragments.Schedule;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.shutmeproject.R;
import com.example.shutmeproject.TimePickerUtilz.SleepTimePicker;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.Duration;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.sql.Time;
import java.util.Locale;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class AddScheduleFragment extends Fragment {

    private View view;
    private final String MONDAY = "MONDAY", TUESDAY = "TUESDAY", WEDNESDAY = "WEDNESDAY", THURSDAY = "THURSDAY",
            FRIDAY = "FRIDAY", SATURDAY = "SATURDAY", SUNDAY = "SUNDAY", TAG = "ScheduleTimePicker";
    private SleepTimePicker sleepTimePicker;
    private TextView bedTimeTV, wakeTimeTV, hoursTV, minutesTV;
    private String scheduleName = "", startTime = "8:00", endTime = "13:00";

    public AddScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidThreeTen.init(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_schedule, container, false);
        initTimePicker();
        return view;
    }

    private void initTimePicker() {
        sleepTimePicker = (SleepTimePicker) view.findViewById(R.id.timePicker);
        sleepTimePicker.setTime(LocalTime.of(8,0), LocalTime.of(13,0));

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
        hoursTV.setText(String.valueOf(hours));
        minutesTV.setText(String.valueOf(minutes));
    }
}