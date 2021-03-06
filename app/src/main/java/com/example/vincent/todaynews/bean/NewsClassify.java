package com.example.vincent.todaynews.bean;


public class NewsClassify {

    public enum ColumnType {
        NEWS,
        CITY,
        VIDEO
    }

    public Integer id;
    public int type;
    public String title;
    public Boolean is_myinterest;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getIs_myinterest() {
        return is_myinterest;
    }

    public void setIs_myinterest(Boolean is_myinterest) {
        this.is_myinterest = is_myinterest;
    }

}
