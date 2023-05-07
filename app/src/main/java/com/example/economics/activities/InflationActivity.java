package com.example.economics.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.economics.LoadingDialog;
import com.example.economics.MainActivity;
import com.example.economics.R;
import com.example.economics.services.ConsumerPriceIndexService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InflationActivity extends AppCompatActivity {

    JSONArray dataArray;
    JSONObject latest_CPI_Object;
    JSONObject previous_CPI_Object;
    Float latestCPI;
    Float previousCPI;
    String monthlyInflation;

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
            public void onResponse(JSONArray CPI_Array) {

                dataArray = CPI_Array;
                try {
                    latest_CPI_Object = CPI_Array.getJSONObject(0);
                    previous_CPI_Object = CPI_Array.getJSONObject(1);
                    latestCPI = Float.parseFloat(latest_CPI_Object.getString("value"));
                    previousCPI = Float.parseFloat(previous_CPI_Object.getString("value"));
                    Float calculated = ((latestCPI-previousCPI)/previousCPI)*100;
                    //monthlyInflation = Float.toString(calculated);
                    monthlyInflation = String.format("%.1f", calculated);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                loadingDialog.dismissDialog();
                Toast.makeText(InflationActivity.this, "Inflation MoM: "+ monthlyInflation +"%", Toast.LENGTH_SHORT).show();
            }
        });

    }
}