package com.example.todolist.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.todolist.data.dao.TodoItemDao;
import com.example.todolist.data.dao.TodoSheetDao;
import com.example.todolist.data.entities.TodoItem;
import com.example.todolist.data.entities.TodoSheet;

@Database(entities = { TodoSheet.class, TodoItem.class }, exportSchema = false, version = 1)
@TypeConverters({ Converter.class, DateTimeConverter.class })
public abstract class TodoDatabase extends RoomDatabase {

    public abstract TodoSheetDao todoSheetDao();

    public abstract TodoItemDao todoItemDao();
}
