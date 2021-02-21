package com.example.worktime;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.worktime.adapters.OvertimeListAdapter;
import com.example.worktime.models.WorkplaceModel;
import com.example.worktime.viewmodels.WorkplaceViewModel;

import java.util.ArrayList;
public class WorkplaceAddActivity extends AppCompatActivity implements OvertimeListAdapter.OnOvertimeListner {
    public static final String EXTRA_C_NAME =
            "com.example.worktime.EXTRA_C_NAME";
    public static final String EXTRA_C_ADDRESS =
            "com.example.worktime.EXTRA_C_ADDRESS";
    public static final String EXTRA_C_ORGNUM =
            "com.example.worktime.EXTRA_C_ORGNUM";
    public static final String EXTRA_C_SALARY =
            "com.example.worktime.EXTRA_C_SALARY";
    public static final String EXTRA_C_OB_HOUR_SALARY =
            "com.example.worktime.EXTRA_C_OB_SALARY";

    Toolbar toolbar;
    EditText companyNameET, companyAddressET, companyOrgNumET, salaryET;
    Button saveBtn, cancelBtn;
    RecyclerView overtimeHourSalaryRV;
    Intent data;
    OvertimeListAdapter adapter;
    ArrayList<String> mOvertimeList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workplace_add);
        data = new Intent();
        cancelBtn = findViewById(R.id.cancelWorkplaceAddBtn);
        saveBtn = findViewById(R.id.saveWorkplaceAddBtn);
        companyNameET = findViewById(R.id.wpCompanyNameAddET);
        companyOrgNumET = findViewById(R.id.wpCompanyOrgNumAddET);
        companyAddressET = findViewById(R.id.wpCompanyAddressAddET);
        salaryET = findViewById(R.id.wpCompanySalaryAddET);
        overtimeHourSalaryRV = findViewById(R.id.wpOvertimeHourSalaryAddRV);
        toolbar = findViewById(R.id.workplaceAddToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCompany();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setmOvertimeList();
    }

    public void addCompany() {
        String companyName = companyNameET.getText().toString();
        String companyOrgNum = companyOrgNumET.getText().toString();
        String companyAddress = companyAddressET.getText().toString();
        String salary = salaryET.getText().toString();
        String obHourSalary = "0";
        if (companyName.trim().isEmpty() || companyOrgNum.isEmpty() || companyAddress.trim().isEmpty() || salary.trim().isEmpty()) {
            Toast.makeText(this, "Du måste skriva i alla fält", Toast.LENGTH_SHORT).show();
        } else {
            double dSalary = Double.parseDouble(salary);
            data.putExtra(EXTRA_C_NAME, companyName);
            data.putExtra(EXTRA_C_ADDRESS, companyAddress);
            data.putExtra(EXTRA_C_ORGNUM, companyOrgNum);
            data.putExtra(EXTRA_C_SALARY, dSalary);
            data.putExtra(EXTRA_C_OB_HOUR_SALARY, obHourSalary);
            setResult(RESULT_OK, data);
            finish();
        }
    }

    private void setmOvertimeList(){
        mOvertimeList.add("test");
        mOvertimeList.add("test");
        overtimeHourSalaryRV.setLayoutManager(new LinearLayoutManager(this));
        adapter = new OvertimeListAdapter(mOvertimeList,this);
        adapter.setOvertimeList(mOvertimeList);
        overtimeHourSalaryRV.setAdapter(adapter);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onOvertimeClick(int position) {
        System.out.println("click");
    }
}
