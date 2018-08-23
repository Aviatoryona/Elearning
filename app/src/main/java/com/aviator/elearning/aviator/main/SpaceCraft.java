package com.aviator.elearning.aviator.main;

import android.content.Context;

public class SpaceCraft {
    public static int NEW_COURSE = 1,
            NEW_EXAM = 2;

    public static String STRING_DATA = "ELEARNING";
    public static String CONNECTION_ERROR_MSG = "Internet Connection Error";
    public static String BUNDLE = "BUNDLE";
    public static int SNACKBARTIME = 120000;

    private String BASE_URL = "http://";

    public String getUrl(Context context) {
        MyPreferences myPrefs = new MyPreferences(context);
        if (myPrefs.isHasIp()) {
            BASE_URL += myPrefs.getIp();
        } else {
            return "http://192.168.1.76:9190/Elearning/?";
        }
        if (myPrefs.isHasPort()) {
            BASE_URL += ":" + myPrefs.getPort();
        }
        String TAIL = "/Elearning/";
        BASE_URL += TAIL;

        return BASE_URL.trim().replaceAll(" ", "");
    }

    public String getProfpicChangeUrl(Context context) {
        MyPreferences myPrefs = new MyPreferences(context);
        if (myPrefs.isHasIp()) {
            BASE_URL += myPrefs.getIp();
        } else {
            return "http://192.168.1.76:9190/Elearning/?";
        }
        if (myPrefs.isHasPort()) {
            BASE_URL += ":" + myPrefs.getPort();
        }
        String TAIL = "/Elearning/editprofpic.php";
        BASE_URL += TAIL;
        return BASE_URL.trim().replaceAll(" ", "");
    }

    public String ipPort(Context context){
        MyPreferences myPrefs = new MyPreferences(context);
        if (myPrefs.isHasIp()) {
            BASE_URL += myPrefs.getIp();
        } else {
            return "http://192.168.1.76:9190";
        }
        if (myPrefs.isHasPort()) {
            BASE_URL += ":" + myPrefs.getPort();
        }
        return BASE_URL;
    }
}
