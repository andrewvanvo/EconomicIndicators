package com.example.economics.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.economics.LoadingDialog;
import com.example.economics.MainActivity;
import com.example.economics.R;
import com.example.economics.services.GDPService;

public class GdpActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gdp);

        final LoadingDialog loadingDialog = new LoadingDialog(GdpActivity.this);
        loadingDialog.startLoadingDialog();

        GDPService gdpService = new GDPService(GdpActivity.this);
        gdpService.getLatestGDP(new GDPService.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(GdpActivity.this, "Something Broke", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onResponse(String quarterlyGDP) {
                loadingDialog.dismissDialog();
                Toast.makeText(GdpActivity.this, "GDP QoQ: $"+ quarterlyGDP +"bn", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
