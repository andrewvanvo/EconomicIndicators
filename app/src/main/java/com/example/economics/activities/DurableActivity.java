package com.example.economics.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.economics.LoadingDialog;
import com.example.economics.MainActivity;
import com.example.economics.R;
import com.example.economics.services.DurableService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DurableActivity extends AppCompatActivity {

    JSONArray dataArray;
    JSONObject latestDurableObject;
    String latestDurable;




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
            public void onResponse(JSONArray durableArray) {
                dataArray = durableArray;

                try {
                    latestDurableObject = durableArray.getJSONObject(0);
                    latestDurable = latestDurableObject.getString("value");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                loadingDialog.dismissDialog();
                Toast.makeText(DurableActivity.this, "Durable MoM: $"+ latestDurable + "m", Toast.LENGTH_SHORT).show();
            }
        });

    }
}