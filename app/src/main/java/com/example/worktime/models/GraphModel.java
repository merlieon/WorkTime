package com.example.worktime.models;

public class GraphModel {

    String companyName;
    int year;
    int month;
    int day;
    double hours;

    public GraphModel(String companyName, int year, int month, int day, double hours) {
        this.companyName = companyName;
        this.year = year;
        this.month = month;
        this.hours = hours;
        this.day = day;
    }

    public GraphModel(String companyName, int month, double hours) {
        this.companyName = companyName;
        this.month = month;
        this.hours = hours;
    }

    public String getCompanyName() {
        return companyName;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    @Override
    public String toString() {
        return "GraphModel{" +
                "companyName='" + companyName + '\'' +
                ", year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", hours=" + hours +
                '}';
    }
}
