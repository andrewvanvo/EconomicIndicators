package com.example.economics.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.economics.LoadingDialog;
import com.example.economics.MainActivity;
import com.example.economics.R;
import com.example.economics.services.GDPService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GdpActivity extends AppCompatActivity {
    JSONArray dataArray;
    JSONObject latestGdpObject;
    String latestGDP;
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
            public void onResponse(JSONArray gdpArray) {
                dataArray = gdpArray;
                try {
                    latestGdpObject = gdpArray.getJSONObject(0);
                    latestGDP = latestGdpObject.getString("value");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                loadingDialog.dismissDialog();
                Toast.makeText(GdpActivity.this, "GDP QoQ: $"+ latestGDP +"bn", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
