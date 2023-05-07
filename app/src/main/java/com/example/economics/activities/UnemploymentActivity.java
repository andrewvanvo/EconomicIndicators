package com.example.economics.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.economics.LoadingDialog;
import com.example.economics.MainActivity;
import com.example.economics.R;
import com.example.economics.services.UnemploymentService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UnemploymentActivity extends AppCompatActivity {

    JSONArray dataArray;
    JSONObject latestUnemploymentObject;
    String latestUnemployment;

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
            public void onResponse(JSONArray unemploymentArray) {
                dataArray = unemploymentArray;

                try {
                    latestUnemploymentObject = unemploymentArray.getJSONObject(0);
                    latestUnemployment = latestUnemploymentObject.getString("value");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                loadingDialog.dismissDialog();
                Toast.makeText(UnemploymentActivity.this, "Unemployment MoM: "+ latestUnemployment +"%", Toast.LENGTH_SHORT).show();
            }
        });
    }
}