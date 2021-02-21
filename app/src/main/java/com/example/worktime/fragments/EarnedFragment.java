package com.example.worktime.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.worktime.MainActivity;
import com.example.worktime.R;

import java.text.DateFormatSymbols;
import java.util.Calendar;

public class EarnedFragment extends Fragment {

    Button YMWBtn;
    TextView workedHoursTV, salaryBeforeTV, taxTV, salaryAfterTV;
    private int mYear, mMonth, mDay;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_earned, container, false);
        YMWBtn = view.findViewById(R.id.earnedYMWBtn);
        workedHoursTV = view.findViewById(R.id.earnedTotWorkedHoursTV);
        salaryBeforeTV = view.findViewById(R.id.earnedSalaryBeforeTV);
        salaryAfterTV = view.findViewById(R.id.earnedSalaryAfterTV);
        taxTV = view.findViewById(R.id.earnedTaxTV);
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH) + 1;
        YMWBtn.setText( mYear + " " + getMonth(mMonth));
        setYearMonthWeekDialog();
        return view;
    }


    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month-1];
    }



    private void setYearMonthWeekDialog(){
        YMWBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_DARK,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                mYear = year;
                                mMonth = monthOfYear + 1;
                                YMWBtn.setText( mYear + " " + getMonth(mMonth));
                            }
                        }, mYear, mMonth, mDay);
                ((ViewGroup) datePickerDialog.getDatePicker()).findViewById(Resources.getSystem().getIdentifier("day", "id", "android")).setVisibility(View.GONE);
                datePickerDialog.show();
            }
        });
    }
}
