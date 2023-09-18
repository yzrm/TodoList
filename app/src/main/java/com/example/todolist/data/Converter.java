package com.example.todolist.data;

import androidx.room.TypeConverter;

public class Converter {

    /**
     * int型からBooleam型に変換するメソッド
     * @param value
     * @return
     */
    @TypeConverter
    public static Boolean fromInt(int value) {
        return value == 1 ? true : false ;
    }

    /**
     * Booleam型からint型に変換するメソッド
     * @param value
     * @return
     */
    @TypeConverter
    public static int fromBoolean(Boolean value) {
        return value ? 1 : 0;
    }
}
