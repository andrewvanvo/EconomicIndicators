package com.example.economics.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.economics.LoadingDialog;
import com.example.economics.MainActivity;
import com.example.economics.R;
import com.example.economics.services.ConsumerPriceIndexService;

public class InflationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inflation);

        final LoadingDialog loadingDialog = new LoadingDialog(InflationActivity.this);
        loadingDialog.startLoadingDialog();

        ConsumerPriceIndexService consumerPriceIndexService = new ConsumerPriceIndexService(InflationActivity.this);
        consumerPriceIndexService.getLatestCPI(new ConsumerPriceIndexService.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(InflationActivity.this, "Something Broke", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String monthlyInflation) {
                loadingDialog.dismissDialog();
                Toast.makeText(InflationActivity.this, "Inflation MoM: "+ monthlyInflation +"%", Toast.LENGTH_SHORT).show();
            }
        });

    }
}