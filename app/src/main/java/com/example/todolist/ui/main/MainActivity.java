package com.example.todolist.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todolist.R;
import com.example.todolist.data.entities.TodoSheet;
import com.example.todolist.ui.adapter.TodoSheetListAdapter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity
    implements View.OnKeyListener {

    BottomSheetBehavior mBottomSheetBehavior;

    private static final String TAG = "MainActivity";

    private MainViewModel mainViewModel;

    private EditText listNameEditText;
    private RecyclerView recyclerView;
    private RecyclerView navigationRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listNameEditText = findViewById(R.id.list_name_edit_text);
        listNameEditText.setOnKeyListener(this);

        // RecyclerViewの取得
        recyclerView = findViewById(R.id.todo_sheet_recycler_view);
        navigationRecyclerView = findViewById(R.id.navigation_recycler_view);

        // ViewModelの生成
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        //bottomSheet
        View bottomView = findViewById(R.id.bottom_sheet_layout);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomView);
        mBottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if(newState == BottomSheetBehavior.STATE_HIDDEN) {
                    closeKeyboard(bottomSheet);
                }
            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                //nothing to do.
            }
        });

        // プラスボタン
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {

            if ( mBottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED ){
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        // チェックボタン
        FloatingActionButton checkBtn = findViewById(R.id.check_btn);
        checkBtn.setOnClickListener(view -> {
            String listName = listNameEditText.getText().toString();
            if (listName.isEmpty()) {
                // チェックボタン押下時にリスト名が未入力の場合は警告ダイアログの表示。
                Toast.makeText(this, R.string.input_list_name, Toast.LENGTH_SHORT).show();
            } else {
                // チェックボタンを押下で、DBにリスト名追加。
                mainViewModel.addNewTodoSheet(listName, () -> {
                    //  TODOSheet表示更新
                    mainViewModel.getTodoSheetAll(todoSheetList ->
                            runOnUiThread(() -> {
                                ((TodoSheetListAdapter) recyclerView.getAdapter())
                                        .updateTodoSheetList(todoSheetList);
                            })
                    );
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                });
            }
        });

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        //DrawerToggle
        DrawerLayout drawer =
            (DrawerLayout) findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.drawer_open,
                R.string.drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // TODOSheetのデータを取得
        mainViewModel.getTodoSheetAll(todoSheetList ->
                runOnUiThread(() -> {
                    displayTodoSheetList(todoSheetList);
                    displayNavigationList(todoSheetList);
                })
        );
    }

    private void displayTodoSheetList(List<TodoSheet> todoSheetList){
        // Adapterの設定
        TodoSheetListAdapter adapter = new TodoSheetListAdapter(todoSheetList);
        recyclerView.setAdapter(adapter);

        // LayoutManagerの設定
        GridLayoutManager layoutManager = new GridLayoutManager(
                this,
                2,
                RecyclerView.VERTICAL,
                false
        );

        recyclerView.setLayoutManager(layoutManager);
    }

    private void displayNavigationList(List<TodoSheet> todoSheetList){
        //TODO: Adapterの設定

        //LayoutManagerの設定
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        navigationRecyclerView.setLayoutManager(layoutManager);
    }

    //Enterkeyでキーボードが消える処理
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
    // Enterkeyが押されたかを判定。
        if (event.getAction() == KeyEvent.ACTION_DOWN
                && keyCode == KeyEvent.KEYCODE_ENTER){
            //キーボードを閉じる処理
            closeKeyboard(v);
            return false;
        }
        return false;
    }

    /**
     * キーボードを閉じるメソッド
     * @param view
     */
    public void closeKeyboard(View view){
        //ソフトキーボードを閉じる
        InputMethodManager inputMethodManager =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
