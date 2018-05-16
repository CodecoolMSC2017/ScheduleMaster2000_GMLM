package com.codecool.web.model;

public class Task extends AbstractModel {
    private String name;
    private String content;
    private int hour;

    public Task(int id, String name, String content, int hour) {
        super(id);
        this.name = name;
        this.content = content;
        this.hour = hour;
    }

    public Task(int id, String name, String content) {
        super(id);
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public int getHour() {
        return hour;
    }
}
