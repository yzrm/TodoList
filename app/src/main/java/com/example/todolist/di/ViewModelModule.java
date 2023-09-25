package com.example.todolist.di;

import android.content.Context;

import com.example.todolist.data.repositories.ToDoListRepository;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.android.scopes.ViewModelScoped;

@Module
@InstallIn(ViewModelComponent.class)
public class ViewModelModule {

    @Provides
    @ViewModelScoped
    static ToDoListRepository provideRepository(@ApplicationContext Context context){
        return new ToDoListRepository(context);
    }
}
