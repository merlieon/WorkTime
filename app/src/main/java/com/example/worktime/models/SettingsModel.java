package com.example.worktime.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "settings_table")
public class SettingsModel {

    @PrimaryKey(autoGenerate = true)
    @NonNull int id;
    @ColumnInfo(name = "birth_day")
    String birthDay;
    String language;
    @ColumnInfo(name = "municipal_tax")
    String municipalTax;
    @ColumnInfo(name = "swedish_church")
    public String sChurch;
    @ColumnInfo(name = "religious_community")
    String religiousCommunity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getMunicipalTax() {
        return municipalTax;
    }

    public void setMunicipalTax(String municipalTax) {
        this.municipalTax = municipalTax;
    }

    public String getsChurch() {
        return sChurch;
    }

    public void setsChurch(String sChurch) {
        this.sChurch = sChurch;
    }

    public String getReligiousCommunity() {
        return religiousCommunity;
    }

    public void setReligiousCommunity(String religiousCommunity) {
        this.religiousCommunity = religiousCommunity;
    }
}
