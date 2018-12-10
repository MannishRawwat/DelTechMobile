package com.rawggar.deltechmobile.Models;

import java.util.List;

public class CalendarModel {
    private int day;
    private int month;
    private int year;
    private boolean holiday;
    private List<CalendarEventModel> list;

    public CalendarModel(int day, int month, int year, boolean holiday, List<CalendarEventModel> list) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.holiday = holiday;
        this.list = list;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<CalendarEventModel> getList() {
        return list;
    }

    public void setList(List<CalendarEventModel> list) {
        this.list = list;
    }

    public boolean isHoliday() {
        return holiday;
    }

    public void setHoliday(boolean holiday) {
        this.holiday = holiday;
    }
}
