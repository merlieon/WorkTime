package com.example.worktime;
import android.os.Bundle;
import com.example.worktime.fragments.CalendarFragment;
import com.example.worktime.fragments.DiagramFragment;
import com.example.worktime.fragments.EarnedFragment;
import com.example.worktime.fragments.HomeFragment;
import com.example.worktime.fragments.SettingsFragment;
import com.example.worktime.fragments.WorkplacesFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.MenuItem;
import android.view.View;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {
    //Contribute to
    //<a href="https://iconscout.com/icons/calender" target="_blank">Calendar Icon</a> on <a href="https://iconscout.com">Iconscout</a>
    //<a href="https://iconscout.com/icons/work" target="_blank">Work Icon</a> by <a href="https://iconscout.com/contributors/google-inc">Google Inc.</a> on <a href="https://iconscout.com">Iconscout</a>
    //<a href="https://iconscout.com/icons/home" target="_blank">Home Icon</a> by <a href="https://iconscout.com/contributors/google-inc" target="_blank">Google Inc.</a>
    //<a href="https://iconscout.com/icons/settings" target="_blank">Settings Icon</a> by <a href="https://iconscout.com/contributors/google-inc" target="_blank">Google Inc.</a>
    //<a href="https://iconscout.com/icons/list" target="_blank">List Icon</a> on <a href="https://iconscout.com">Iconscout</a>
    //<a href="https://iconscout.com/icons/graph" target="_blank">Graph Icon</a> by <a href="https://iconscout.com/contributors/vector-stall" target="_blank">Vector Stall</a>
    //<a href="https://iconscout.com/icons/dollar" target="_blank">Dollar Icon</a> by <a href="https://iconscout.com/contributors/cosmin-negoita" target="_blank">Cosmin Negoita</a>

    DrawerLayout drawer;
    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.mainToolbar);
        drawer = findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                bottomNavigationView.setVisibility(View.VISIBLE);
                break;
            case R.id.nav_calendar:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CalendarFragment()).commit();
                bottomNavigationView.setVisibility(View.GONE);
                break;
            case R.id.nav_workplaces:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new WorkplacesFragment()).commit();
                bottomNavigationView.setVisibility(View.GONE);
                break;
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingsFragment()).commit();
                bottomNavigationView.setVisibility(View.GONE);
                break;
            case R.id.bottom_nav_list:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                break;
            case R.id.bottom_nav_graph:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DiagramFragment()).commit();
                break;
            case R.id.bottom_nav_dollar:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EarnedFragment()).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}
