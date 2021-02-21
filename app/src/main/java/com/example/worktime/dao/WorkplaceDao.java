package com.example.worktime.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.worktime.models.TimeReportModel;
import com.example.worktime.models.WorkplaceModel;

import java.util.List;

@Dao
public interface WorkplaceDao {

    @Insert
    void insert(WorkplaceModel workplaceModel);

    @Update
    void update(WorkplaceModel workplaceModel);

    @Delete
    void delete(WorkplaceModel workplaceModel);

    @Query("SELECT * FROM workplace_table")
    LiveData<List<WorkplaceModel>> getAllWorkplaceItems();

}
