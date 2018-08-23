package com.aviator.elearning.el.net;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.aviator.elearning.aviator.main.SpaceCraft;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class VolleySingleton {
    private static RequestQueue requestQueue;
    private static HttpURLConnection httpURLConnection;

    public static RequestQueue volleyInstance(Context context) {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context);
        }
        return requestQueue;
    }

    public static HttpURLConnection getHttpURLConnection(Context context) {
        if (httpURLConnection == null) {
            try {
                URL url = new URL(new SpaceCraft().getUrl(context));
                httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setReadTimeout(20000);
                httpURLConnection.connect();
                return httpURLConnection;
            } catch (MalformedURLException ignored) {

            } catch (IOException ignored) {

            }
        }

        return null;
    }

}
