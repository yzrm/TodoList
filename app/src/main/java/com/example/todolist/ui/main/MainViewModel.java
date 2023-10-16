package com.example.todolist.ui.main;

import androidx.lifecycle.ViewModel;

import com.example.todolist.data.entities.TodoSheet;
import com.example.todolist.data.repositories.ToDoListRepository;
import com.example.todolist.ui.adapter.TodoSheetListAdapter;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainViewModel extends ViewModel {

    interface OnActionListener {
        void getTodoSheetAll(List<TodoSheet> todoSheetList);
    }

    private ToDoListRepository repository;
    @Inject
    MainViewModel(ToDoListRepository repository){
        this.repository = repository;
    }

    public void addNewTodoSheet(String title){
        new Thread(){
            @Override
            public void run() {
                super.run();
                TodoSheet newSheet = new TodoSheet();
                newSheet.title = title;
                newSheet.createdAt = new Date();

                repository.insertTodoSheet(newSheet);
            }
        }.start();
    }

    public void getTodoSheetAll(OnActionListener listener){
        new Thread(){
            @Override
            public void run(){
                super.run();
                List<TodoSheet> todoSheetList = repository.getTodoSheetAll();
                listener.getTodoSheetAll(todoSheetList);
            }
        }.start();
    }
}
