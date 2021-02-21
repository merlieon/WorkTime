package com.example.worktime.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "timereport_table")
public class TimeReportModel implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @NonNull int id;
    @ColumnInfo(name = "company_name")
    String companyName;
    double salary;
    @ColumnInfo(name = "hour_salary")
    double hourSalary;
    double hours;
    @ColumnInfo(name = "ob_hours")
    double obHours;
    @ColumnInfo(name = "ob_hour_salary")
    String obSalary;
    //@ColumnInfo(name = "ob_percent")
    //public double obSalaryPercent;
    @ColumnInfo(name = "unpaid_brake")
    double unpaidBrake;
    @ColumnInfo(name = "have_worked")
    Boolean haveWorked;
    @ColumnInfo(name = "worked_date")
    String workedDate;

    public TimeReportModel(String companyName,double salary,double hourSalary, double hours, double obHours, String obSalary, double unpaidBrake, Boolean haveWorked, String workedDate){
        this.companyName = companyName;
        this.salary = salary;
        this.hourSalary = hourSalary;
        this.hours = hours;
        this.obHours = obHours;
        this.obSalary = obSalary;
       // this.obSalaryPercent = obSalaryPercent;
        this.haveWorked = haveWorked;
        this.unpaidBrake = unpaidBrake;
        this.workedDate = workedDate;
    }

    protected TimeReportModel(Parcel in) {
        id = in.readInt();
        companyName = in.readString();
        salary = in.readDouble();
        hourSalary = in.readDouble();
        hours = in.readDouble();
        obHours = in.readDouble();
        obSalary = in.readString();
       // obSalaryPercent = in.readDouble();
        unpaidBrake = in.readDouble();
        byte tmpHaveWorked = in.readByte();
        haveWorked = tmpHaveWorked == 0 ? null : tmpHaveWorked == 1;
        workedDate = in.readString();
    }

    public static final Creator<TimeReportModel> CREATOR = new Creator<TimeReportModel>() {
        @Override
        public TimeReportModel createFromParcel(Parcel in) {
            return new TimeReportModel(in);
        }

        @Override
        public TimeReportModel[] newArray(int size) {
            return new TimeReportModel[size];
        }
    };

    public double getHourSalary() {
        return hourSalary;
    }

    public void setHourSalary(double hourSalary) {
        this.hourSalary = hourSalary;
    }

  /*  public double getObSalaryPercent() {
        return obSalaryPercent;
    }

    public void setObSalaryPercent(double obSalaryPercent) {
        this.obSalaryPercent = obSalaryPercent;
    }*/

    public String getObSalary() {
        return obSalary;
    }

    public void setObSalary(String obSalary) {
        this.obSalary = obSalary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public double getUnpaidBrake() {
        return unpaidBrake;
    }

    public void setUnpaidBrake(double unpaidBrake) {
        this.unpaidBrake = unpaidBrake;
    }

    public Boolean getHaveWorked() {
        return haveWorked;
    }

    public void setHaveWorked(Boolean haveWorked) {
        this.haveWorked = haveWorked;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    public double getObHours() {
        return obHours;
    }

    public void setObHours(double obHours) {
        this.obHours = obHours;
    }

    public String getWorkedDate() {
        return workedDate;
    }

    public void setWorkedDate(String workedDate) {
        this.workedDate = workedDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(companyName);
        dest.writeDouble(salary);
        dest.writeDouble(hourSalary);
        dest.writeDouble(hours);
        dest.writeDouble(obHours);
        dest.writeString(obSalary);
     //   dest.writeDouble(obSalaryPercent);
        dest.writeDouble(unpaidBrake);
        dest.writeByte((byte) (haveWorked == null ? 0 : haveWorked ? 1 : 2));
        dest.writeString(workedDate);
    }

    @Override
    public String toString() {
        return "TimeReportModel{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", salary=" + salary +
                ", hourSalary=" + hourSalary +
                ", hours=" + hours +
                ", obHours=" + obHours +
                ", obSalary=" + obSalary +
               // ", obSalaryPercent=" + obSalaryPercent +
                ", unpaidBrake=" + unpaidBrake +
                ", haveWorked=" + haveWorked +
                ", workedDate='" + workedDate + '\'' +
                '}';
    }
}
