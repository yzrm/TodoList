package com.example.todolist.ui.todoSheetList;

import android.content.Context;
import android.location.GnssAntennaInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;
import com.example.todolist.data.entities.TodoSheet;
import com.example.todolist.ui.adapter.TodoSheetListAdapter;

import java.util.ArrayList;
import java.util.List;

public class TodoSheetListFragment extends Fragment implements TodoSheetListAdapter.OnclickTodoSheetListItemListener{

    public interface TodoSheetListActionListener{
        // TodoSheetListデータを取得するためのメソッド
       void getTodoSheetListDate();
       void onClickTodoSheetItem(TodoSheet todoSheet);
    }

    private RecyclerView recyclerView;
    private  TodoSheetListActionListener listener;

    /**
     * TodoSheetListFragmentのインスタンス生成メソッド
     * @return インスタンス
     */
    public static TodoSheetListFragment newInstance(){
        return new TodoSheetListFragment();
    }

    private TodoSheetListFragment(){}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (TodoSheetListActionListener) context;
        } catch (ClassCastException exception){
            exception.printStackTrace();
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_todosheetlist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
            // RecyclerViewの取得
            recyclerView = view.findViewById(R.id.todo_sheet_recycler_view);
            displayTodoSheetList(new ArrayList<>());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (listener != null){
            // リストデータの取得依頼
            listener.getTodoSheetListDate();
        }
    }

    private void displayTodoSheetList(List<TodoSheet> todoSheetList){
            // Adapterの設定
            TodoSheetListAdapter adapter = new TodoSheetListAdapter(todoSheetList, this);
            recyclerView.setAdapter(adapter);

            // LayoutManagerの設定
            GridLayoutManager layoutManager = new GridLayoutManager(
                    requireContext(),
                    2,
                    RecyclerView.VERTICAL,
                    false
            );

            recyclerView.setLayoutManager(layoutManager);
        }
    public void updateTodoSheetListData(List<TodoSheet> todoSheetList){
        //TodoSheetデータ更新処理
        if (getActivity() != null) {
            getActivity().runOnUiThread(() -> {
                ((TodoSheetListAdapter) recyclerView.getAdapter()).updateTodoSheetList(todoSheetList);
            });
        }
    }

    /**
     * リストアイテムクリック処理
     * @param itemData
     */
    @Override
    public void onClickItem(TodoSheet itemData) {
        // MainActivityにタップされたデータを渡して、詳細画面用のFragmentに切り替えてもらう。
        if (listener != null){
            listener.onClickTodoSheetItem(itemData);
        }
    }
}
