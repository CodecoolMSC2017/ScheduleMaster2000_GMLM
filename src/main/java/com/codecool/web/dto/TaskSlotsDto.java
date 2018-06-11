package com.codecool.web.dto;

import com.codecool.web.model.Day;
import com.codecool.web.model.Task;

import java.util.List;

public class TaskSlotsDto {

    private final List<Task> unassignedTasks;
    private final List<Integer> startHours;
    private final List<Integer> endHours;

    public TaskSlotsDto(List<Task> unassignedTasks, List<Integer> startHours, List<Integer> endHours) {
        this.unassignedTasks = unassignedTasks;
        this.startHours = startHours;
        this.endHours = endHours;

    }
}
