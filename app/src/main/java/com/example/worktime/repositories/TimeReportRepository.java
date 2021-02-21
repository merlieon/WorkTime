package com.example.worktime.repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Update;

import com.example.worktime.dao.TimeReportDao;
import com.example.worktime.databases.AppDatabase;
import com.example.worktime.models.TimeReportModel;

import java.util.List;

import static android.content.ContentValues.TAG;

public class TimeReportRepository {
    private TimeReportDao timeReportDao;
    private LiveData<List <TimeReportModel>> allTimeReports;
    private LiveData<List <TimeReportModel>> allTimeReportsByCompanyName;
    private LiveData<List <TimeReportModel>> allTimeReportsByWorkedDate;
    String cName;
    String trDate;
    public TimeReportRepository(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        timeReportDao = database.timeReportDao();
        allTimeReports = timeReportDao.getAllTimeReportItems();
        allTimeReportsByCompanyName = timeReportDao.getTimeReportByCompanyName(cName);
        allTimeReportsByWorkedDate = timeReportDao.getTimeReportByWorkedDate(trDate);

    }

    public void insert(TimeReportModel timeReportModel){
        new InsertTimeReportAsyncTask(timeReportDao).execute(timeReportModel);
    }

    public void update(TimeReportModel timeReportModel){
        new UpdateTimeReportAsyncTask(timeReportDao).execute(timeReportModel);
    }

    public void delete(TimeReportModel timeReportModel){
        new DeleteTimeReportAsyncTask(timeReportDao).execute(timeReportModel);
    }

    public LiveData<List<TimeReportModel>> getAllTimereports(){
        return allTimeReports;
    }

    public LiveData<List <TimeReportModel>> getAllTimeReportsByCompanyName(String cName){
        this.cName = cName;
        allTimeReportsByCompanyName = timeReportDao.getTimeReportByCompanyName(cName);
        return allTimeReportsByCompanyName;
    }

    public LiveData<List <TimeReportModel>> getAllTimeReportsByWorkedDate(String trDate){
        this.trDate = trDate;
        Log.d(TAG, "getAllTimeReportsByWorkedDate: " + trDate);
        allTimeReportsByWorkedDate = timeReportDao.getTimeReportByWorkedDate(trDate);
        return allTimeReportsByWorkedDate;
    }

    private static class InsertTimeReportAsyncTask extends AsyncTask<TimeReportModel, Void, Void>{
        private TimeReportDao timeReportDao;
        private InsertTimeReportAsyncTask(TimeReportDao timeReportDao){
            this.timeReportDao = timeReportDao;
        }

        @Override
        protected Void doInBackground(TimeReportModel... timeReportModels) {
            timeReportDao.insert(timeReportModels[0]);
            return null;
        }

    }

    private static class UpdateTimeReportAsyncTask extends AsyncTask<TimeReportModel, Void, Void>{
        private TimeReportDao timeReportDao;
        private UpdateTimeReportAsyncTask(TimeReportDao timeReportDao){
            this.timeReportDao = timeReportDao;
        }

        @Override
        protected Void doInBackground(TimeReportModel... timeReportModels) {
            timeReportDao.update(timeReportModels[0]);
            return null;
        }

    }

    private static class DeleteTimeReportAsyncTask extends AsyncTask<TimeReportModel, Void, Void>{
        private TimeReportDao timeReportDao;
        private DeleteTimeReportAsyncTask(TimeReportDao timeReportDao){
            this.timeReportDao = timeReportDao;
        }

        @Override
        protected Void doInBackground(TimeReportModel... timeReportModels) {
            timeReportDao.delete(timeReportModels[0]);
            return null;
        }
    }
}
