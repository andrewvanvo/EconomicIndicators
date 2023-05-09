package com.example.economics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
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
import com.example.economics.models.EconIndicatorModel;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;

    ArrayList<EconIndicatorModel> econIndicatorModel = new ArrayList<>();
    int[] econIndicatorImages = {R.drawable.ic_fed_funds_rate, R.drawable.ic_gdp, R.drawable.ic_inflation,
            R.drawable.ic_yields, R.drawable.ic_unemployment, R.drawable.ic_durable};

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                    case R.id.menuHome:{
                        Toast.makeText(MainActivity.this, "Menu Home Selected", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.menuOTHER:{
                        Toast.makeText(MainActivity.this, "Menu Other Selected", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.menuLogout:{
                        Toast.makeText(MainActivity.this, "Menu Logout Selected", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                return false;
            }
        });


        //logout button
        Button logoutButton = findViewById(R.id.logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });

        //main recycler view. Set up recycler adapter after models are set up!
        RecyclerView recyclerView = findViewById(R.id.econRecyclerView);

        setUpEconIndicatorModels();

        Econ_RecylerViewAdapter adapter = new Econ_RecylerViewAdapter(this, econIndicatorModel );
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Drag and drop functionality
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView((recyclerView));

    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void setUpEconIndicatorModels(){
        String[] econIndicatorNames = getResources().getStringArray(R.array.econ_indicators_titles);
        for (int i = 0; i < econIndicatorNames.length; i++){
            econIndicatorModel.add(new EconIndicatorModel(econIndicatorNames[i], econIndicatorImages[i] ));
        }
    }

    //drag and drop
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN |
            ItemTouchHelper.START | ItemTouchHelper.END, 0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();

            Collections.swap(econIndicatorModel, fromPosition, toPosition);
            recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };

}