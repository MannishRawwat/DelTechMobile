package com.rawggar.deltechmobile.Models;

public class CalendarEventModel {
    private String desc;
    private String url;

    public CalendarEventModel(String desc, String url) {
        this.desc = desc;
        this.url = url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
