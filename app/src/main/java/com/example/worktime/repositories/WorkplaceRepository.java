package com.example.worktime.repositories;

import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.worktime.dao.TimeReportDao;
import com.example.worktime.dao.WorkplaceDao;
import com.example.worktime.databases.AppDatabase;
import com.example.worktime.models.TimeReportModel;
import com.example.worktime.models.WorkplaceModel;

import java.util.List;

public class WorkplaceRepository {
    private WorkplaceDao workplaceDao;
    private LiveData<List <WorkplaceModel>> allWorkplaces;
    public WorkplaceRepository(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        workplaceDao = database.workplaceDao();
        allWorkplaces = workplaceDao.getAllWorkplaceItems();
    }

    public void insert(WorkplaceModel workplaceModel){
        new InsertWorkplaceAsyncTask(workplaceDao).execute(workplaceModel);
    }

    public void update(WorkplaceModel workplaceModel){
        new UpdateWorkplaceAsyncTask(workplaceDao).execute(workplaceModel);
    }

    public void delete(WorkplaceModel workplaceModel){
        new DeleteWorkplaceAsyncTask(workplaceDao).execute(workplaceModel);
    }

    public LiveData<List<WorkplaceModel>> getAllWorkplaces(){
        return allWorkplaces;
    }

    private static class InsertWorkplaceAsyncTask extends AsyncTask<WorkplaceModel, Void, Void>{
        private WorkplaceDao workplaceDao;
        private InsertWorkplaceAsyncTask(WorkplaceDao workplaceDao){
            this.workplaceDao = workplaceDao;
        }

        @Override
        protected Void doInBackground(WorkplaceModel... workplaceModels) {
            workplaceDao.insert(workplaceModels[0]);
            return null;
        }
    }

    private static class UpdateWorkplaceAsyncTask extends AsyncTask<WorkplaceModel, Void, Void>{
        private WorkplaceDao workplaceDao;
        private UpdateWorkplaceAsyncTask(WorkplaceDao workplaceDao){
            this.workplaceDao = workplaceDao;
        }

        @Override
        protected Void doInBackground(WorkplaceModel... workplaceModels) {
            workplaceDao.update(workplaceModels[0]);
            return null;
        }
    }

    private static class DeleteWorkplaceAsyncTask extends AsyncTask<WorkplaceModel, Void, Void>{
        private WorkplaceDao workplaceDao;
        private DeleteWorkplaceAsyncTask(WorkplaceDao workplaceDao){
            this.workplaceDao = workplaceDao;
        }

        @Override
        protected Void doInBackground(WorkplaceModel... workplaceModels) {
            workplaceDao.delete(workplaceModels[0]);
            return null;
        }
    }
}
