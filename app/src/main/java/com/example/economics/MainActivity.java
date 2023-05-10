package com.example.economics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.economics.adapters.recycler_views.Econ_RecylerViewAdapter;
import com.example.economics.databinding.ActivityMainBinding;
import com.example.economics.fragments.centerFragment;
import com.example.economics.fragments.leftFragment;
import com.example.economics.fragments.rightFragment;
import com.example.economics.models.EconIndicatorModel;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    //drawer variables
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;

    //Drawer
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //bottom nav setup
    ActivityMainBinding binding;

    //MAIN ONCREATE FUNCTION
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //bottom nav
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        //setContentView(R.layout.activity_main);
        setContentView(binding.getRoot());
        replaceFragment(new leftFragment());

        binding.bottomNavBar.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.bottomNav1:
                    replaceFragment(new leftFragment());
                    break;

                case R.id.bottomNav2:
                    replaceFragment(new centerFragment());
                    break;

                case R.id.bottomNav3:
                    replaceFragment((new rightFragment()));
                    break;
            }
            return true;
        });


        //drawer
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.menuLogout:{
                        AuthUI.getInstance()
                                .signOut(MainActivity.this)

                                //redirecting to login page where our login flow is being displayed.
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(MainActivity.this, "User Signed Out", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(MainActivity.this, LoginActivity.class);
                                        startActivity(i);
                                    }
                                });
                        break;
                    }
                }
                return false;
            }
        });
    }


    //fragment replace
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();

    }

    //drawer
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}