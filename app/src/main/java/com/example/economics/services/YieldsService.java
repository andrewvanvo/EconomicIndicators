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

public class YieldsService {
    public static final String YIELDS_INTERVAL_MONTHLY_APIKEY_DEMO = "https://www.alphavantage.co/query?function=TREASURY_YIELD&interval=monthly&maturity=10year&apikey=demo";
    Context context;
    String latestYields;

    public YieldsService(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener {
        void onError(String message);
        void onResponse(String latestYields);
    }

    public void getLatestYields(FedFundsRateService.VolleyResponseListener volleyResponseListener){

        String url = YIELDS_INTERVAL_MONTHLY_APIKEY_DEMO;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray yieldsArray;
                        JSONObject latestYieldsObject;
                        latestYields = null;
                        try {
                            yieldsArray = response.getJSONArray("data");
                            latestYieldsObject = yieldsArray.getJSONObject(0);
                            latestYields = latestYieldsObject.getString("value");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        volleyResponseListener.onResponse(latestYields);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        volleyResponseListener.onError("No Yields Retrieved");
                    }
                });


        YieldsSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

}
