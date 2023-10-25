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

public class NavigationItemAdapter extends RecyclerView.Adapter<NavigationItemAdapter.ViewHolder>{


    class ViewHolder extends RecyclerView.ViewHolder{

        TextView itemTitleText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTitleText = itemView.findViewById(R.id.item_title);
        }
    }
    private List<TodoSheet> todoSheetList;
    public NavigationItemAdapter(List<TodoSheet> todoSheetList) {
        this.todoSheetList = todoSheetList;
    }

    public void updateTodoSheetList(List<TodoSheet> todoSheetList){
        this.todoSheetList = todoSheetList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.navigation_item_layout, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NavigationItemAdapter.ViewHolder holder, int position) {
    TodoSheet data =todoSheetList.get(position);
        //タイトルを設定
        holder.itemTitleText.setText(data.title);
        //TODO:カラー設定
    }

    @Override
    public int getItemCount() {
        return todoSheetList.size();
    }
}
