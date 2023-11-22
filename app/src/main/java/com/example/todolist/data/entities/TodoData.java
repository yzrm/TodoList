package com.example.todolist.data.entities;

import java.util.Date;
import java.util.List;

public class TodoData {

    private int id;

    private String title;

    private Date createdAt;

    private List<Todo> todoList;

    public TodoData(int id, String title, Date createdAt, List<Todo> todoList) {
        this.id = id;
        this.title = title;
        this.createdAt = createdAt;
        this.todoList = todoList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<Todo> getTodoList() {
        return todoList;
    }

    public void setTodoList(List<Todo> todoList) {
        this.todoList = todoList;
    }
}
