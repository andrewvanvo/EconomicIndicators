package com.example.economics.adapters.spark_adapters;

import com.robinhood.spark.SparkAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

public class EconSparkAdapter extends SparkAdapter {

    private final JSONArray data;


    public EconSparkAdapter(JSONArray data) {
        this.data = data;
    }
    @Override
    public int getCount() {
        return data.length();
    }

    @Override
    public Object getItem(int index) {
        Object object = null;
        try {
            object = data.getJSONObject(index);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    @Override
    public float getY(int index) {
        Float yValue = 0.00f;
        try{
            JSONObject entry = data.getJSONObject(index);
            yValue = Float.parseFloat(entry.getString("value"));
        } catch (JSONException e){
            e.printStackTrace();
        }
        return yValue;
    }
}


