package com.example.demo;

import java.util.List;

public class Project {
    private Long id;
    private String name;
    private User manager;

    private List<Task> tasks;

    private List<String> editingFields;

    public Project() {
    }

    public Project(Long id, String name, User manager, List<Task> tasks) {
        this.id = id;
        this.name = name;
        this.manager = manager;
        this.tasks = tasks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<String> getEditingFields() {
        return editingFields;
    }

    public void setEditingFields(List<String> editingFields) {
        this.editingFields = editingFields;
    }
}
