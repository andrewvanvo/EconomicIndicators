package com.example.economics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.economics.services.ConsumerPriceIndexService;
import com.example.economics.services.DurableService;
import com.example.economics.services.FedFundsRateService;
import com.example.economics.services.GDPService;
import com.example.economics.services.UnemploymentService;
import com.example.economics.services.YieldsService;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button federalFundsRateButton;
    Button treasuryYieldButton;
    Button consumerPriceIndexButton;
    Button gdpButton;
    Button unemploymentButton;
    Button durableButton;

    ArrayList<EconIndicatorModel> econIndicatorModel = new ArrayList<>();
    int[] econIndicatorImages = {R.drawable.ic_fed_funds_rate, R.drawable.ic_gdp, R.drawable.ic_inflation,
            R.drawable.ic_yields, R.drawable.ic_unemployment, R.drawable.ic_durable};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //recycler view. Set up recycler adapter after models are set up!
        RecyclerView recyclerView = findViewById(R.id.econRecyclerView);
        setUpEconIndicatorModels();
        Econ_RecylerViewAdapter adapter = new Econ_RecylerViewAdapter(this, econIndicatorModel );
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        federalFundsRateButton = findViewById(R.id.federalFundsRateButton);
        federalFundsRateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FedFundsRateService fedFundsRateService = new FedFundsRateService(MainActivity.this);
                fedFundsRateService.getLatestFedFundsRate(new FedFundsRateService.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "Something Broke", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String latestFedFundsRate) {
                        Toast.makeText(MainActivity.this, "FFR: "+ latestFedFundsRate + "%", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        treasuryYieldButton = findViewById(R.id.yieldButton);
        treasuryYieldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YieldsService yieldsService = new YieldsService(MainActivity.this);
                yieldsService.getLatestYields(new FedFundsRateService.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "Something Broke", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String latestYields) {
                        Toast.makeText(MainActivity.this, "Yields: "+ latestYields + "%", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        consumerPriceIndexButton = findViewById(R.id.cpiButton);
        consumerPriceIndexButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConsumerPriceIndexService consumerPriceIndexService = new ConsumerPriceIndexService(MainActivity.this);
                consumerPriceIndexService.getLatestCPI(new ConsumerPriceIndexService.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "Something Broke", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String monthlyInflation) {
                        Toast.makeText(MainActivity.this, "Inflation MoM: "+ monthlyInflation +"%", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        gdpButton = findViewById(R.id.gdpButton);
        gdpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GDPService gdpService = new GDPService(MainActivity.this);
                gdpService.getLatestGDP(new GDPService.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "Something Broke", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String quarterlyGDP) {
                        Toast.makeText(MainActivity.this, "GDP QoQ: $"+ quarterlyGDP +"bn", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        unemploymentButton = findViewById(R.id.unemploymentButton);
        unemploymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UnemploymentService unemploymentService = new UnemploymentService(MainActivity.this);
                unemploymentService.getLatestUnemployment(new UnemploymentService.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "Something Broke", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String monthlyUnemployment) {
                        Toast.makeText(MainActivity.this, "Unemployment MoM: "+ monthlyUnemployment +"%", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        durableButton = findViewById(R.id.durableButton);
        durableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DurableService durableService = new DurableService(MainActivity.this);
                durableService.getLatestDurable(new DurableService.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "Something Broke", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String monthlyDurable) {
                        Toast.makeText(MainActivity.this, "Durable MoM: $"+ monthlyDurable + "m", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }
    private void setUpEconIndicatorModels(){
        String[] econIndicatorNames = getResources().getStringArray(R.array.econ_indicators_titles);
        for (int i = 0; i < econIndicatorNames.length; i++){
            econIndicatorModel.add(new EconIndicatorModel(econIndicatorNames[i], econIndicatorImages[i] ));
        }
    }

}