package com.example.worktime.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.worktime.models.SettingsModel;
import com.example.worktime.models.TimeReportModel;

import java.util.List;

@Dao
public interface SettingsDao {

    @Insert
    public void insert(SettingsModel settingsModel);

    @Update
    public void update(SettingsModel settingsModel);

    @Delete
    public void delete(SettingsModel settingsModel);

    @Query("SELECT * FROM settings_table")
    LiveData<List<SettingsModel>> getAllSettingsItem();

}
