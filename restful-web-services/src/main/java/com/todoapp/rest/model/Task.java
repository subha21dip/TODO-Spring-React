package com.todoapp.rest.model;

import java.util.Date;
import java.util.Objects;

public class Task {


    private Long id;
    private String username;
    private String task;
    private String description;
    private Date targetDate;
    private boolean done;

    protected Task() {

    }
    public Task(long id, String username, String task, Date targetDate, boolean done, String description) {
        this.id = id;
        this.username = username;
        this.task = task;
        this.targetDate = targetDate;
        this.done = done;
        this.description = description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getTask() {
        return task;
    }

    public Date getTargetDate() {
        return targetDate;
    }

    public boolean isDone() {
        return done;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
