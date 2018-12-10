package com.rawggar.deltechmobile.Models;

import java.util.Date;

public class NewsModel {
    String title;
    String url;
    Date createdAt;

    public NewsModel(String title, String url, Date createdAt) {

        this.title = title;
        this.url = url;
        this.createdAt = createdAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreatedAt() {return createdAt;}

    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt;}
}
