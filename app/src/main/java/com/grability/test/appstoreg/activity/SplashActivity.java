package com.grability.test.appstoreg.activity;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.grability.test.appstoreg.R;
import com.grability.test.appstoreg.util.Util;

/**
 *  Clase inicial que carga el splash
 *  y revisa la conectividad a internet
 */
public class SplashActivity extends AppCompatActivity {
    private static String URL_TO_ASK = "https://itunes.apple.com/us/rss/topfreeapplications/limit=20/json";
    private GetDataTask task;
    private ReadTask readTask;
    private WriteTask writeTask;
    private String saved;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getResources().getBoolean(R.bool.portrait))
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        else
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mCellphone = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        saved = "";

        // Verifica la conectividad de internet para descargar los datos
        // en caso de tener conexión a internet
        if (mWifi != null){
            if (mWifi.isConnected()){
                task = new GetDataTask(this);
                task.execute((Void) null);
            }else{
                if (mCellphone!=null){
                    if (mCellphone.isConnected()){
                        task = new GetDataTask(this);
                        task.execute((Void) null);
                    }else{
                        readTask = new ReadTask(this);   //Busca el json en un archivo interno
                        readTask.execute((Void) null);
                    }
                }else{
                    readTask = new ReadTask(this);
                    readTask.execute((Void) null);
                }
            }
        } else{
            if (mCellphone!=null){
                if (mCellphone.isConnected()){
                    task = new GetDataTask(this);
                    task.execute((Void) null);
                }else{
                    readTask = new ReadTask(this);
                    readTask.execute((Void) null);
                }
            }else{
                readTask = new ReadTask(this);
                readTask.execute((Void) null);
            }
        }

        try {
            Thread.sleep(4000);
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
                            callHome(response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
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

    public class ReadTask extends AsyncTask<Void, Void, Boolean> {
        private Context activity;

        ReadTask(Context context){
            activity = context;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            saved = Util.readFromFile(activity, "json.txt");  // Json obtenido del archivo interno
            if (saved.length()>0){
                callHome(saved.trim());
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {

            super.onPostExecute(aBoolean);
        }
    }

    public class WriteTask extends AsyncTask<Void, Void, Boolean> {
        private Context activity;
        String json;

        WriteTask(Context context, String jsonSaved){
            activity = context;
            json = jsonSaved;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Util.writeToFile(json, activity, "json.txt");
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {

            super.onPostExecute(aBoolean);
        }
    }

    private void callHome(String categories){
        if (saved.length() == 0){    // Si tiene una cadena significa que ya estaba guardado el json
            writeTask = new WriteTask(this, categories);
            writeTask.execute((Void) null);
        }

        Intent accessIntent = new Intent(this, HomeActivity.class);
        Bundle bundleAccess = new Bundle();
        bundleAccess.putString("categories", categories);
        accessIntent.putExtras(bundleAccess);
        startActivity(accessIntent);
        finish();
    }

}
