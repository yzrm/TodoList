package com.example.todolist.data;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateTimeConverter {

    /**
     * タイムスタンプからDate型に変換するメソッド
     * @param value
     * @return
     */
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return  value == null ? null : new Date(value);
    }

    /**
     * Date型からタイムスタンプに変換するメソッド
     */
    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
