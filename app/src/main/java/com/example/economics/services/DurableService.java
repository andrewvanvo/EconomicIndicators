package com.example.economics.services;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.economics.singletons.DurableSingleton;
import com.example.economics.singletons.GDPSingleton;
import com.example.economics.singletons.UnemploymentSingleton;
import com.example.economics.singletons.YieldsSingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DurableService {
    public static final String DURABLE_INTERVAL_MONTHLY_APIKEY_DEMO = "https://www.alphavantage.co/query?function=DURABLES&apikey=demo";
    Context context;
    String latestDurable;

    public DurableService(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener {
        void onError(String message);
        void onResponse(String latestDurable);
    }

    public void getLatestDurable(DurableService.VolleyResponseListener volleyResponseListener){

        String url = DURABLE_INTERVAL_MONTHLY_APIKEY_DEMO;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray durableArray;
                        JSONObject latestDurableObject;
                        latestDurable = null;
                        try {
                            durableArray = response.getJSONArray("data");
                            latestDurableObject = durableArray.getJSONObject(0);
                            latestDurable = latestDurableObject.getString("value");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        volleyResponseListener.onResponse(latestDurable);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        volleyResponseListener.onError("No Yields Retrieved");
                    }
                });


        DurableSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

}


