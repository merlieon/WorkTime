package com.example.worktime;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.worktime.models.TimeReportModel;
import com.example.worktime.models.WorkplaceModel;
import com.example.worktime.viewmodels.WorkplaceViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TimeReportEditActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText hoursET, obHoursET, unpaidBrakeET, dateET;
    TextView hourSalaryTV, salaryTV;
    Spinner companyNameSP;
    Switch haveWorkedSW;
    Button saveBtn, cancelBtn;
    WorkplaceViewModel workplaceViewModel;
    ArrayAdapter<String> workPlaceAdapter;
    String mDay;
    public static final String EXTRA_C_NAME = "com.example.worktime.EXTRA_C_NAME";
    public static final String EXTRA_TR_ID = "com.example.worktime.EXTRA_TR_ID";
    public static final String EXTRA_TR_SALARY = "com.example.worktime.EXTRA_TR_SALARY";
    public static final String EXTRA_TR_HOUR_SALARY = "com.example.worktime.EXTRA_TR_HOUR_SALARY";
    public static final String EXTRA_TR_HAVE_WORKED = "com.example.worktime.EXTRA_TR_HAVE_WORKED";
    public static final String EXTRA_TR_HOURS = "com.example.worktime.EXTRA_TR_HOURS";
    public static final String EXTRA_TR_OB_HOURS = "com.example.worktime.EXTRA_TR_OB_HOURS";
    public static final String EXTRA_TR_UNPAID_BRAKE = "com.example.worktime.EXTRA_TR_UNPAID_BRAKE";
    public static final String EXTRA_TR_OB_SALARY = "com.example.worktime.EXTRA_TR_OB_SALARY";
    public static final String EXTRA_TR_OB_SALARY_PERCENT = "com.example.worktime.EXTRA_TR_OB_SALARY_PERCENT";
    public static final String EXTRA_TR_DATE = "com.example.worktime.EXTRA_TR_DATE";
    TimeReportModel timeReportModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timereport_edit);
        toolbar = findViewById(R.id.timeReportEditToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        workplaceViewModel = ViewModelProviders.of(this).get(WorkplaceViewModel.class);
        companyNameSP = findViewById(R.id.trCompanyNameEditSP);
        dateET = findViewById(R.id.trDateEditET);
        hoursET = findViewById(R.id.trHoursEditET);
        hourSalaryTV = findViewById(R.id.trHourSalaryEditTV);
        unpaidBrakeET = findViewById(R.id.trUnpaidBrakeEditET);
        salaryTV = findViewById(R.id.trSalaryEditTV);
        obHoursET = findViewById(R.id.trObHoursEditET);
        haveWorkedSW = findViewById(R.id.trHaveWorkedEditSW);
        saveBtn = findViewById(R.id.trSaveEditBtn);
        cancelBtn = findViewById(R.id.trCancelEditBtn);

        if (getIntent().hasExtra("selected_item")) {
            timeReportModel = getIntent().getParcelableExtra("selected_item");
            setSpinner(timeReportModel.getCompanyName());
            hoursET.setText(String.valueOf(timeReportModel.getHours()));
            hourSalaryTV.setText(String.valueOf(timeReportModel.getHourSalary()));
            unpaidBrakeET.setText(String.valueOf(timeReportModel.getUnpaidBrake()));
            salaryTV.setText(String.valueOf(timeReportModel.getSalary()));
            obHoursET.setText(String.valueOf(timeReportModel.getObHours()));
            dateET.setText(timeReportModel.getWorkedDate());
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTimeReport();
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
                mTimePicker = new TimePickerDialog(TimeReportEditActivity.this, android.R.style.Theme_Holo_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
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
                mTimePicker = new TimePickerDialog(TimeReportEditActivity.this, android.R.style.Theme_Holo_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
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
                mTimePicker = new TimePickerDialog(TimeReportEditActivity.this, android.R.style.Theme_Holo_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
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
                final int day = mcurrentDate.get(Calendar.DAY_OF_MONTH);
                int month = mcurrentDate.get(Calendar.MONTH);
                int year = mcurrentDate.get(Calendar.YEAR);
                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(TimeReportEditActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        if (dayOfMonth < 10)
                            mDay = "0" + dayOfMonth;
                        else
                            mDay = String.valueOf(dayOfMonth);

                        month += 1;
                        if (month < 10)
                            dateET.setText(year + "-0" + month + "-" + dayOfMonth);
                        else
                            dateET.setText(year + "-" + month + "-" + dayOfMonth);
                    }
                }, day, month, year);
                mDatePicker.updateDate(year,month,day);
                mDatePicker.show();
            }});
    }

    private void setSpinner(final String cName) {
        final ArrayList<String> workPlaceList = new ArrayList<>();
        workPlaceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, workPlaceList);
        companyNameSP.setAdapter(workPlaceAdapter);
        workplaceViewModel.getAllWorkplaces().observe(this, new Observer<List<WorkplaceModel>>() {
            @Override
            public void onChanged(List<WorkplaceModel> workplaceModels) {
                for (final WorkplaceModel workplace : workplaceModels) {
                    workPlaceList.add(workplace.getCompanyName());
                    int spinnerValue = workPlaceAdapter.getPosition(cName);
                    companyNameSP.setSelection(spinnerValue);
                    workPlaceAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void saveTimeReport() {
        if (hoursET.getText().toString().trim().isEmpty())
            hoursET.setText("0");
        if (unpaidBrakeET.getText().toString().trim().isEmpty())
            unpaidBrakeET.setText("0");
        if (obHoursET.getText().toString().trim().isEmpty())
            obHoursET.setText("0");
        String companyName = companyNameSP.getSelectedItem().toString();
        String date = dateET.getText().toString();
        double dHourSalary = Double.parseDouble(hourSalaryTV.getText().toString());
        double dHours = Double.parseDouble(hoursET.getText().toString());
        double dUnpaidBrake = Double.parseDouble(unpaidBrakeET.getText().toString());
        double obHours = Double.parseDouble(obHoursET.getText().toString());
        salaryTV.setText(String.valueOf(dHourSalary * (dHours - dUnpaidBrake)));
        double dSalary = Double.parseDouble(salaryTV.getText().toString());
        Boolean haveWorked = haveWorkedSW.isChecked();

        Intent data = new Intent();
        data.putExtra(EXTRA_C_NAME, companyName);
        data.putExtra(EXTRA_TR_ID, timeReportModel.getId());
        data.putExtra(EXTRA_TR_SALARY, dSalary);
        data.putExtra(EXTRA_TR_HOUR_SALARY, dHourSalary);
        data.putExtra(EXTRA_TR_HOURS, dHours);
        data.putExtra(EXTRA_TR_OB_HOURS, obHours);
        data.putExtra(EXTRA_TR_UNPAID_BRAKE, dUnpaidBrake);
        data.putExtra(EXTRA_TR_HAVE_WORKED, haveWorked);
        data.putExtra(EXTRA_TR_DATE, date);
        int id = getIntent().getIntExtra(EXTRA_TR_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_TR_ID, id);
        }
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
