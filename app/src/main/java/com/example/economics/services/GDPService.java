package com.example.economics.services;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.economics.singletons.GDPSingleton;
import com.example.economics.singletons.YieldsSingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GDPService {
    public static final String GDP_INTERVAL_QUARTERLY_APIKEY_DEMO = "https://www.alphavantage.co/query?function=REAL_GDP&interval=annual&apikey=demo";
    Context context;
    String latestGDP;

    public GDPService(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener {
        void onError(String message);
        void onResponse(String latestGDP);
    }

    public void getLatestGDP(GDPService.VolleyResponseListener volleyResponseListener){

        String url = GDP_INTERVAL_QUARTERLY_APIKEY_DEMO;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray gdpArray;
                        JSONObject latestGdpObject;
                        latestGDP = null;
                        try {
                            gdpArray = response.getJSONArray("data");
                            latestGdpObject = gdpArray.getJSONObject(0);
                            latestGDP = latestGdpObject.getString("value");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        volleyResponseListener.onResponse(latestGDP);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        volleyResponseListener.onError("No Yields Retrieved");
                    }
                });


        GDPSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

}

