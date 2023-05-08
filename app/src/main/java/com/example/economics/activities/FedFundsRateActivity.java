package com.example.economics.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.economics.FFR_Carousel_RecylerViewAdapter;
import com.example.economics.FedFundsRateCarouselModel;

import com.example.economics.FedFundsSparkAdapter;
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
    ArrayList<FedFundsRateCarouselModel> fedFundsRateCarouselModels = new ArrayList<>();

    FedFundsSparkAdapter fedFundsSparkAdapter;
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
//                JSONObject testobj;
//                try {
//                    testobj = reversed.getJSONObject(dataArray.length()-1);
//                    Log.d("responseRcvd", testobj.toString());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }


                //Recyclerview setups
                RecyclerView carouselRecyclerView = findViewById(R.id.ffrCarouselRecyclerView);
                setupFedFundsRateCarouselModels(dataArray);

                FFR_Carousel_RecylerViewAdapter adapter = new FFR_Carousel_RecylerViewAdapter(FedFundsRateActivity.this, fedFundsRateCarouselModels);
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

        fedFundsSparkAdapter = new FedFundsSparkAdapter(dataArray);
        sparkView.setAdapter(fedFundsSparkAdapter);

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

    private void setupFedFundsRateCarouselModels(JSONArray data){
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
            fedFundsRateCarouselModels.add(new FedFundsRateCarouselModel(ffrCarouselStrings[i],ffrCarouselDescriptors[i]));
        }
    }





}