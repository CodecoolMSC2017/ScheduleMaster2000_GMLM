package com.codecool.web.model;

public class Schedule extends AbstractModel{
    private String name;
    private boolean isPublished;

    public Schedule(int id, String name, boolean isPublished) {
        super(id);
        this.name = name;
        this.isPublished = isPublished;
    }

    public String getName() {
        return name;
    }

    public boolean isPublished() {
        return isPublished;
    }
}
