package com.grability.test.appstoreg.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
import com.grability.test.appstoreg.R;
import com.grability.test.appstoreg.api.model.App;

import java.lang.reflect.Type;

public class SplashActivity extends AppCompatActivity {
    private static String URL_TO_ASK = "https://itunes.apple.com/us/rss/topfreeapplications/limit=20/json";
    private App result;
    private GetDataTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getResources().getBoolean(R.bool.portrait))
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        else
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        task = new GetDataTask(this);
        task.execute((Void) null);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public class GetDataTask extends AsyncTask<Void, Void, Boolean> {
        private Context activity;

        GetDataTask(Context context){
            activity = context;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_TO_ASK,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            GsonBuilder gsonBuilder = new GsonBuilder();
                            Gson gson = gsonBuilder.create();
                            Type type = new TypeToken<App>(){}.getType();
                            result = gson.fromJson(response, type);
                            if (result != null){
                                callHome(response);
                            }
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
            Volley.newRequestQueue(activity).add(stringRequest);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {

            super.onPostExecute(aBoolean);
        }
    }

    private void callHome(String categories){
        Intent accessIntent = new Intent(this, HomeActivity.class);
        Bundle bundleAccess = new Bundle();
        bundleAccess.putString("categories", categories);
        accessIntent.putExtras(bundleAccess);
        startActivity(accessIntent);
        finish();
    }


}
