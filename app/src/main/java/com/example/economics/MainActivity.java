package com.example.economics;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button federalFundsRateButton;
    Button treasuryYieldButton;
    Button consumerPriceIndexButton;

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

    }
}