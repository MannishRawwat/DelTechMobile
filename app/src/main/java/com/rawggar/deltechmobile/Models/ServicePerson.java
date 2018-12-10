package com.rawggar.deltechmobile.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class ServicePerson implements Parcelable{
    private String name;
    private String contact;
    public ServicePerson(String name, String contact) {
        this.name = name;
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected ServicePerson(Parcel in){
        name = in.readString();
        contact = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(name);
            parcel.writeString(contact);
    }

    public static final Creator<ServicePerson> CREATOR = new Creator<ServicePerson>() {
        @Override
        public ServicePerson createFromParcel(Parcel parcel) {
            return new ServicePerson(parcel);
        }

        @Override
        public ServicePerson[] newArray(int i) {
            return new ServicePerson[i];
        }
    };

}
