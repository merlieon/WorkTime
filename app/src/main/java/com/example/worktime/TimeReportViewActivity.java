package com.example.worktime;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.example.worktime.models.TimeReportModel;
import com.example.worktime.viewmodels.TimeReportViewModel;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class TimeReportViewActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView companyName, hours, unpaidBrakes, hourSalary, salary, obHours, dateTV;
    Button deleteBtn;
    ArrayList<TimeReportModel> trList;
    TimeReportModel timeReportModel;
    TimeReportViewModel timeReportViewModel;
    public static final int EDIT_TR_REQUEST = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timereport_view);
        toolbar = findViewById(R.id.timeReportViewToolbar);
        companyName = findViewById(R.id.trCompanyNameViewTV);
        dateTV = findViewById(R.id.trDateViewTV);
        hours = findViewById(R.id.trHoursViewTV);
        hourSalary = findViewById(R.id.trHourSalaryViewTV);
        unpaidBrakes = findViewById(R.id.trUnpaidBrakeViewTV);
        salary = findViewById(R.id.trSalaryViewTV);
        obHours = findViewById(R.id.trObHoursViewTV);
        deleteBtn = findViewById(R.id.trDeleteBtn);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        trList = new ArrayList<>();
        timeReportViewModel = ViewModelProviders.of(this).get(TimeReportViewModel.class);

        if (getIntent().hasExtra("selected_item")){
            timeReportModel = getIntent().getParcelableExtra("selected_item");
            companyName.setText("Företags namn: " + timeReportModel.getCompanyName());
            dateTV.setText("Datum: " + timeReportModel.getWorkedDate());
            hours.setText("Timmar: " + String.valueOf(timeReportModel.getHours()));
            hourSalary.setText("Timlön: " + String.valueOf(timeReportModel.getHourSalary()));
            unpaidBrakes.setText("Obetalda Timmar: " + String.valueOf(timeReportModel.getUnpaidBrake()));
            salary.setText("Brutton lön: " + String.valueOf(timeReportModel.getSalary()));
            obHours.setText("Överbetalda timmar: " + String.valueOf(timeReportModel.getObHours()));
        }

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeReportViewModel.delete(timeReportModel);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_edit_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.editIconItem:
                Intent intent = new Intent(this, TimeReportEditActivity.class);
                intent.putExtra("selected_item", timeReportModel);
                startActivityForResult(intent, EDIT_TR_REQUEST);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_TR_REQUEST && resultCode == RESULT_OK){
            int id = data.getIntExtra(TimeReportEditActivity.EXTRA_TR_ID, -1);
            if (id == -1){
                Toast.makeText(this, "Tidsrapporten kundes inte uppdatera", Toast.LENGTH_SHORT).show();
                return;
            }
            String mCompanyName = data.getStringExtra(TimeReportEditActivity.EXTRA_C_NAME);
            String mDate = data.getStringExtra(TimeReportEditActivity.EXTRA_TR_DATE);
            double mTotSalary = data.getDoubleExtra(TimeReportEditActivity.EXTRA_TR_SALARY, 0.0);
            double mHourSalary = data.getDoubleExtra(TimeReportEditActivity.EXTRA_TR_HOUR_SALARY, 0.0);
            double mHours = data.getDoubleExtra(TimeReportEditActivity.EXTRA_TR_HOURS, 0.0);
            double mObHours = data.getDoubleExtra(TimeReportEditActivity.EXTRA_TR_OB_HOURS, 0.0);
            String mObSalary = data.getStringExtra(TimeReportEditActivity.EXTRA_TR_OB_SALARY);
            double mUnpaidBrake = data.getDoubleExtra(TimeReportEditActivity.EXTRA_TR_UNPAID_BRAKE, 0.0);
            double mObSalaryPercent = data.getDoubleExtra(TimeReportEditActivity.EXTRA_TR_OB_SALARY_PERCENT,0.0);
            Boolean mHaveWorked = data.getBooleanExtra(TimeReportEditActivity.EXTRA_TR_HAVE_WORKED, false);
            timeReportModel = new TimeReportModel(mCompanyName, mTotSalary,mHourSalary, mHours,mObHours,mObSalary,mUnpaidBrake,mHaveWorked,mDate);
            timeReportModel.setId(id);
            timeReportViewModel.update(timeReportModel);
            companyName.setText("Företags namn: " + mCompanyName);
            dateTV.setText("Datum: "+ mDate);
            hours.setText("Timmar: " + mHours);
            hourSalary.setText("Timlön: " + mHourSalary);
            unpaidBrakes.setText("Obetalda Timmar: " + mUnpaidBrake);
            salary.setText("Brutton lön: " + mTotSalary);
            obHours.setText("Överbetalda timmar: " + mObHours);
            Toast.makeText(this, "Tidsrapporten sparades", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Tidsrapporten sparades ej", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
