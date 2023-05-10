package com.example.economics.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.economics.LoadingDialog;
import com.example.economics.R;
import com.example.economics.adapters.recycler_views.Inflation_CarouselRecyclerViewAdapter;
import com.example.economics.adapters.spark_adapters.EconSparkAdapter;
import com.example.economics.models.Econ_Carousel_Model;
import com.example.economics.services.InflationService;
import com.robinhood.spark.SparkView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class InflationActivity extends AppCompatActivity {

    JSONArray dataArray;
    ArrayList<Econ_Carousel_Model> econCarouselModels = new ArrayList<>();

    EconSparkAdapter econSparkAdapter;
    SparkView sparkView;
    TextView sparkStat;
    TextView sparkDate;

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

                //SparkView Setups
                sparkView = findViewById(R.id.cpiSparkView);
                sparkStat = findViewById(R.id.cpiHistoricalStat);
                sparkDate = findViewById(R.id.cpiHistoricalDate);
                updateSparkViewWithData(dataArray);
                setupEventListeners();


                loadingDialog.dismissDialog();

            }
        });

        //app bar back button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    // enable the back button function
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupEventListeners() {

        //user scrubbing
        sparkView.setScrubEnabled(true);
        sparkView.setScrubListener(new SparkView.OnScrubListener(){
            @Override
            public void onScrubbed(Object value){
                if(value == null){
                    sparkStat.setText("--");
                    sparkDate.setText("--");
                } else{
                    updateScrubBoxes((JSONObject) value);
                }
            }
        });

    }

    private void updateSparkViewWithData(JSONArray dataArray) {

        econSparkAdapter = new EconSparkAdapter(dataArray);
        sparkView.setAdapter(econSparkAdapter);

        JSONObject latestFedFundsObject;
        try {
            latestFedFundsObject = dataArray.getJSONObject(dataArray.length()-1);
            updateScrubBoxes(latestFedFundsObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateScrubBoxes(JSONObject dataObject) {
        //change boxes when adapter property is returned (start with latest info)
        JSONObject item = dataObject;
        try {
            sparkStat.setText(item.getString("value"));
            sparkDate.setText(item.getString("date"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
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