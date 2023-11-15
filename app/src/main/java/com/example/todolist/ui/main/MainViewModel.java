package com.example.todolist.ui.main;

import androidx.lifecycle.ViewModel;

import com.example.todolist.data.entities.TodoItem;
import com.example.todolist.data.entities.TodoSheet;
import com.example.todolist.data.repositories.ToDoListRepository;

import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainViewModel extends ViewModel {

    interface GetTodoSheetAll {
        void onComplete(List<TodoSheet> todoSheetList);

    }
    interface GetTodoItemList{
        void onComplete(List<TodoItem> todoItemList);
    }
    interface  OnCompleteCallback{
        void onComplete();
    }

    private ToDoListRepository repository;
    @Inject
    MainViewModel(ToDoListRepository repository){
        this.repository = repository;
    }

    public void addNewTodoSheet(String title, OnCompleteCallback callback){
        new Thread(){
            @Override
            public void run() {
                super.run();
                TodoSheet newSheet = new TodoSheet();
                newSheet.title = title;
                newSheet.createdAt = new Date();

                repository.insertTodoSheet(newSheet);
                callback.onComplete();
            }
        }.start();
    }

    public void addTodoItem(int todoSheetId, String detail, OnCompleteCallback callback){
        new Thread() {
            @Override
            public void run() {
                super.run();
                TodoItem todoItem = new TodoItem();
                todoItem.todoSheetId = todoSheetId;
                todoItem.detail = detail;
                todoItem.isDeleted = false;
                todoItem.createdAt = new Date();
                repository.insertTodoItem(todoItem);
                callback.onComplete();
            }
        }.start();
    }

    public void getTodoSheetAll(GetTodoSheetAll callback){
        new Thread(){
            @Override
            public void run() {
                super.run();
                List<TodoSheet> todoSheetList = repository.getTodoSheetAll();
                callback.onComplete(todoSheetList);
            }
        }.start();
    }
    public void getTodoItemListById(int todoSheetId, GetTodoItemList callback){
        new Thread() {
            @Override
            public void run() {
                super.run();
                List<TodoItem> todoItemList = repository.getTodoItemListById(todoSheetId);
                callback.onComplete(todoItemList);
            }
        }.start();
    }
}
