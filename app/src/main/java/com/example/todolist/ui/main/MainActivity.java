package com.example.todolist.ui.main;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todolist.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener, View.OnKeyListener {

    BottomSheetBehavior mBottomSheetBehavior;

    private static final String TAG = "MainActivity";

    private MainViewModel mainViewModel;

    private EditText listNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listNameEditText = findViewById(R.id.list_name_edit_text);
        listNameEditText.setOnKeyListener(this);

        // ViewModelの生成
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        // プラスボタン
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {

            //bottomSheet
            View bottomView = findViewById(R.id.bottom_sheet_layout);
            mBottomSheetBehavior = BottomSheetBehavior.from(bottomView);

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
                mainViewModel.addNewTodoSheet(listName);
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
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

        //NavigationView Listener
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if (R.id.menu_item1 == item.getItemId()) {
            Log.d(TAG, "menu_item1 Selected!");
        } else if (R.id.menu_item2 == item.getItemId()) {
            Log.d(TAG, "menu_item2 Selected!");
        } else if (R.id.menu_item3 == item.getItemId()) {
            Log.d(TAG, "menu_item3 Selected!");
        } else if (R.id.menu_item4 == item.getItemId()) {
            Log.d(TAG, "menu_item4 Selected!");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Enterkeyでキーボードが消える処理
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
    // Enterkeyが押されたかを判定。
        if (event.getAction() == KeyEvent.ACTION_DOWN
                && keyCode == KeyEvent.KEYCODE_ENTER){
            //ソフトキーボードを閉じる
            InputMethodManager inputMethodManager =
                    (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            return false;
        }
        return false;
    }
}
