package com.todoapp.rest.model;

import java.util.List;

public class TaskListRange {
    String prevUrl;
    String nextUrl;
    List<Task> taskList;
    int totalPages;

    public TaskListRange(String prevUrl, String nextUrl, List<Task> taskList, int totalPages) {
        this.prevUrl = prevUrl;
        this.nextUrl = nextUrl;
        this.taskList = taskList;
        this.totalPages = totalPages;
    }

    public String getPrevUrl() {
        return prevUrl;
    }

    public void setPrevUrl(String prevUrl) {
        this.prevUrl = prevUrl;
    }

    public String getNextUrl() {
        return nextUrl;
    }

    public void setNextUrl(String nextUrl) {
        this.nextUrl = nextUrl;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
