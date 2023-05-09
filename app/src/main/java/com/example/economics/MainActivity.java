package com.example.economics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.economics.adapters.recycler_views.Econ_RecylerViewAdapter;
import com.example.economics.models.EconIndicatorModel;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    ArrayList<EconIndicatorModel> econIndicatorModel = new ArrayList<>();
    int[] econIndicatorImages = {R.drawable.ic_fed_funds_rate, R.drawable.ic_gdp, R.drawable.ic_inflation,
            R.drawable.ic_yields, R.drawable.ic_unemployment, R.drawable.ic_durable};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        //Drag and drop
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView((recyclerView));

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