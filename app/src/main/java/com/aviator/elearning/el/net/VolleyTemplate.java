package com.aviator.elearning.el.net;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.aviator.elearning.aviator.main.SpaceCraft;

import java.util.HashMap;
import java.util.Map;

public class VolleyTemplate {

    private void Template(Context context){
        final RequestQueue requestQueue=VolleySingleton.volleyInstance(context);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, new SpaceCraft().getUrl(context), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                requestQueue.stop();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                requestQueue.stop();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map=new HashMap<>();
                map.put("target","target");
                map.put("action","action");
                return map;
            }
        };
        requestQueue.add(stringRequest);
        requestQueue.start();
    }

}
