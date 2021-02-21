package com.example.worktime.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.worktime.models.TimeReportModel;

import java.util.List;

@Dao
public interface TimeReportDao {

    @Insert
    public void insert(TimeReportModel timeReportModel);

    @Update
    public void update(TimeReportModel timeReportModel);

    @Delete
    public void delete(TimeReportModel timeReportModel);

    @Query("SELECT * FROM timereport_table")
    LiveData<List<TimeReportModel>> getAllTimeReportItems();

    @Query("SELECT * FROM timereport_table WHERE company_name == :cName")
    LiveData<List<TimeReportModel>> getTimeReportByCompanyName(String cName);

    @Query("SELECT * FROM timereport_table WHERE worked_date == :trDate")
    LiveData<List<TimeReportModel>> getTimeReportByWorkedDate(String trDate);

}
