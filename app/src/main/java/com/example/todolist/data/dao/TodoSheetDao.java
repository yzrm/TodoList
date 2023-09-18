package com.example.todolist.data.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Upsert;

import com.example.todolist.data.entities.TodoSheet;

import java.util.List;

@Dao
public interface TodoSheetDao {

    @Upsert
    void insert(TodoSheet todoSheet);

    @Query("SELECT * FROM todosheet")
    List<TodoSheet> getAll();
}
