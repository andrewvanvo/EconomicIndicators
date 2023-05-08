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
    JSONArray sizedDown = new JSONArray();
    JSONArray reversed = new JSONArray();

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
                            for (int i = 0; i <20 ; i++){
                                sizedDown.put(unemploymentArray.get(i));
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
                        volleyResponseListener.onError("No unemployment Retrieved");
                    }
                });


        UnemploymentSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

}


