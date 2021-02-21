package com.example.worktime;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.worktime.adapters.OvertimeListAdapter;
import com.example.worktime.models.WorkplaceModel;
import com.example.worktime.viewmodels.WorkplaceViewModel;

import java.util.ArrayList;
import java.util.List;

public class WorkplaceEditActivity extends AppCompatActivity implements OvertimeListAdapter.OnOvertimeListner {

    Toolbar toolbar;
    EditText companyNameET, companyAddressET, companyOrgNumET, companySalaryET;
    RecyclerView companyObHourSalaryRV;
    WorkplaceModel workplaceModel;
    WorkplaceViewModel workplaceViewModel;
    OvertimeListAdapter adapter;
    Button saveBtn, cancelBtn;

    public static final String EXTRA_C_ID =
            "com.example.worktime.EXTRA_C_ID";
    public static final String EXTRA_C_NAME =
            "com.example.worktime.EXTRA_C_NAME";
    public static final String EXTRA_C_ADDRESS =
            "com.example.worktime.EXTRA_C_ADDRESS";
    public static final String EXTRA_C_ORGNUM =
            "com.example.worktime.EXTRA_C_ORGNUM";
    public static final String EXTRA_C_SALARY =
            "com.example.worktime.EXTRA_C_SALARY";
    public static final String EXTRA_C_OB_HOUR_SALARY =
            "com.example.worktime.EXTRA_C_OB_HOUR_SALARY";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workplace_edit);

        companyNameET = findViewById(R.id.wpCompanyNameEditET);
        companyAddressET = findViewById(R.id.wpCompanyAddressEditET);
        companyOrgNumET = findViewById(R.id.wpCompanyOrgNumEditET);
        companySalaryET = findViewById(R.id.wpCompanySalaryEditET);
        companyObHourSalaryRV = findViewById(R.id.wpOvertimeHourSalaryEditRV);
        saveBtn = findViewById(R.id.saveWorkplaceEditBtn);
        cancelBtn = findViewById(R.id.cancelWorkplaceEditBtn);
        toolbar = findViewById(R.id.workplaceEditToolbar);
        workplaceViewModel = ViewModelProviders.of(this).get(WorkplaceViewModel.class);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (getIntent().hasExtra("selected_item")) {
            workplaceModel = getIntent().getParcelableExtra("selected_item");
            companyNameET.setText(workplaceModel.getCompanyName());
            companyAddressET.setText(workplaceModel.getCompanyAddress());
            companySalaryET.setText(String.valueOf(workplaceModel.getSalary()));
            companyOrgNumET.setText(workplaceModel.getCompanyOrgNum());

            // set IF statement for percent

        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCompany();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setmOvertimeList(){
      /*  workplaceViewModel.getAllWorkplaces().observe(this, new Observer<List<WorkplaceModel>>() {
            @Override
            public void onChanged(List<WorkplaceModel> workplaceList) {
                workplaceList.add(new WorkplaceModel("","test"));
                companyObHourSalaryRV.setLayoutManager(new LinearLayoutManager(WorkplaceEditActivity.this));
                adapter = new OvertimeListAdapter((ArrayList<WorkplaceModel>) workplaceList,WorkplaceEditActivity.this);
                adapter.setOvertimeList(workplaceList);
                companyObHourSalaryRV.setAdapter(adapter);
            }
        });*/


    }

    public void saveCompany() {
        String mCompanyName = companyNameET.getText().toString();
        String mCompanyAddress = companyAddressET.getText().toString();
        String mCompanyOrgNum = companyOrgNumET.getText().toString();
        double mCompanySalary = Double.parseDouble(companySalaryET.getText().toString());
        for (int i = 0; i < adapter.getItemCount(); i++) {
            String mCompanyObHourSalary = "";
        }


        Intent data = new Intent();
        int id = getIntent().getIntExtra(EXTRA_C_ID, -1);
        data.putExtra(EXTRA_C_NAME, mCompanyName);
        data.putExtra(EXTRA_C_ID, workplaceModel.getId());
        data.putExtra(EXTRA_C_ADDRESS, mCompanyAddress);
        data.putExtra(EXTRA_C_ORGNUM, mCompanyOrgNum);
        data.putExtra(EXTRA_C_SALARY, mCompanySalary);
        data.putExtra(EXTRA_C_OB_HOUR_SALARY, "mCompanyObHourSalary");

        if (id != -1) {
            data.putExtra(EXTRA_C_ID, id);
        }
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onOvertimeClick(int position) {

    }
}
