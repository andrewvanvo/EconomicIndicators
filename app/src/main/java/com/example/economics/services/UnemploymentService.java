package com.example.economics.services;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.economics.singletons.GDPSingleton;
import com.example.economics.singletons.UnemploymentSingleton;
import com.example.economics.singletons.YieldsSingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UnemploymentService {
    public static final String UNEMPLOYMENT_INTERVAL_MONTHLY_APIKEY_DEMO = "https://www.alphavantage.co/query?function=UNEMPLOYMENT&apikey=demo";
    Context context;
    JSONArray unemploymentArray;

    public UnemploymentService(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener {
        void onError(String message);
        void onResponse(JSONArray unemploymentArray);
    }

    public void getLatestUnemployment(UnemploymentService.VolleyResponseListener volleyResponseListener){

        String url = UNEMPLOYMENT_INTERVAL_MONTHLY_APIKEY_DEMO;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            unemploymentArray = response.getJSONArray("data");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        volleyResponseListener.onResponse(unemploymentArray);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        volleyResponseListener.onError("No unemployment Retrieved");
                    }
                });


        UnemploymentSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

}


