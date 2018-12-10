package com.rawggar.deltechmobile.Models;

import java.util.Date;

public class IssuesModel {

    Date createdAt;
    String desc;
    String url;
    Boolean resolved;

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


    public IssuesModel(String desc, String url, Boolean resolved) {
        this.desc = desc;
        this.url = url;
        this.resolved = resolved;
    }

    public IssuesModel(Date createdAt, String desc, String url, Boolean resolved) {
        this.createdAt = createdAt;
        this.desc = desc;
        this.url = url;
        this.resolved = resolved;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getResolved() {
        return resolved;
    }

    public void setResolved(Boolean resolved) {
        this.resolved = resolved;
    }
}
