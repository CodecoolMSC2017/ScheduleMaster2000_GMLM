package com.codecool.web.model;

public class Slot {

    private int hour;
    private int dayId;
    private int taskId;

    public Slot(int hour, int dayId, int taskId) {
        this.hour = hour;
        this.dayId = dayId;
        this.taskId = taskId;
    }

    public int getHour() {
        return hour;
    }

    public int getDayId() {
        return dayId;
    }

    public int getTaskId() {
        return taskId;
    }
}
