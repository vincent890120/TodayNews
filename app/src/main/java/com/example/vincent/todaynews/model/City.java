package com.example.vincent.todaynews.model;

/**
 * Created by vincent on 16/2/23.
 */
public class City {
    public String name;
    public int id;
    private int firstChar;

    public City(int id, String name, int firstChar) {
        this.name = name;
        this.id = id;
        this.firstChar = firstChar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFirstChar() {
        return firstChar;
    }

    public void setFirstChar(int firstChar) {
        this.firstChar = firstChar;
    }

    public String firstChar() {
        return firstChar == 0 ? "" : String.valueOf((char) firstChar);
    }

}
