package com.example.economics.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.economics.LoadingDialog;
import com.example.economics.R;
import com.example.economics.adapters.GDP_CarouselRecyclerViewAdapter;
import com.example.economics.adapters.Inflation_CarouselRecyclerViewAdapter;
import com.example.economics.models.Econ_Carousel_Model;
import com.example.economics.services.InflationService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class InflationActivity extends AppCompatActivity {

    JSONArray dataArray;
    ArrayList<Econ_Carousel_Model> econCarouselModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inflation);

        final LoadingDialog loadingDialog = new LoadingDialog(InflationActivity.this);
        loadingDialog.startLoadingDialog();

        InflationService inflationService = new InflationService(InflationActivity.this);
        inflationService.getLatestCPI(new InflationService.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(InflationActivity.this, "Something Broke", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(JSONArray reversed) {

                dataArray = reversed;

                //Recyclerview setups
                RecyclerView carouselRecyclerView = findViewById(R.id.cpiCarouselRecyclerView);
                setupCarouselModels(dataArray);

                Inflation_CarouselRecyclerViewAdapter adapter = new Inflation_CarouselRecyclerViewAdapter(InflationActivity.this, econCarouselModels);
                carouselRecyclerView.setAdapter(adapter);
                carouselRecyclerView.setLayoutManager(new LinearLayoutManager(InflationActivity.this, LinearLayoutManager.HORIZONTAL, false));

                loadingDialog.dismissDialog();

            }
        });
    }
    private void setupCarouselModels(JSONArray data){
        String[] carouselDescriptors = {"Latest", "Previous", "Change" };
        String[] carouselStrings = new String[3];
        JSONObject latestEconObject;
        JSONObject prevEconObject;
        String latestStat;
        String prevStat;
        Float delta;
        String deltaDisplay;

        try {
            latestEconObject = data.getJSONObject(data.length()-1);
            prevEconObject = data.getJSONObject(data.length()-2);
            latestStat = latestEconObject.getString("value");
            prevStat = prevEconObject.getString("value");
            delta = Float.parseFloat(latestStat) - Float.parseFloat(prevStat);
            deltaDisplay = String.format("%.2f", delta);

            carouselStrings[0] = latestStat;
            carouselStrings[1] = prevStat;
            carouselStrings[2] = deltaDisplay;

        } catch (JSONException e){
            e.printStackTrace();
        }
        for (int i =0; i < carouselStrings.length; i++){
            econCarouselModels.add(new Econ_Carousel_Model(carouselStrings[i],carouselDescriptors[i]));
        }
    }
}