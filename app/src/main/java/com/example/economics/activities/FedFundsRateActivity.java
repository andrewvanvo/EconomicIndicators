package com.example.economics.activities;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.economics.adapters.recycler_views.FFR_CarouselRecylerViewAdapter;
import com.example.economics.models.Econ_Carousel_Model;

import com.example.economics.adapters.spark_adapters.EconSparkAdapter;
import com.example.economics.LoadingDialog;
import com.example.economics.R;
import com.example.economics.services.FedFundsRateService;
import com.robinhood.spark.SparkView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FedFundsRateActivity extends AppCompatActivity {

    JSONArray dataArray;
    ArrayList<Econ_Carousel_Model> econCarouselModels = new ArrayList<>();

    EconSparkAdapter econSparkAdapter;
    SparkView sparkView;
    TextView sparkStat;
    TextView sparkDate;


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
            public void onResponse(JSONArray reversed) {

                dataArray = reversed;

                //Recyclerview setups
                RecyclerView carouselRecyclerView = findViewById(R.id.ffrCarouselRecyclerView);
                setupCarouselModels(dataArray);

                FFR_CarouselRecylerViewAdapter adapter = new FFR_CarouselRecylerViewAdapter(FedFundsRateActivity.this, econCarouselModels);
                carouselRecyclerView.setAdapter(adapter);
                carouselRecyclerView.setLayoutManager(new LinearLayoutManager(FedFundsRateActivity.this, LinearLayoutManager.HORIZONTAL, false));

                //SparkView Setups
                sparkView = findViewById(R.id.ffrSparkView);
                sparkStat = findViewById(R.id.ffrHistoricalStat);
                sparkDate = findViewById(R.id.ffrHistoricalDate);
                updateSparkViewWithData(dataArray);
                setupEventListeners();
                loadingDialog.dismissDialog();


            }
        });
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
        String[] ffrCarouselDescriptors = {"Latest", "Previous", "Change" };
        String[] ffrCarouselStrings = new String[3];

        JSONObject latestFedFundsObject;
        JSONObject prevFedFundsObject;
        String latestFedFundsRate;
        String prevFedFundsRate;
        Float delta;
        String deltaDisplay;

        try {
            latestFedFundsObject = data.getJSONObject(data.length()-1);
            prevFedFundsObject = data.getJSONObject(data.length()-2);
            latestFedFundsRate = latestFedFundsObject.getString("value");
            prevFedFundsRate = prevFedFundsObject.getString("value");
            delta = Float.parseFloat(latestFedFundsRate) - Float.parseFloat(prevFedFundsRate);
            deltaDisplay = String.format("%.2f", delta);

            ffrCarouselStrings[0] = latestFedFundsRate;
            ffrCarouselStrings[1] = prevFedFundsRate;
            ffrCarouselStrings[2] = deltaDisplay;

        } catch (JSONException e){
            e.printStackTrace();
        }
        for (int i =0; i < ffrCarouselStrings.length; i++){
            econCarouselModels.add(new Econ_Carousel_Model(ffrCarouselStrings[i],ffrCarouselDescriptors[i]));
        }
    }





}