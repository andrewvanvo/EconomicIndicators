package com.example.economics.services;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.economics.singletons.FederalFundsRateSingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FedFundsRateService {

    public static final String FEDERAL_FUNDS_RATE_INTERVAL_MONTHLY_APIKEY_DEMO = "https://www.alphavantage.co/query?function=FEDERAL_FUNDS_RATE&interval=monthly&apikey=demo";
    Context context;
    JSONArray fedFundsArray;

    public FedFundsRateService(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener {
        void onError(String message);
        void onResponse(JSONArray fedFundsArray);
    }

    public void getLatestFedFundsRate(VolleyResponseListener volleyResponseListener){

        String url = FEDERAL_FUNDS_RATE_INTERVAL_MONTHLY_APIKEY_DEMO;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            fedFundsArray = response.getJSONArray("data");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        volleyResponseListener.onResponse(fedFundsArray);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        volleyResponseListener.onError("No FFR Retrieved");
                    }
                });


        FederalFundsRateSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
}
