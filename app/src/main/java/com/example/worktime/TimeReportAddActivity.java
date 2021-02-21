package com.example.worktime;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.DialogCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.worktime.models.WorkplaceModel;
import com.example.worktime.viewmodels.WorkplaceViewModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TimeReportAddActivity extends AppCompatActivity {
    Toolbar toolbar;
    Spinner companyNameSP;
    TextView hourSalaryTV, totSalaryTV;
    EditText hoursET, obHoursET, unpaidBrakeET, dateET;
    Switch haveWorkedSW;
    Button cancelBtn, saveBtn;
    WorkplaceViewModel workplaceViewModel;
    Intent data;
    String mDay;
    public static final String EXTRA_C_NAME = "com.example.worktime.EXTRA_C_NAME";
    public static final String EXTRA_C_SALARY = "com.example.worktime.EXTRA_C_SALARY";
    public static final String EXTRA_TR_HOUR_SALARY = "com.example.worktime.EXTRA_TR_HOUR_SALARY";
    public static final String EXTRA_TR_HAVE_WORKED = "com.example.worktime.EXTRA_TR_HAVE_WORKED";
    public static final String EXTRA_TR_HOURS = "com.example.worktime.EXTRA_TR_HOURS";
    public static final String EXTRA_TR_OB_HOURS = "com.example.worktime.EXTRA_TR_OB_HOURS";
    public static final String EXTRA_TR_UNPAID_BRAKE = "com.example.worktime.EXTRA_TR_UNPAID_BRAKE";
    public static final String EXTRA_TR_OB_SALARY = "com.example.worktime.EXTRA_TR_OB_SALARY";
    public static final String EXTRA_TR_OB_SALARY_PERCENT = "com.example.worktime.EXTRA_TR_OB_SALARY_PERCENT";
    public static final String EXTRA_TR_DATE= "com.example.worktime.EXTRA_TR_DATE";


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timereport_add);
        workplaceViewModel = ViewModelProviders.of(this).get(WorkplaceViewModel.class);
        data = new Intent();
        totSalaryTV = findViewById(R.id.trSalaryAddTV);
        dateET = findViewById(R.id.trDateAddET);
        hourSalaryTV = findViewById(R.id.trHourSalaryAddTV);
        hoursET = findViewById(R.id.trHoursAddET);
        obHoursET = findViewById(R.id.trObHoursAddET);
        unpaidBrakeET = findViewById(R.id.trUnpaidBrakeAddET);
        haveWorkedSW = findViewById(R.id.trHaveWorkedAddSW);
        companyNameSP = findViewById(R.id.trCompanyNameAddSP);
        cancelBtn = findViewById(R.id.trCancelAddBtn);
        saveBtn = findViewById(R.id.trSaveAddBtn);
        toolbar = findViewById(R.id.timeReportAddToolbar);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime localDateTime = LocalDateTime.now();
        dateET.setText(dtf.format(localDateTime));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setSpinner();
        companyNameSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, View view, final int position, final long id) {
                workplaceViewModel.getAllWorkplaces().observe(TimeReportAddActivity.this, new Observer<List<WorkplaceModel>>() {
                    @Override
                    public void onChanged(List<WorkplaceModel> workplaceModels) {
                        for (final WorkplaceModel workplace : workplaceModels) {

                            if (parent.getItemAtPosition(position).equals(workplace.getCompanyName())) {
                                hourSalaryTV.setText(String.valueOf(workplace.getSalary()));
                            }
                        }
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTimeReport();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        hoursET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(TimeReportAddActivity.this, android.R.style.Theme_Holo_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        hoursET.setText("" + selectedHour + "." + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.updateTime(0, 0);
                mTimePicker.setTitle("Välj antal timmar och minuter");
                mTimePicker.show();
            }
        });

        unpaidBrakeET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(TimeReportAddActivity.this, android.R.style.Theme_Holo_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        unpaidBrakeET.setText(selectedHour + "." + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.updateTime(0, 0);
                mTimePicker.setTitle("Välj antal timmar och minuter");
                mTimePicker.show();
            }
        });

        obHoursET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(TimeReportAddActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        obHoursET.setText(selectedHour + "." + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.updateTime(0, 0);
                mTimePicker.setTitle("Välj antal timmar och minuter");
                mTimePicker.show();
            }
        });

        dateET.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();
                int day = mcurrentDate.get(Calendar.DAY_OF_MONTH);
                int month = mcurrentDate.get(Calendar.MONTH);
                int year = mcurrentDate.get(Calendar.YEAR);
                final DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(TimeReportAddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        if (dayOfMonth < 10)
                            mDay = "0" + dayOfMonth;
                        else
                            mDay = String.valueOf(dayOfMonth);

                            month += 1;
                        if (month < 10)
                            dateET.setText(year + "-0" + month + "-" + mDay);
                        else
                            dateET.setText(year + "-" + month + "-" + mDay);
                    }
                }, day, month, year);
                mDatePicker.updateDate(year,month,day);
                mDatePicker.show();
            }});
    }

    private void setSpinner() {
        final ArrayList<String> workPlaceList = new ArrayList<>();
        final ArrayAdapter<String> workPlaceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, workPlaceList);
        companyNameSP.setAdapter(workPlaceAdapter);
        workplaceViewModel.getAllWorkplaces().observe(this, new Observer<List<WorkplaceModel>>() {
            @Override
            public void onChanged(List<WorkplaceModel> workplaceModels) {
                for (final WorkplaceModel workplace : workplaceModels) {
                    workPlaceList.add(workplace.getCompanyName());
                    workPlaceAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void addTimeReport() {
        if (hoursET.getText().toString().trim().isEmpty())
            hoursET.setText("0");
        if (unpaidBrakeET.getText().toString().trim().isEmpty())
            unpaidBrakeET.setText("0");
        if (obHoursET.getText().toString().trim().isEmpty())
            obHoursET.setText("0");
        String companyName = companyNameSP.getSelectedItem().toString();
        double hourSalary = Double.parseDouble(hourSalaryTV.getText().toString());
        double hours = Double.parseDouble(hoursET.getText().toString());
        double unpaidBrake = Double.parseDouble(unpaidBrakeET.getText().toString());
        double obHours = Double.parseDouble(obHoursET.getText().toString());
        totSalaryTV.setText(String.valueOf(hourSalary * (hours - unpaidBrake)));
        double totSalary = Double.parseDouble(totSalaryTV.getText().toString());
        String date = dateET.getText().toString();
        Boolean haveWorked = haveWorkedSW.isChecked();
        data.putExtra(EXTRA_C_NAME, companyName);
        data.putExtra(EXTRA_C_SALARY, totSalary);
        data.putExtra(EXTRA_TR_HOUR_SALARY, hourSalary);
        data.putExtra(EXTRA_TR_HOURS, hours);
        data.putExtra(EXTRA_TR_OB_HOURS, obHours);
        data.putExtra(EXTRA_TR_UNPAID_BRAKE, unpaidBrake);
        data.putExtra(EXTRA_TR_HAVE_WORKED, haveWorked);
        data.putExtra(EXTRA_TR_DATE, date);
        setResult(RESULT_OK, data);
        finish();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
