package com.example.economics.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.economics.FFR_Carousel_RecylerViewAdapter;
import com.example.economics.FedFundsRateCarouselModel;
import com.example.economics.LoadingDialog;
import com.example.economics.MainActivity;
import com.example.economics.R;
import com.example.economics.services.FedFundsRateService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FedFundsRateActivity extends AppCompatActivity {

    JSONArray dataArray;

    ArrayList<FedFundsRateCarouselModel> fedFundsRateCarouselModels = new ArrayList<>();

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

                //Recyclerview setups
                RecyclerView carouselRecyclerView = findViewById(R.id.ffrCarouselRecyclerView);

                setupFedFundsRateCarouselModels(dataArray);

                FFR_Carousel_RecylerViewAdapter adapter = new FFR_Carousel_RecylerViewAdapter(FedFundsRateActivity.this, fedFundsRateCarouselModels);
                carouselRecyclerView.setAdapter(adapter);
                carouselRecyclerView.setLayoutManager(new LinearLayoutManager(FedFundsRateActivity.this));


                loadingDialog.dismissDialog();
//                Toast.makeText(FedFundsRateActivity.this, latestFedFundsRate, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupFedFundsRateCarouselModels(JSONArray data){
        String[] ffrCarouselDescriptors = {"Latest", "Previous", "Change" };
        String[] ffrCarouselStrings = new String[3];

        JSONObject latestFedFundsObject;
        JSONObject prevFedFundsObject;
        String latestFedFundsRate;
        String prevFedFundsRate;
        Float delta;
        String deltaDisplay;

        Log.d("data", data.toString());


        try {
            latestFedFundsObject = data.getJSONObject(0);
            prevFedFundsObject = data.getJSONObject(1);
            latestFedFundsRate = latestFedFundsObject.getString("value");
            prevFedFundsRate = prevFedFundsObject.getString("value");
            delta = Float.parseFloat(latestFedFundsRate) - Float.parseFloat(prevFedFundsRate);
            deltaDisplay = String.format("%.2f", delta);

            ffrCarouselStrings[0] = latestFedFundsRate;
            ffrCarouselStrings[1] = prevFedFundsRate;
            ffrCarouselStrings[2] = deltaDisplay;

            Log.d("stat", ffrCarouselStrings.toString());


        } catch (JSONException e){
            e.printStackTrace();
        }
        for (int i =0; i < ffrCarouselStrings.length; i++){
            fedFundsRateCarouselModels.add(new FedFundsRateCarouselModel(ffrCarouselStrings[i],ffrCarouselDescriptors[i]));
        }
    }

}