package com.example.economics;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FedFundsRateService {

    public static final String FEDERAL_FUNDS_RATE_INTERVAL_MONTHLY_APIKEY_DEMO = "https://www.alphavantage.co/query?function=FEDERAL_FUNDS_RATE&interval=monthly&apikey=demo";
    Context context;
    String latestFedFundsRate;

    public FedFundsRateService(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener {
        void onError(String message);

        void onResponse(Object response);
    }

    public String getLatestFedFundsRate(){

        String url = FEDERAL_FUNDS_RATE_INTERVAL_MONTHLY_APIKEY_DEMO;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray fedFundsArray;
                        JSONObject latestFedFundsObject;
                        latestFedFundsRate = null;
                        try {
                            fedFundsArray = response.getJSONArray("data");
                            latestFedFundsObject = fedFundsArray.getJSONObject(0);
                            latestFedFundsRate = latestFedFundsObject.getString("value");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(context, latestFedFundsRate + "%", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Something broke", Toast.LENGTH_SHORT).show();

                    }
                });

        //queue.add(jsonObjectRequest);
        // Add a request (in this example, called stringRequest) to your RequestQueue.
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);

        return latestFedFundsRate;
    }

//    public List<FedFundsRateHistorical> getOldFedFundRates(){
//
//    }
}
