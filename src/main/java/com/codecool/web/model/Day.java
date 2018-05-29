package com.codecool.web.model;

public class Day extends AbstractModel {

    private String title;
    private int scheduleId;

    public Day(int id, String title, int scheduleId) {
        super(id);
        this.title = title;
        this.scheduleId = scheduleId;
    }

    public String getTitle() {
        return title;
    }
}
