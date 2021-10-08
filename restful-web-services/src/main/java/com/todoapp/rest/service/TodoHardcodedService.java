package com.todoapp.rest.service;

import com.todoapp.rest.model.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TodoHardcodedService {

    private static List<Task> tasks = new ArrayList<>();
    private static long idCounter = 0;

    static {
        tasks.add(new Task(++idCounter, "subhadip", "Learn GO", new Date(), false, "Need to learn GO language"));
        tasks.add(new Task(++idCounter, "subhadip", "Learn MongoDB", new Date(), false, "Need to learn Mongo DB and bson"));
        tasks.add(new Task(++idCounter, "subhadip", "Learn React", new Date(), false, "Need to learn React and promise"));
    }

    public List<Task> findAll() {
        return tasks;
    }

    public Task deleteById(long id){
        Task task = findById(id);
        if(task == null) return null;
        if(tasks.remove(task)) {
            return task;
        }
        return null;
    }

    public Task findById(long id){
        for(Task task : tasks){
            if(task.getId() == id){
                return task;
            }
        }
        return null;
    }

    public Task save(Task task) {
        if(task.getId()==-1 || task.getId()==0){
            task.setId(++idCounter);
            tasks.add(task);
        }
        else {
            deleteById(task.getId());
            tasks.add(task);
        }
        return task;
    }


    public List<Task> findByFilter(String name, String description) {
        List<Task> taskList = new ArrayList<>();
        if(name == null){
            for(Task task : tasks){
                if(task.getDescription().equals(description)){
                    taskList.add(task);
                }
            }
        }
        else if(description == null){
            for(Task task : tasks){
                if(task.getTask().equals(name)){
                    taskList.add(task);
                }
            }
        }else{
            for(Task task : tasks){
                if(task.getDescription().equals(description) && task.getTask().equals(name)){
                    taskList.add(task);
                }
            }
        }
        return taskList;
    }
}
