package com.example.vincent.todaynews.bean;

/**
 * Created by vincent on 16/1/6.
 */
public class LeftItemClassify {
    public int iconId;
    public String text;

    public LeftItemClassify(int iconId, String text) {
        this.iconId = iconId;
        this.text = text;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
