package com.gadgetsfolk.admin.ncertbooks.model;

public class Book {
    private String class_name;

    public Book(String class_name) {
        this.class_name = class_name;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }
}
