package com.example.todolist.ui.main;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;
import com.example.todolist.data.entities.TodoItem;
import com.example.todolist.data.entities.TodoSheet;
import com.example.todolist.ui.adapter.NavigationItemAdapter;
import com.example.todolist.ui.todoSheetDetail.TodoSheetDetailFragment;
import com.example.todolist.ui.todoSheetList.TodoSheetListFragment;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity
    implements View.OnKeyListener, TodoSheetListFragment.TodoSheetListActionListener, TodoSheetDetailFragment.OnActionListener {

    BottomSheetBehavior mBottomSheetBehavior;
    //TodoSheetListFragment
    //TodoSheetDetailFragment
    private static final String TAG = "MainActivity";

    private MainViewModel mainViewModel;
    private TodoSheetListFragment todoSheetListFragment;
    private TodoSheetDetailFragment todoSheetDetailFragment;
    private FrameLayout mainContainer;
    private EditText listNameEditText;
    private DrawerLayout drawer;

    private RecyclerView navigationRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //バックボタン処理
        registerOnBackPressedCallback();
        //TodoSheetListFragmentのインスタンスを取得
        todoSheetListFragment = TodoSheetListFragment.newInstance();
        //Fragmentの追加
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_container, todoSheetListFragment)
                .commitNow();

        listNameEditText = findViewById(R.id.list_name_edit_text);
        listNameEditText.setOnKeyListener(this);

        navigationRecyclerView = findViewById(R.id.navigation_recycler_view);

        // ViewModelの生成
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        //ボトムシート設定
        bottomSheetSetting();
        // プラスボタン
        plusButtonSetting();
        //チェックボタン
        checkButtonSetting();
        //Drawer設定
        drawerSetting();
        // TODOSheetのデータを取得
        mainViewModel.getTodoSheetAll(todoSheetList ->
                runOnUiThread(() -> displayNavigationList(todoSheetList))
        );
    }

    /**
     * バックボタンの処理
     */
    private void registerOnBackPressedCallback(){
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if(drawer.isDrawerOpen(GravityCompat.START)){
                    // ドロワーメニューが開いている場合閉じる。
                    drawer.closeDrawer(GravityCompat.START);
                } else if(mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HALF_EXPANDED
                        || mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED){
                    //ボトムシートを開いている場合は閉じる
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }else{
                    // それ以外はデフォルトのバック操作
                    this.remove();
                    onBackPressed();
                    getOnBackPressedDispatcher().addCallback(this);
                }
            }
        });
    }
    /**
     * ボトムシート設定
     */
    private void bottomSheetSetting(){
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
    }
    /**
     * plusButton設定
     */
    private void plusButtonSetting(){
        ImageButton todoAddBtn = findViewById(R.id.todo_add_btn);
        todoAddBtn.setOnClickListener(view -> {
            //
            String listItemName = listNameEditText.getText().toString();
            if (listItemName.isEmpty()) {
                // 追加ボタン押下時にリスト名が未入力の場合は警告ダイアログの表示。
                Toast.makeText(this, R.string.input_todo, Toast.LENGTH_SHORT).show();
            } else {
                // 追加ボタン押下で、DBにリスト名追加。
                int todoSheetId = todoSheetDetailFragment.getTodoSheetId();
                mainViewModel.addTodoItem(todoSheetId, listItemName ,() -> {
                    //TodoItemList表示更新
                    mainViewModel.getTodoItemListById(todoSheetId, todoItemList -> {
                        runOnUiThread(() -> {
                            if (todoSheetDetailFragment != null) {
                                todoSheetDetailFragment.updateTodoItemListData(todoItemList);
                            }
                            // EditTextの内容もクリアする
                            listNameEditText.setText("");
                        });
                    });
                });
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Fragment fragment =getSupportFragmentManager().findFragmentById(R.id.main_container);
            //トップビューの場合
            if (fragment instanceof TodoSheetListFragment) {
                listNameEditText.setHint(getString(R.string.list_name));
                todoAddBtn.setVisibility(View.GONE);
                if ( mBottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED ){
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                //詳細表示の場合
            }else if (fragment instanceof TodoSheetDetailFragment){
                listNameEditText.setHint(getString(R.string.todo));
                todoAddBtn.setVisibility(View.VISIBLE);
                todoAddBtn.setColorFilter(Color.GRAY);
                if ( mBottomSheetBehavior.getState() != BottomSheetBehavior.STATE_HALF_EXPANDED ) {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
                }
            }
        });
    }

    /**
     * チェックボタン設定
     */
    private void checkButtonSetting() {
        // チェックボタン
        FloatingActionButton checkBtn = findViewById(R.id.check_btn);
        checkBtn.setOnClickListener(view -> {
            // リスト画面の処理
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
                                if (todoSheetListFragment != null) {
                                    todoSheetListFragment.updateTodoSheetListData(todoSheetList);
                                }
                                ((NavigationItemAdapter) navigationRecyclerView.getAdapter())
                                        .updateTodoSheetList(todoSheetList);
                                //EditTextをクリアする。
                                listNameEditText.setText("");
                            })
                    );
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                });
            }
        });
    }
    /**
     * Drawer設定
     */
    private void drawerSetting(){
        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        //DrawerToggle
        drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.drawer_open,
                R.string.drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void displayNavigationList(List<TodoSheet> todoSheetList){
        //TODO: Adapterの設定
        NavigationItemAdapter adapter = new NavigationItemAdapter(todoSheetList);
        navigationRecyclerView.setAdapter(adapter);

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

    @Override
    public void getTodoSheetListDate() {
        mainViewModel.getTodoSheetAll(todoSheetList -> {
            //Fragmentにデータを渡す処理
            if (todoSheetListFragment != null){
                todoSheetListFragment.updateTodoSheetListData(todoSheetList);
            }
        });
    }

    @Override
    public void onClickTodoSheetItem(TodoSheet todoSheet) {
        // TODO: TodoSheetデータを渡して遷移先で表示できるようにする。
        todoSheetDetailFragment = TodoSheetDetailFragment.newInstance(todoSheet.id, todoSheet.title);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, todoSheetDetailFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void getTodoItemListData(int todoSheetId) {
        mainViewModel.getTodoItemListById(todoSheetId, todoItemList -> {
            // Fragmentにデータを渡して表示更新してもらう
            if (todoSheetDetailFragment != null){
                todoSheetDetailFragment.updateTodoItemListData(todoItemList);
            }
        });
    }
}
