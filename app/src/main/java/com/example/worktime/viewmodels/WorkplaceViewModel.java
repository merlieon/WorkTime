package com.example.worktime.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.worktime.models.WorkplaceModel;
import com.example.worktime.repositories.WorkplaceRepository;

import java.util.List;

public class WorkplaceViewModel extends AndroidViewModel {
    private WorkplaceRepository repository;
    private LiveData<List<WorkplaceModel>> allWorkplaces;
    public WorkplaceViewModel(@NonNull Application application) {
        super(application);
        repository = new WorkplaceRepository(application);
        allWorkplaces = repository.getAllWorkplaces();
    }

    public void insert(WorkplaceModel workplaceModel){
        repository.insert(workplaceModel);
    }
    public void update(WorkplaceModel workplaceModel){
        repository.update(workplaceModel);
    }
    public void delete(WorkplaceModel workplaceModel){
        repository.delete(workplaceModel);
    }
    public LiveData<List<WorkplaceModel>> getAllWorkplaces(){
        return allWorkplaces;
    }
}
