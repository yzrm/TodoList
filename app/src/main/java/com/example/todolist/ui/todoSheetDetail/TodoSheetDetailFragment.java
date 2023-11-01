package com.example.todolist.ui.todoSheetDetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.todolist.R;
import com.example.todolist.ui.todoSheetList.TodoSheetListFragment;


public class TodoSheetDetailFragment extends Fragment {

    /**
     * TodoSheetDetailFragmentのインスタンス生成メソッド
     * @return インスタンス
     */
    public static TodoSheetDetailFragment newInstance(){
        return new TodoSheetDetailFragment();
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
        // TODO:画面の初期化処理
    }
}
