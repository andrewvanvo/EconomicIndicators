package com.example.economics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.economics.adapters.Econ_RecylerViewAdapter;
import com.example.economics.models.EconIndicatorModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<EconIndicatorModel> econIndicatorModel = new ArrayList<>();
    int[] econIndicatorImages = {R.drawable.ic_fed_funds_rate, R.drawable.ic_gdp, R.drawable.ic_inflation,
            R.drawable.ic_yields, R.drawable.ic_unemployment, R.drawable.ic_durable};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //main recycler view. Set up recycler adapter after models are set up!
        RecyclerView recyclerView = findViewById(R.id.econRecyclerView);

        setUpEconIndicatorModels();

        Econ_RecylerViewAdapter adapter = new Econ_RecylerViewAdapter(this, econIndicatorModel );
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }
    private void setUpEconIndicatorModels(){
        String[] econIndicatorNames = getResources().getStringArray(R.array.econ_indicators_titles);
        for (int i = 0; i < econIndicatorNames.length; i++){
            econIndicatorModel.add(new EconIndicatorModel(econIndicatorNames[i], econIndicatorImages[i] ));
        }
    }

}