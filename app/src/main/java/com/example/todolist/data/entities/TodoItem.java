package com.example.todolist.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class TodoItem {
    /**
     * ID
     */
    @PrimaryKey(autoGenerate = true)
    public int id;

    /**
    * TodoSheetのID
    */
    @ColumnInfo(name = "todo_sheet_id")
    public int todoSheetId;

    /**
    * 詳細
    */
    @ColumnInfo(name = "detail")
    public String detail;

    /**
    * 完了フラグ
    */
    @ColumnInfo(name = "is_deleted")
    public Boolean isDeleted;

    /**
    * 作成日時
    */
    @ColumnInfo(name = "created_at")
    public Date createdAt;
}
