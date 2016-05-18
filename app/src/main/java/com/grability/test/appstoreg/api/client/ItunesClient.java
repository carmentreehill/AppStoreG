package com.grability.test.appstoreg.api.client;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.grability.test.appstoreg.api.model.App;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Carmen Pérez Hernández on 15/05/16.
 */
public class ItunesClient {
    private static String URL_TO_ASK = "https://itunes.apple.com/us/rss/topfreeapplications/limit=1/json";
    private static List<App> result;

    public static List<App> requestInfo(Context context){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_TO_ASK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        Type listType = new TypeToken<List<App>>(){}.getType();
                        result = gson.fromJson(response, listType);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                result = null;
            }
        });
        int socketTimeout = 60000;// Tiempo de respuesta mayor para evitar TimeOut
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        Volley.newRequestQueue(context).add(stringRequest);
        return result;
    }

}
