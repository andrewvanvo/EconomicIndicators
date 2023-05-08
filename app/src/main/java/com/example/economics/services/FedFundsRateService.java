package com.example.economics.services;

import android.content.Context;
import android.util.Log;

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
    JSONArray sizedDown = new JSONArray();
    JSONArray reversed = new JSONArray();

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
                            for (int i = 0; i < 60; i++){
                                sizedDown.put(fedFundsArray.get(i));
                            }

                            for (int i = sizedDown.length()-1;i >= 0; i-- ){
                                reversed.put(sizedDown.get(i));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.d("responseSent", reversed.toString());
                        volleyResponseListener.onResponse(reversed);
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
