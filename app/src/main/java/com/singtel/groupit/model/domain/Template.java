package com.singtel.groupit.model.domain;

/**
 * Created by razelsoco on 1/7/16.
 */

public class Template {
    public int id;
    public String url;
    boolean isSelected;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
