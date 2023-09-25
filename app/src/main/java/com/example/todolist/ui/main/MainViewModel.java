package com.example.todolist.ui.main;

import androidx.lifecycle.ViewModel;

import com.example.todolist.data.entities.TodoSheet;
import com.example.todolist.data.repositories.ToDoListRepository;

import java.util.Date;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainViewModel extends ViewModel {

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
}
