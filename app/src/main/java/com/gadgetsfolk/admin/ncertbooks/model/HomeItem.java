package com.gadgetsfolk.admin.ncertbooks.model;

public class HomeItem {
    private int iconResourceId;
    private String title;

    public HomeItem(int iconResourceId, String title) {
        this.iconResourceId = iconResourceId;
        this.title = title;
    }

    public int getIconResourceId() {
        return iconResourceId;
    }

    public void setIconResourceId(int iconResourceId) {
        this.iconResourceId = iconResourceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
