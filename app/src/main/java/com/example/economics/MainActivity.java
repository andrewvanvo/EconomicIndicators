package com.example.economics;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.federalFundsRateButton);
        button.setOnClickListener(new View.OnClickListener() {
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

    }
}