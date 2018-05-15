package com.codecool.web.model;

public class Schedule extends AbstractModel{
    private String name;

    public Schedule(int id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
