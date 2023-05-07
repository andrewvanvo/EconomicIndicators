package com.example.economics.activities;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.economics.LoadingDialog;
import com.example.economics.MainActivity;
import com.example.economics.R;
import com.example.economics.services.FedFundsRateService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FedFundsRateActivity extends AppCompatActivity {

    JSONArray dataArray;
    JSONObject latestFedFundsObject;
    String latestFedFundsRate;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fed_funds_rate);

        final LoadingDialog loadingDialog = new LoadingDialog(FedFundsRateActivity.this);
        loadingDialog.startLoadingDialog();

        FedFundsRateService fedFundsRateService = new FedFundsRateService(FedFundsRateActivity.this);
        fedFundsRateService.getLatestFedFundsRate(new FedFundsRateService.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(FedFundsRateActivity.this, "Something Broke", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(JSONArray fedFundsArray) {
                dataArray = fedFundsArray;
                try {
                    latestFedFundsObject = dataArray.getJSONObject(0);
                    latestFedFundsRate = latestFedFundsObject.getString("value");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                loadingDialog.dismissDialog();
                Toast.makeText(FedFundsRateActivity.this, latestFedFundsRate, Toast.LENGTH_SHORT).show();
            }
        });

    }
}