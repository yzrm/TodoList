package com.example.todolist.ui.todoSheetDetail;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;
import com.example.todolist.data.entities.TodoItem;
import com.example.todolist.ui.todoSheetList.TodoSheetListFragment;

import java.util.ArrayList;
import java.util.List;


public class TodoSheetDetailFragment extends Fragment {

    public interface OnActionListener {
        void getTodoItemListData(int todoSheetId);
    }

    public static final String KEY_TODO_SHEET_ID = "com.example.todolist.key_todo_sheet_id";
    public static final String KEY_TODO_SHEET_TITLE = "com.example.todolist.key_todo_sheet_title";

    private int todoSheetId = -1;
    private String todoSheetTitle = "";
    private RecyclerView recyclerView;
    private OnActionListener listener;

    /**
     * TodoSheetDetailFragmentのインスタンス生成メソッド
     *
     * @return インスタンス
     */
    public static TodoSheetDetailFragment newInstance(int id, String title) {
        Bundle args = new Bundle();
        args.putInt(KEY_TODO_SHEET_ID, id);
        args.putString(KEY_TODO_SHEET_TITLE, title);
        TodoSheetDetailFragment fragment = new TodoSheetDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private TodoSheetDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_todosheetdetail, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (OnActionListener) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //TodoSheetIdとTitleの取得
        if (getArguments() != null) {
            todoSheetId = getArguments().getInt(KEY_TODO_SHEET_ID, -1);
            todoSheetTitle = getArguments().getString(KEY_TODO_SHEET_TITLE, "");
        }

        // SheetTitle用TextViewの取得
        TextView todoSheetTitleTextView = view.findViewById(R.id.todo_sheet_title);
        // タイトルの設定
        todoSheetTitleTextView.setText(todoSheetTitle);
        // RecyclerViewの取得
        recyclerView = view.findViewById(R.id.todo_item_recycler_view);
        // LayoutManagerのセット
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        // Adapterを生成
        TodoItemListAdapter adapter = new TodoItemListAdapter();
        // Adapterをセット
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        // TODO:画面の表示更新
        if (listener != null) {
            //FIXME:
            listener.getTodoItemListData(todoSheetId);
        }
    }

    public int getTodoSheetId() {
        return todoSheetId;
    }
    public void updateTodoItemListData(List<TodoItem> todoItemList){
        if (getActivity() != null){
            getActivity().runOnUiThread(() -> {
                ((TodoItemListAdapter) recyclerView.getAdapter()).updateTodoItemList(todoItemList);
            });
        }
    }


    class TodoItemListAdapter extends RecyclerView.Adapter<TodoItemViewHolder> {
        private List<TodoItem> todoItemList = new ArrayList<>();
        public void updateTodoItemList(List<TodoItem> todoItemList) {
            this.todoItemList = todoItemList;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public TodoItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.layout_todo_list_item, parent, false);
            return new TodoItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TodoItemViewHolder holder, int position) {
            TodoItem itemData = todoItemList.get(position);
            // 項目を設定
            holder.itemDetailText.setText(itemData.detail);
        }

        @Override
        public int getItemCount() {
            return todoItemList.size();
        }
    }

    class TodoItemViewHolder extends RecyclerView.ViewHolder{
        TextView itemDetailText;
        public TodoItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemDetailText = itemView.findViewById(R.id.todo_item_text);

        }
    }
}