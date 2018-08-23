package com.aviator.elearning.el.CommonsPkg;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.aviator.elearning.aviator.main.MyPreferences;
import com.aviator.elearning.aviator.main.SpaceCraft;
import com.aviator.elearning.el.models.NetParamsModel;
import com.aviator.elearning.el.net.VolleySingleton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class HttpLoadData {

    public static String LoadData(Context context, ArrayList<NetParamsModel> params) {
        HttpURLConnection httpURLConnection = VolleySingleton.getHttpURLConnection(context);
        if (httpURLConnection != null) {
            OutputStream bufferedOutputStream;
            try {
                bufferedOutputStream = httpURLConnection.getOutputStream();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(bufferedOutputStream);
                String data = WriteData(params);
                outputStreamWriter.write(data);
                outputStreamWriter.flush();
                outputStreamWriter.close();

                if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    String line;
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    Log.e(SpaceCraft.STRING_DATA, stringBuilder.toString());
                    return stringBuilder.toString();
                }
            } catch (IOException e) {
                Log.e(SpaceCraft.STRING_DATA, e.toString());
            }
        }
        return null;
    }


    private static String WriteData(ArrayList<NetParamsModel> params) {

        if (params != null) {
            StringBuilder data = new StringBuilder();
            for (int i = 0; i < params.size(); i++) {
                NetParamsModel netParamsModel = params.get(i);
                try {
                    data.append(URLEncoder.encode(netParamsModel.getKey(), "UTF-8")).append("=").append(URLEncoder.encode(netParamsModel.getValue(), "UTF-8"));
                    if (i < params.size()) {
                        data.append("&");
                    }
                } catch (UnsupportedEncodingException e) {
                    Log.e(SpaceCraft.STRING_DATA, e.toString());
                    return "";
                }
            }
            return data.toString();
        } else {
            return "";
        }

    }

}
