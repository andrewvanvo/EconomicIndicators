package com.example.economics.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.economics.LoadingDialog;
import com.example.economics.MainActivity;
import com.example.economics.R;
import com.example.economics.services.FedFundsRateService;
import com.example.economics.services.YieldsService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TreasuryYieldsActivity extends AppCompatActivity {
    JSONArray dataArray;
    JSONObject latestYieldsObject;
    String latestYields;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treasury_yields);

        final LoadingDialog loadingDialog = new LoadingDialog(TreasuryYieldsActivity.this);
        loadingDialog.startLoadingDialog();

        YieldsService yieldsService = new YieldsService(TreasuryYieldsActivity.this);
        yieldsService.getLatestYields(new YieldsService.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(TreasuryYieldsActivity.this, "Something Broke", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(JSONArray yieldsArray) {
                dataArray = yieldsArray;
                try {
                    latestYieldsObject = dataArray.getJSONObject(0);
                    latestYields = latestYieldsObject.getString("value");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                loadingDialog.dismissDialog();
                Toast.makeText(TreasuryYieldsActivity.this, "Yields: "+ latestYields + "%", Toast.LENGTH_SHORT).show();
            }
        });

    }
}