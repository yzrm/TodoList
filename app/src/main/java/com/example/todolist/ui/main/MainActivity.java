package com.example.todolist.ui.main;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.todolist.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

    BottomSheetBehavior mBottomSheetBehavior;

    private static final String TAG = "MainActivity";

    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ViewModelの生成
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        //fab
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Log.d(TAG, "fab Selected!");

            //bottomSheet
            View bottomView = findViewById(R.id.bottom_sheet_layout);
            mBottomSheetBehavior = BottomSheetBehavior.from(bottomView);

            if ( mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED ){
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }else{
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
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
    protected void onResume(){
        super.onResume();
        mainViewModel.addNewTodoSheet("サンプル1");
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

}
