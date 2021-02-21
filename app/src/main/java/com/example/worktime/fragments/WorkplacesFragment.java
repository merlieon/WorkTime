package com.example.worktime.fragments;

import android.content.Intent;
import android.icu.text.DateTimePatternGenerator;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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
import com.example.worktime.WorkplaceAddActivity;
import com.example.worktime.WorkplaceViewActivity;
import com.example.worktime.adapters.TimeReportListAdapter;
import com.example.worktime.adapters.WorkplaceListAdapter;
import com.example.worktime.models.TimeReportModel;
import com.example.worktime.models.WorkplaceModel;
import com.example.worktime.viewmodels.TimeReportViewModel;
import com.example.worktime.viewmodels.WorkplaceViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

public class WorkplacesFragment extends Fragment implements WorkplaceListAdapter.OnWorkPlaceListner {
    public static final int ADD_C_REQUEST = 1;

    WorkplaceViewModel workplaceViewModel;
    RecyclerView recyclerView;
    ArrayList<WorkplaceModel> mWorkplaceList = new ArrayList<>();
    WorkplaceListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workplaces, container, false);
        setHasOptionsMenu(true);
        recyclerView = view.findViewById(R.id.workplaceRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new WorkplaceListAdapter(mWorkplaceList, this);
        recyclerView.setAdapter(adapter);
        setRecycleView();

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
                Intent intent = new Intent(getActivity(), WorkplaceAddActivity.class);
                startActivityForResult(intent, ADD_C_REQUEST);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Workplace Activity result
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_C_REQUEST && resultCode == getActivity().RESULT_OK) {
            String companyName = data.getStringExtra(WorkplaceAddActivity.EXTRA_C_NAME);
            String companyAddress = data.getStringExtra(WorkplaceAddActivity.EXTRA_C_ADDRESS);
            String companyOrgNum = data.getStringExtra(WorkplaceAddActivity.EXTRA_C_ORGNUM);
            double companySalary = data.getDoubleExtra(WorkplaceAddActivity.EXTRA_C_SALARY, 0);
            String companyObHourSalary = data.getStringExtra(WorkplaceAddActivity.EXTRA_C_OB_HOUR_SALARY);
            WorkplaceModel workplaceModel = new WorkplaceModel(companyName, companyAddress, companyOrgNum, companySalary, companyObHourSalary);
            workplaceViewModel.insert(workplaceModel);
            Toast.makeText(getActivity(), "Jobbplats sparad", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Jobbplats inte sparad", Toast.LENGTH_SHORT).show();
        }
    }

    private void setRecycleView() {
        workplaceViewModel = ViewModelProviders.of(this).get(WorkplaceViewModel.class);
        workplaceViewModel.getAllWorkplaces().observe(getActivity(), new Observer<List<WorkplaceModel>>() {
            @Override
            public void onChanged(List<WorkplaceModel> workplaceList) {
                adapter.setWorkplaceList(workplaceList);
                mWorkplaceList = (ArrayList) workplaceList;
            }
        });
    }

    @Override
    public void onWorkplaceClick(int position) {
        Intent intent = new Intent(getActivity(), WorkplaceViewActivity.class);
        intent.putExtra("selected_item", mWorkplaceList.get(position));
        startActivity(intent);
    }
}
