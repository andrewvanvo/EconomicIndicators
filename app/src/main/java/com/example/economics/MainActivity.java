package com.example.economics;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.economics.services.ConsumerPriceIndexService;
import com.example.economics.services.FedFundsRateService;
import com.example.economics.services.GDPService;
import com.example.economics.services.UnemploymentService;
import com.example.economics.services.YieldsService;

public class MainActivity extends AppCompatActivity {

    Button federalFundsRateButton;
    Button treasuryYieldButton;
    Button consumerPriceIndexButton;
    Button gdpButton;
    Button unemploymentButton;
    Button durableButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    }
}