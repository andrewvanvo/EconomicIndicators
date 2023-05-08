package com.example.economics.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.economics.LoadingDialog;
import com.example.economics.R;
import com.example.economics.adapters.GDP_CarouselRecyclerViewAdapter;
import com.example.economics.models.Econ_Carousel_Model;
import com.example.economics.services.GDPService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GdpActivity extends AppCompatActivity {
    JSONArray dataArray;
    ArrayList<Econ_Carousel_Model> econCarouselModels = new ArrayList<>();

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
            public void onResponse(JSONArray reversed) {
                dataArray = reversed;

                //Recyclerview setups
                RecyclerView carouselRecyclerView = findViewById(R.id.gdpCarouselRecyclerView);
                setupCarouselModels(dataArray);

                GDP_CarouselRecyclerViewAdapter adapter = new GDP_CarouselRecyclerViewAdapter(GdpActivity.this, econCarouselModels);
                carouselRecyclerView.setAdapter(adapter);
                carouselRecyclerView.setLayoutManager(new LinearLayoutManager(GdpActivity.this, LinearLayoutManager.HORIZONTAL, false));

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
