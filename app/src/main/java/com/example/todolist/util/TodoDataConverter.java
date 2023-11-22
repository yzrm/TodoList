package com.example.todolist.util;

import com.example.todolist.data.entities.Todo;
import com.example.todolist.data.entities.TodoData;
import com.example.todolist.data.entities.TodoItem;
import com.example.todolist.data.entities.TodoSheet;

import java.util.ArrayList;
import java.util.List;

public class TodoDataConverter {

    /**
     * TodoItemをTodoに変換するメソッド
     * @param todoItem
     * @return
     */
    public static Todo toTodo(TodoItem todoItem) {
        return new Todo(
                todoItem.id,
                todoItem.todoSheetId,
                todoItem.detail,
                todoItem.isDeleted,
                todoItem.createdAt
        );
    }

    /**
     * TodoSheetとTodoItemからTodoDataへ変換するメソッド
     * @param todoSheet
     * @param todoItemList
     * @return
     */
    public static TodoData toTodoData(TodoSheet todoSheet, List<TodoItem> todoItemList){
        // Todoのリストを作成する
        List<Todo> todoList = new ArrayList<>();
        for (TodoItem item : todoItemList){
            Todo todo = toTodo(item);
            todoList.add(todo);
        }

        return new TodoData(
                todoSheet.id,
                todoSheet.title,
                todoSheet.createdAt,
                todoList
        );
    }
}
