package com.example.todolist.ui.todoSheetDetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.todolist.R;
import com.example.todolist.ui.todoSheetList.TodoSheetListFragment;


public class TodoSheetDetailFragment extends Fragment {

    public static final String KEY_TODO_SHEET_ID = "com.example.todolist.key_todo_sheet_id";
    public static final String KEY_TODO_SHEET_TITLE = "com.example.todolist.key_todo_sheet_title";

    private int todoSheetId = -1;
    private String todoSheetTitle = "";

    /**
     * TodoSheetDetailFragmentのインスタンス生成メソッド
     * @return インスタンス
     */
    public static TodoSheetDetailFragment newInstance(int id, String title){
        Bundle args = new Bundle();
        args.putInt(KEY_TODO_SHEET_ID, id);
        args.putString(KEY_TODO_SHEET_TITLE, title);
        TodoSheetDetailFragment fragment = new TodoSheetDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private TodoSheetDetailFragment(){}
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_todosheetdetail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //TodoSheetIdとTitleの取得
        if (getArguments() != null){
            todoSheetId = getArguments().getInt(KEY_TODO_SHEET_ID, -1);
            todoSheetTitle = getArguments().getString(KEY_TODO_SHEET_TITLE, "");
        }

        // SheetTitle用TextViewの取得
        TextView todoSheetTitleTextView = view.findViewById(R.id.todo_sheet_title);
        // タイトルの設定
        todoSheetTitleTextView.setText(todoSheetTitle);
    }

    @Override
    public void onResume() {
        super.onResume();
        // TODO:画面の表示更新
    }
}
