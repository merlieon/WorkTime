package com.example.worktime.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.worktime.R;
import com.example.worktime.TimeReportAddActivity;
import com.example.worktime.TimeReportViewActivity;
import com.example.worktime.WorkplaceAddActivity;
import com.example.worktime.adapters.TimeReportListAdapter;
import com.example.worktime.models.TimeReportModel;
import com.example.worktime.models.WorkplaceModel;
import com.example.worktime.viewmodels.TimeReportViewModel;
import com.example.worktime.viewmodels.WorkplaceViewModel;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment implements TimeReportListAdapter.OnTimeReportListner {
    public static final int ADD_TR_REQUEST = 1;
    Spinner homeSpinner;
    private TimeReportViewModel timeReportViewModel;
    WorkplaceViewModel workplaceViewModel;
    ArrayList<TimeReportModel> mTimeReportList = new ArrayList<>();
    TimeReportListAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);
        homeSpinner = view.findViewById(R.id.homeSP);

        RecyclerView recyclerView = view.findViewById(R.id.timeReportRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new TimeReportListAdapter(mTimeReportList,this);
        recyclerView.setAdapter(adapter);

        timeReportViewModel = ViewModelProviders.of(this).get(TimeReportViewModel.class);
        workplaceViewModel = ViewModelProviders.of(this).get(WorkplaceViewModel.class);

        setSpinner();
        CheckCompanySpinner();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, menu);
    }

    //appbar icon onclick
    //@Override
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addIconItem:
                Intent intent = new Intent(getActivity(), TimeReportAddActivity.class);
                startActivityForResult(intent, ADD_TR_REQUEST);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_TR_REQUEST && resultCode == getActivity().RESULT_OK){
            String companyName = data.getStringExtra(TimeReportAddActivity.EXTRA_C_NAME);
            String date = data.getStringExtra(TimeReportAddActivity.EXTRA_TR_DATE);
            double totSalary = data.getDoubleExtra(TimeReportAddActivity.EXTRA_C_SALARY, 0.0);
            double hourSalary = data.getDoubleExtra(TimeReportAddActivity.EXTRA_TR_HOUR_SALARY, 0.0);
            double hours = data.getDoubleExtra(TimeReportAddActivity.EXTRA_TR_HOURS, 0.0);
            double obHours = data.getDoubleExtra(TimeReportAddActivity.EXTRA_TR_OB_HOURS, 0.0);
            String obSalary = data.getStringExtra(TimeReportAddActivity.EXTRA_TR_OB_SALARY);
            double unpaidBrake = data.getDoubleExtra(TimeReportAddActivity.EXTRA_TR_UNPAID_BRAKE, 0.0);
            double obSalaryPercent = data.getDoubleExtra(TimeReportAddActivity.EXTRA_TR_OB_SALARY_PERCENT,0.0);
            Boolean haveWorked = data.getBooleanExtra(TimeReportAddActivity.EXTRA_TR_HAVE_WORKED, false);
            TimeReportModel timeReportModel = new TimeReportModel(companyName, totSalary,hourSalary, hours,obHours,obSalary,unpaidBrake,haveWorked,date);
            timeReportViewModel.insert(timeReportModel);
            Toast.makeText(getActivity(), "Tidsrapporten sparades", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Tidsrapporten sparades ej", Toast.LENGTH_SHORT).show();
        }
    }

    private void setSpinner() {
        final ArrayList<String> workPlaceList = new ArrayList<>();
        final ArrayAdapter<String> workPlaceAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, workPlaceList);
        homeSpinner.setAdapter(workPlaceAdapter);
        workplaceViewModel = ViewModelProviders.of(getActivity()).get(WorkplaceViewModel.class);
        workplaceViewModel.getAllWorkplaces().observe(getActivity(), new Observer<List<WorkplaceModel>>() {
            @Override
            public void onChanged(List<WorkplaceModel> workplaceModels) {
                workPlaceList.add("Alla Företag");
                for (WorkplaceModel workplace : workplaceModels){
                    workPlaceList.add(workplace.getCompanyName());
                    workPlaceAdapter.notifyDataSetChanged();
                }

            }
        });

    }
    public void CheckCompanySpinner(){
        homeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (homeSpinner.getSelectedItemPosition() == 0){
                    timeReportViewModel.getAllTimeReports().observe(getActivity(), new Observer<List<TimeReportModel>>() {
                        @Override
                        public void onChanged(List<TimeReportModel> timeReportList) {
                            adapter.setTimeReportList(timeReportList);
                            mTimeReportList = (ArrayList<TimeReportModel>) timeReportList;
                        }
                    });
                } else{
                    timeReportViewModel.getAllTimeReportsByCompanyName(parent.getItemAtPosition(position).toString()).observe(getActivity(), new Observer<List<TimeReportModel>>() {
                        @Override
                        public void onChanged(List<TimeReportModel> timeReportList) {
                            adapter.setTimeReportList(timeReportList);
                            mTimeReportList = (ArrayList<TimeReportModel>) timeReportList;
                        }
                    });
                }
        }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void OnTimeReportClick(int position) {
        Intent intent = new Intent(getActivity(), TimeReportViewActivity.class);
        intent.putExtra("selected_item", mTimeReportList.get(position));
        startActivity(intent);
    }
}
