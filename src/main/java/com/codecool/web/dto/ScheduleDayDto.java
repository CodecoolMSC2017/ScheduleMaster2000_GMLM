package com.codecool.web.dto;

import com.codecool.web.model.Day;
import com.codecool.web.model.Schedule;

import java.util.List;

public class ScheduleDayDto {

    private final Schedule schedule;
    private final List<Day> daysOfSchedule;

    public ScheduleDayDto(Schedule schedule, List<Day> daysOfSchedule) {
        this.schedule = schedule;
        this.daysOfSchedule = daysOfSchedule;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public List<Day> getDaysOfSchedule() {
        return daysOfSchedule;
    }
}
