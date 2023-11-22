package com.example.todolist.data.entities;

import java.util.Date;

public class Todo {

    private int id;

    private int todoSheetId;

    private String detail;

    private Boolean isDeleted;

    private Date createdAt;

    public Todo(int id, int todoSheetId, String detail, Boolean isDeleted, Date createdAt) {
        this.id = id;
        this.todoSheetId = todoSheetId;
        this.detail = detail;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTodoSheetId() {
        return todoSheetId;
    }

    public void setTodoSheetId(int todoSheetId) {
        this.todoSheetId = todoSheetId;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
