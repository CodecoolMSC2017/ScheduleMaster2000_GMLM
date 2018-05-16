package com.codecool.web.model;

public class Day extends AbstractModel {

    private String title;

    public Day(int id, String title) {
        super(id);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
