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

public class InflationService {

    Context context;
    JSONArray CPI_Array;
    JSONArray sizedDown = new JSONArray();
    JSONArray reversed = new JSONArray();

    public static final String CPI_INTERVAL_MONTHLY_APIKEY_DEMO = "https://www.alphavantage.co/query?function=CPI&interval=monthly&apikey=demo";

    public InflationService(Context context) {
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
                            for (int i = 0; i <20 ; i++){
                                sizedDown.put(CPI_Array.get(i));
                            }

                            for (int i = sizedDown.length()-1;i >= 0; i-- ){
                                reversed.put(sizedDown.get(i));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        volleyResponseListener.onResponse(reversed);
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


