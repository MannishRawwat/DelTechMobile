package com.rawggar.deltechmobile.Models;

import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ServiceModel {
    private String title;
    private ArrayList<ServicePerson> list;

    public ServiceModel(String title, ArrayList<ServicePerson> list) {
        this.title = title;
        this.list = list;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<ServicePerson> getList() {
        return list;
    }

    public void setList(ArrayList<ServicePerson> list) {
        this.list = list;
    }
}
