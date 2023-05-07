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

    Context context;
    JSONArray CPI_Array;

    public static final String CPI_INTERVAL_MONTHLY_APIKEY_DEMO = "https://www.alphavantage.co/query?function=CPI&interval=monthly&apikey=demo";

    public ConsumerPriceIndexService(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener {
        void onError(String message);
        void onResponse(JSONArray inflationArray);
    }

    public void getLatestCPI(VolleyResponseListener volleyResponseListener){

        String url = CPI_INTERVAL_MONTHLY_APIKEY_DEMO;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            CPI_Array = response.getJSONArray("data");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        volleyResponseListener.onResponse(CPI_Array);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        volleyResponseListener.onError("No inflation Retrieved");
                    }
                });


        YieldsSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
}


