package com.example.worktime.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.worktime.models.TimeReportModel;
import com.example.worktime.repositories.TimeReportRepository;

import java.util.List;

import static android.content.ContentValues.TAG;

public class TimeReportViewModel extends AndroidViewModel {
    private TimeReportRepository repository;
    private LiveData<List<TimeReportModel>> allTimeReports;
    private LiveData<List<TimeReportModel>> allTimeReportsByCompanyName;
    private LiveData<List<TimeReportModel>> allTimeReportsByWorkedDate;
    String cName;
    String trDate;
    public TimeReportViewModel(@NonNull Application application) {
        super(application);
        repository = new TimeReportRepository(application);
        allTimeReports = repository.getAllTimereports();
        allTimeReportsByCompanyName = repository.getAllTimeReportsByCompanyName(cName);
        allTimeReportsByWorkedDate = repository.getAllTimeReportsByWorkedDate(trDate);
    }

    public void insert(TimeReportModel timeReportModel){
        repository.insert(timeReportModel);
    }
    public void update(TimeReportModel timeReportModel){
        repository.update(timeReportModel);
    }
    public void delete(TimeReportModel timeReportModel){
        repository.delete(timeReportModel);
    }
    public LiveData<List<TimeReportModel>> getAllTimeReports(){
        return allTimeReports;
    }
    public LiveData<List<TimeReportModel>> getAllTimeReportsByCompanyName(String cName){
        this.cName = cName;
        allTimeReportsByCompanyName = repository.getAllTimeReportsByCompanyName(cName);
        return allTimeReportsByCompanyName;
    }
    public LiveData<List<TimeReportModel>> getAllTimeReportsByWorkedDate(String trDate){
        this.trDate = trDate;
        Log.d(TAG, "getAllTimeReportsByWorkedDate: " + trDate);
        allTimeReportsByWorkedDate = repository.getAllTimeReportsByWorkedDate(trDate);
        return allTimeReportsByWorkedDate;
    }
}
