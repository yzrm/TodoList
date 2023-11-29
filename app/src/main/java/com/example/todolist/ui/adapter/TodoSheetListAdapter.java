package com.example.todolist.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;
import com.example.todolist.data.entities.Todo;
import com.example.todolist.data.entities.TodoData;
import com.example.todolist.data.entities.TodoSheet;

import java.util.ArrayList;
import java.util.List;

public class TodoSheetListAdapter extends RecyclerView.Adapter<TodoSheetListAdapter.ViewHolder> {

    private static final String TAG = TodoSheetListAdapter.class.getSimpleName();
    public interface OnclickTodoSheetListItemListener {
        void onClickItem(TodoData itemData);
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView itemTitleText;
        List<LinearLayoutCompat> itemLayoutList = new ArrayList<>();
        List<View> itemCheckViewList = new ArrayList<>();
        List<TextView> itemTitleTextList = new ArrayList<>();

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTitleText = itemView.findViewById(R.id.item_title);
            itemLayoutList.add(itemView.findViewById(R.id.todo_item_layout_1));
            itemLayoutList.add(itemView.findViewById(R.id.todo_item_layout_2));
            itemLayoutList.add(itemView.findViewById(R.id.todo_item_layout_3));
            itemLayoutList.add(itemView.findViewById(R.id.todo_item_layout_4));
            itemLayoutList.add(itemView.findViewById(R.id.todo_item_layout_5));
            itemLayoutList.add(itemView.findViewById(R.id.todo_item_layout_6));
            itemLayoutList.add(itemView.findViewById(R.id.todo_item_layout_7));
            itemLayoutList.add(itemView.findViewById(R.id.todo_item_layout_8));

            //各項目のチェック用のViewを取得
            itemCheckViewList.add(itemView.findViewById(R.id.check_icon_1));
            itemCheckViewList.add(itemView.findViewById(R.id.check_icon_2));
            itemCheckViewList.add(itemView.findViewById(R.id.check_icon_3));
            itemCheckViewList.add(itemView.findViewById(R.id.check_icon_4));
            itemCheckViewList.add(itemView.findViewById(R.id.check_icon_5));
            itemCheckViewList.add(itemView.findViewById(R.id.check_icon_6));
            itemCheckViewList.add(itemView.findViewById(R.id.check_icon_7));
            itemCheckViewList.add(itemView.findViewById(R.id.check_icon_8));

            //各項目のタイトル用のViewを取得
            itemTitleTextList.add(itemView.findViewById(R.id.todo_title_1));
            itemTitleTextList.add(itemView.findViewById(R.id.todo_title_2));
            itemTitleTextList.add(itemView.findViewById(R.id.todo_title_3));
            itemTitleTextList.add(itemView.findViewById(R.id.todo_title_4));
            itemTitleTextList.add(itemView.findViewById(R.id.todo_title_5));
            itemTitleTextList.add(itemView.findViewById(R.id.todo_title_6));
            itemTitleTextList.add(itemView.findViewById(R.id.todo_title_7));
            itemTitleTextList.add(itemView.findViewById(R.id.todo_title_8));

        }

        public void allItemLayoutGone(){
            for (LinearLayoutCompat layout : itemLayoutList) {
                layout.setVisibility(View.GONE);
            }
        }

        public void itemLayoutVisible(List<Todo> todoList){
            for (int i = 0; i < todoList.size(); i++){
                if (i > 7) {
                    Log.w(TAG, "size exceeds maximum value.");
                    return;
                }
                Todo todo = todoList.get(i);
                itemLayoutList.get(i).setVisibility(View.VISIBLE);
                int resId;
                if (todo.getDeleted()) {
                    resId = R.drawable.ic_item_check;
                } else {
                    resId = R.drawable.ic_item_circle;
                }
                itemCheckViewList.get(i).setBackgroundResource(resId);
                itemTitleTextList.get(i).setText(todo.getDetail());

            }
        }
    }

    private List<TodoData> todoDataList;
    private OnclickTodoSheetListItemListener listener;
    public TodoSheetListAdapter(List<TodoData> todoDataList, OnclickTodoSheetListItemListener listener) {
        this.todoDataList = todoDataList;
        this.listener = listener;
    }

    public void updateTodoDataList(List<TodoData> todoDataList){
        this.todoDataList = todoDataList;
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
        TodoData data = todoDataList.get(position);
        // タイトルを設定
        holder.itemTitleText.setText(data.getTitle());
        holder.itemView.setOnClickListener(view -> {
            if (listener != null){
                listener.onClickItem(data);
            }
        });
        // TODO: カラーを設定

        holder.allItemLayoutGone();
        holder.itemLayoutVisible(data.getTodoList());
//        for (Todo todo : data.getTodoList()) {
//            // LayoutInflaterを取得
//            LayoutInflater inflater = LayoutInflater.from(holder.itemView.getContext());
//            View view = inflater.inflate(R.layout.todo_sheet_detail_list_item_layout, null);
//            holder.todoSheetContainer.addView(view);
//       }
    }

    @Override
    public int getItemCount() {
        return todoDataList.size();
    }
}
