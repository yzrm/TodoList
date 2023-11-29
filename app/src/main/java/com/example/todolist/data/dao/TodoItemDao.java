package com.example.todolist.data.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Upsert;

import com.example.todolist.data.entities.TodoItem;

import java.util.List;

@Dao
public interface TodoItemDao {

    @Upsert
    void insert(TodoItem todoItem);

    @Query("SELECT * FROM todoitem")
    List<TodoItem> getAll();

    @Query("SELECT * FROM todoitem WHERE todo_sheet_id = :todoSheetId")
    List<TodoItem> getTodoItemListById(int todoSheetId);

    @Query("SELECT COUNT(*) FROM todoitem WHERE todo_sheet_id = :todoSheetId")
    int getItemCount(int todoSheetId);
}
