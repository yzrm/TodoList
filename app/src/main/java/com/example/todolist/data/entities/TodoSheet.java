package com.example.todolist.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class TodoSheet {

    /**
     * ID
     */
    @PrimaryKey(autoGenerate = true)
    public int id;

    /**
     * タイトル
     */
    @ColumnInfo(name = "title")
    public String title;

    /**
     * 作成日時
     */
    @ColumnInfo(name = "created_at")
    public Date createdAt;
}
