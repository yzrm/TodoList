package com.example.todolist.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;
import com.example.todolist.data.entities.TodoSheet;

import java.util.List;

public class TodoSheetListAdapter extends RecyclerView.Adapter<TodoSheetListAdapter.ViewHolder> {


    public interface OnclickTodoSheetListItemListener {
        void onClickItem(TodoSheet itemData);
    }
    class ViewHolder extends RecyclerView.ViewHolder{

        TextView itemTitleText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTitleText = itemView.findViewById(R.id.item_title);
        }
    }

    private List<TodoSheet> todoSheetList;
    private OnclickTodoSheetListItemListener listener;
    public TodoSheetListAdapter(List<TodoSheet> todoSheetList, OnclickTodoSheetListItemListener listener) {
        this.todoSheetList = todoSheetList;
        this.listener = listener;
    }

    public void updateTodoSheetList(List<TodoSheet> todoSheetList){
        this.todoSheetList = todoSheetList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.todo_sheet_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoSheetListAdapter.ViewHolder holder, int position) {
        TodoSheet data = todoSheetList.get(position);
        // タイトルを設定
        holder.itemTitleText.setText(data.title);
        holder.itemView.setOnClickListener(view -> {
            if (listener != null){
                listener.onClickItem(data);
            }
        });
        // TODO: カラーを設定
    }

    @Override
    public int getItemCount() {
        return todoSheetList.size();
    }
}
