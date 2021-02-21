package com.example.worktime.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.worktime.R;
import com.example.worktime.TimeReportViewActivity;
import com.example.worktime.adapters.CalendarListAdapter;
import com.example.worktime.models.TimeReportModel;
import com.example.worktime.viewmodels.TimeReportViewModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.content.ContentValues.TAG;

public class CalendarFragment extends Fragment implements CalendarListAdapter.OnCalendarListner {

    CalendarView calendarView;
    RecyclerView calendarRV;
    ArrayList<TimeReportModel> mTimeReportList = new ArrayList<>();
    CalendarListAdapter adapter;
    TimeReportViewModel timeReportViewModel;
    String selectedDate, mDay;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        setHasOptionsMenu(true);
        calendarView = view.findViewById(R.id.calendarView);
        calendarRV = view.findViewById(R.id.calendarRV);
        calendarRV.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new CalendarListAdapter(mTimeReportList, this);
        calendarRV.setAdapter(adapter);
        LocalDate date = LocalDate.now();
        System.out.println(date);
        timeReportViewModel = ViewModelProviders.of(this).get(TimeReportViewModel.class);
        timeReportViewModel.getAllTimeReportsByWorkedDate(date.toString()).observe(getActivity(), new Observer<List<TimeReportModel>>() {
            @Override
            public void onChanged(List<TimeReportModel> timeReportList) {
                adapter.setCalendartList(timeReportList);
                mTimeReportList = (ArrayList<TimeReportModel>) timeReportList;
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                if (dayOfMonth < 10)
                    mDay = "0" + dayOfMonth;
                else
                    mDay = String.valueOf(dayOfMonth);

                month += 1;
                if (month < 10)
                    selectedDate = year + "-0" + month + "-" + mDay;
                else
                    selectedDate = year + "-" + month + "-" + mDay;

                timeReportViewModel.getAllTimeReportsByWorkedDate(selectedDate).observe(getActivity(), new Observer<List<TimeReportModel>>() {
                    @Override
                    public void onChanged(List<TimeReportModel> timeReportList) {
                        adapter.setCalendartList(timeReportList);
                        mTimeReportList = (ArrayList<TimeReportModel>) timeReportList;
                    }
                });
            }
        });

        return view;
    }

    @Override
    public void OnCalendarClick(int position) {
        Intent intent = new Intent(getActivity(), TimeReportViewActivity.class);
        intent.putExtra("selected_item", mTimeReportList.get(position));
        startActivity(intent);
    }
}
