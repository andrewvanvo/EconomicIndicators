package com.example.economics.services;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.economics.singletons.YieldsSingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ConsumerPriceIndexService {

    public static final String CPI_INTERVAL_MONTHLY_APIKEY_DEMO = "https://www.alphavantage.co/query?function=CPI&interval=monthly&apikey=demo";
    Context context;
    String monthlyInflation;

    public ConsumerPriceIndexService(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener {
        void onError(String message);
        void onResponse(String latestCPI);
    }

    public void getLatestCPI(VolleyResponseListener volleyResponseListener){

        String url = CPI_INTERVAL_MONTHLY_APIKEY_DEMO;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray CPI_Array;
                        JSONObject latest_CPI_Object;
                        JSONObject previous_CPI_Object;
                        Float latestCPI;
                        Float previousCPI;
                        try {
                            CPI_Array = response.getJSONArray("data");
                            latest_CPI_Object = CPI_Array.getJSONObject(0);
                            previous_CPI_Object = CPI_Array.getJSONObject(1);
                            latestCPI = Float.parseFloat(latest_CPI_Object.getString("value"));
                            previousCPI = Float.parseFloat(previous_CPI_Object.getString("value"));
                            Float calculated = ((latestCPI-previousCPI)/previousCPI)*100;

                            //monthlyInflation = Float.toString(calculated);
                            monthlyInflation = String.format("%.1f", calculated);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        volleyResponseListener.onResponse(monthlyInflation);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        volleyResponseListener.onError("No FFR Retrieved");
                    }
                });


        YieldsSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
}


