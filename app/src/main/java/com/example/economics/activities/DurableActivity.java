package com.example.economics.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.economics.LoadingDialog;
import com.example.economics.MainActivity;
import com.example.economics.R;
import com.example.economics.services.DurableService;

public class DurableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_durable);

        final LoadingDialog loadingDialog = new LoadingDialog(DurableActivity.this);
        loadingDialog.startLoadingDialog();

        DurableService durableService = new DurableService(DurableActivity.this);
        durableService.getLatestDurable(new DurableService.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(DurableActivity.this, "Something Broke", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String monthlyDurable) {
                loadingDialog.dismissDialog();
                Toast.makeText(DurableActivity.this, "Durable MoM: $"+ monthlyDurable + "m", Toast.LENGTH_SHORT).show();
            }
        });

    }
}