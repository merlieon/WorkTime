package com.example.worktime.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity (tableName = "workplace_table")
public class WorkplaceModel implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @NonNull int id;

    @ColumnInfo(name = "company_name")
    String companyName;
    @ColumnInfo(name = "company_address")
    String companyAddress;
    @ColumnInfo(name = "company_orgnum")
    String companyOrgNum;
    public double salary;
    //@ColumnInfo(name = "ob_salary_percent")
    //String obHourSalaryPercent;
    @ColumnInfo(name = "ob_hour_salary")
    public String obHourSalary;
    public WorkplaceModel(String companyName, String companyAddress, String companyOrgNum,double salary, String obHourSalary){
        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.companyOrgNum = companyOrgNum;
        this.salary = salary;
        this.obHourSalary = obHourSalary;
       // this.obHourSalaryPercent = obHourSalaryPercent;
    }

    @Ignore
    public WorkplaceModel(String companyName, String companyAddress){
        this.companyName = companyName;
        this.companyAddress = companyAddress;
    }

    protected WorkplaceModel(Parcel in) {
        id = in.readInt();
        companyName = in.readString();
        companyAddress = in.readString();
        companyOrgNum = in.readString();
        salary = in.readDouble();
        //obHourSalaryPercent = in.readString();
        obHourSalary = in.readString();
    }

    public static final Creator<WorkplaceModel> CREATOR = new Creator<WorkplaceModel>() {
        @Override
        public WorkplaceModel createFromParcel(Parcel in) {
            return new WorkplaceModel(in);
        }

        @Override
        public WorkplaceModel[] newArray(int size) {
            return new WorkplaceModel[size];
        }
    };

    public String getobHourSalary() {
        return obHourSalary;
    }

    public void setobHourSalary(String obHourSalary) {
        this.obHourSalary = obHourSalary;
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

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyOrgNum() {
        return companyOrgNum;
    }

    public void setCompanyOrgNum(String companyOrgNum) {
        this.companyOrgNum = companyOrgNum;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(companyName);
        dest.writeString(companyAddress);
        dest.writeString(companyOrgNum);
        dest.writeDouble(salary);
        //dest.writeString(obHourSalaryPercent);
        dest.writeString(obHourSalary);
    }

    @Override
    public String toString() {
        return "WorkplaceModel{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", companyAddress='" + companyAddress + '\'' +
                ", companyOrgNum='" + companyOrgNum + '\'' +
                ", salary=" + salary +
               // ", obHourSalaryPercent=" + obHourSalaryPercent +
                ", obHourSalary=" + obHourSalary +
                '}';
    }
}
