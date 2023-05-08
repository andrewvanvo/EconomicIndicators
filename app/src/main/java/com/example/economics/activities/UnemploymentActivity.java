package com.example.economics.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.economics.LoadingDialog;
import com.example.economics.R;
import com.example.economics.adapters.recycler_views.Unemployment_CarouselRecyclerViewAdapter;
import com.example.economics.adapters.spark_adapters.EconSparkAdapter;
import com.example.economics.models.Econ_Carousel_Model;
import com.example.economics.services.UnemploymentService;
import com.robinhood.spark.SparkView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UnemploymentActivity extends AppCompatActivity {

    JSONArray dataArray;
    ArrayList<Econ_Carousel_Model> econCarouselModels = new ArrayList<>();

    EconSparkAdapter econSparkAdapter;
    SparkView sparkView;
    TextView sparkStat;
    TextView sparkDate;

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

                //Recyclerview setups
                RecyclerView carouselRecyclerView = findViewById(R.id.unemploymentCarouselRecyclerView);
                setupCarouselModels(dataArray);

                Unemployment_CarouselRecyclerViewAdapter adapter = new Unemployment_CarouselRecyclerViewAdapter(UnemploymentActivity.this, econCarouselModels);
                carouselRecyclerView.setAdapter(adapter);
                carouselRecyclerView.setLayoutManager(new LinearLayoutManager(UnemploymentActivity.this, LinearLayoutManager.HORIZONTAL, false));

                //SparkView Setups
                sparkView = findViewById(R.id.unemploymentSparkView);
                sparkStat = findViewById(R.id.unemploymentHistoricalStat);
                sparkDate = findViewById(R.id.unemploymentHistoricalDate);
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