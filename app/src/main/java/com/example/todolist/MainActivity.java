package com.example.todolist;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;


public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

    static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //fab
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Hallow Snackbar", Snackbar.LENGTH_SHORT)
                        .setAction("Action",null).show();
                Log.d(TAG, "fab Selected!");
            }
        });

    //Toolbar
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.action_settings == item.getItemId()) {
            Log.d(TAG, "Setting Selected!");
        }
        return  true;
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
