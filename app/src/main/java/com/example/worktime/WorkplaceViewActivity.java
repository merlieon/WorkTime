package com.example.worktime;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.example.worktime.models.WorkplaceModel;
import com.example.worktime.viewmodels.WorkplaceViewModel;

import java.util.ArrayList;

public class WorkplaceViewActivity extends AppCompatActivity {
    public static final int EDIT_WP_REQUEST = 2;
    Toolbar toolbar;
    TextView companyNameTV, companyAddressTV, companyOrgNumTV, companySalaryTV, companyObSalaryTV;
    WorkplaceModel workplaceModel;
    WorkplaceViewModel workplaceViewModel;
    Button deleteBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workplace_view);
        toolbar = findViewById(R.id.workplaceViewToolbar);
        companyNameTV = findViewById(R.id.wpCompanyNameViewTV);
        companyAddressTV = findViewById(R.id.wpCompanyAddressViewTV);
        companyOrgNumTV = findViewById(R.id.wpCompanyOrgNumViewTV);
        companySalaryTV = findViewById(R.id.wpCompanySalaryViewTV);
        companyObSalaryTV = findViewById(R.id.wpCompanyObSalaryViewTV);
        deleteBtn = findViewById(R.id.wpDeleteBtn);
        workplaceViewModel = ViewModelProviders.of(this).get(WorkplaceViewModel.class);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workplaceViewModel.delete(workplaceModel);
                finish();
            }
        });

        if (getIntent().hasExtra("selected_item")) {
            workplaceModel = getIntent().getParcelableExtra("selected_item");
            companyNameTV.setText("Företags Namn: " + workplaceModel.getCompanyName());
            companyAddressTV.setText("Företags Address: " + workplaceModel.getCompanyAddress());
            companySalaryTV.setText("Timlön: " + workplaceModel.getSalary());
            companyObSalaryTV.setText("Ob Lön: " + workplaceModel.getobHourSalary());
            companyOrgNumTV.setText("Organisations Nummer: " + workplaceModel.getCompanyOrgNum());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_edit_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.editIconItem:
                Intent intent = new Intent(this, WorkplaceEditActivity.class);
                intent.putExtra("selected_item", workplaceModel);
                startActivityForResult(intent, EDIT_WP_REQUEST);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_WP_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(WorkplaceEditActivity.EXTRA_C_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Företags Informationen kundes inte uppdateras", Toast.LENGTH_SHORT).show();
                return;
            }
            String mCompanyName = data.getStringExtra(WorkplaceEditActivity.EXTRA_C_NAME);
            String mCompanyAddress = data.getStringExtra(WorkplaceEditActivity.EXTRA_C_ADDRESS);
            String mCompanyOrgNum = data.getStringExtra(WorkplaceEditActivity.EXTRA_C_ORGNUM);
            double mCompanySalary = data.getDoubleExtra(WorkplaceEditActivity.EXTRA_C_SALARY, 0.0);
            String mCompanyObHourSalary = data.getStringExtra(WorkplaceEditActivity.EXTRA_C_OB_HOUR_SALARY);
            WorkplaceModel workplaceModel = new WorkplaceModel(mCompanyName, mCompanyAddress, mCompanyOrgNum, mCompanySalary, mCompanyObHourSalary);
            workplaceModel.setId(id);
            workplaceViewModel.update(workplaceModel);
            companyNameTV.setText(mCompanyName);
            companyAddressTV.setText(mCompanyAddress);
            companyOrgNumTV.setText(mCompanyOrgNum);
            companySalaryTV.setText(String.valueOf(mCompanySalary));
            companyObSalaryTV.setText(mCompanyObHourSalary);
            Toast.makeText(this, "Företags Informationen sparades", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Företags Informationen sparades inte", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
