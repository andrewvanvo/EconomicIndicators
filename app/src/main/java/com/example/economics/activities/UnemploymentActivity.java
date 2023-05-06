package com.example.economics.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.economics.LoadingDialog;
import com.example.economics.MainActivity;
import com.example.economics.R;
import com.example.economics.services.UnemploymentService;

public class UnemploymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unemployment);

        final LoadingDialog loadingDialog = new LoadingDialog(UnemploymentActivity.this);
        loadingDialog.startLoadingDialog();

        UnemploymentService unemploymentService = new UnemploymentService(UnemploymentActivity.this);
        unemploymentService.getLatestUnemployment(new UnemploymentService.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(UnemploymentActivity.this, "Something Broke", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String monthlyUnemployment) {
                loadingDialog.dismissDialog();
                Toast.makeText(UnemploymentActivity.this, "Unemployment MoM: "+ monthlyUnemployment +"%", Toast.LENGTH_SHORT).show();
            }
        });
    }
}