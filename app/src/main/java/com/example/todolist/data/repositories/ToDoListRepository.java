package com.example.todolist.data.repositories;

import android.content.Context;

import androidx.room.Room;

import com.example.todolist.data.TodoDatabase;
import com.example.todolist.data.dao.TodoItemDao;
import com.example.todolist.data.dao.TodoSheetDao;
import com.example.todolist.data.entities.TodoSheet;

import java.util.List;

public class ToDoListRepository {

    private TodoDatabase db;

    private TodoSheetDao todoSheetDao;

    private TodoItemDao todoItemDao;

    public ToDoListRepository(Context context){
        db = Room.databaseBuilder(
                context,
                TodoDatabase.class,
                "todolist-db"
        ).build();
        todoSheetDao = db.todoSheetDao();
        todoItemDao = db.todoItemDao();
    }
    public void insertTodoSheet(TodoSheet todoSheet){
        todoSheetDao.insert(todoSheet);
    }

    public List<TodoSheet> getTodoSheetAll(){
        return todoSheetDao.getAll();
    }
}
