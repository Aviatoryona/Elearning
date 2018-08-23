package com.aviator.elearning.aviator.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

@SuppressWarnings("ALL")
public class MyPreferences {


    private SharedPreferences sharedPreferences;
    private final String isFirstTime = "isFirstTime";
    private final String lastDt = "lastDt";
    private final String showLauncher = "showLauncher";
    private final String bscode = "bscode";
    private final String IP = "IP", PORT = "PORT", imei = "IMEI", location = "LOCATION";
    private final String name = "NAME", email = "EMAIL", phone = "PHONE", usertype = "USERTYPE", country = "COUNTRY";

    public MyPreferences(@NonNull Context context) {
        String SHNAME = "hsc";
        try {
            sharedPreferences = context.getSharedPreferences(SHNAME, Context.MODE_PRIVATE);
        } catch (NullPointerException ignored) {
        }
    }

    public void WriteFirstTime() {
        if (sharedPreferences == null) {
            return;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(isFirstTime, false);
        editor.apply();
    }

    public boolean isFirstTime() {
        return sharedPreferences != null && sharedPreferences.contains(isFirstTime);
    }

    public void WriteLastDt(long lsd) {
        if (sharedPreferences == null) {
            return;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(lastDt, lsd);
        editor.apply();
    }

    public long getLastDt() {
        if (sharedPreferences == null) {
            return -1;
        }
        if (sharedPreferences.contains(lastDt)) {
            return sharedPreferences.getLong(lastDt, -1);
        }
        return -1;
    }

    public void WriteShowLaunchingScreen(boolean bool) {
        if (sharedPreferences == null) {
            return;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(showLauncher, bool);
        editor.apply();
    }

    public boolean showLauncher() {
        return sharedPreferences != null && (!sharedPreferences.contains(showLauncher) || sharedPreferences.getBoolean(showLauncher, true));
    }

    public void WriteBsCode(String bs) {
        if (sharedPreferences == null) {
            return;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(bscode, bs);
        editor.apply();
    }

    public String getBscode() {
        if (sharedPreferences == null) {
            return "";
        }
        if (sharedPreferences.contains(bscode)) {
            return sharedPreferences.getString(bscode, "");
        }
        return "";
    }

    public boolean isHasIp() {
        return sharedPreferences != null && sharedPreferences.contains(IP);
    }

    public boolean isHasPort() {
        return sharedPreferences != null && sharedPreferences.contains(PORT);
    }

    public void setIp(String ip) {
        if (sharedPreferences == null) {
            return;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(IP, ip);
        editor.apply();
    }

    public void setport(String port) {
        if (sharedPreferences == null) {
            return;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PORT, port);
        editor.apply();
    }

    public String getIp() {
        if (sharedPreferences == null || !sharedPreferences.contains(IP)) {
            return "";
        }
        return sharedPreferences.getString(IP, "");
    }

    public String getPort() {
        if (sharedPreferences == null || !sharedPreferences.contains(PORT)) {
            return "";
        }

        return sharedPreferences.getString(PORT, "");
    }

    public String getName() {
        if (sharedPreferences == null || !sharedPreferences.contains(name)) {
            return "";
        }

        return sharedPreferences.getString(name, "");
    }

    public String getEmail() {
        if (sharedPreferences == null || !sharedPreferences.contains(email)) {
            return "";
        }

        return sharedPreferences.getString(email, "");
    }

    public String getPhone() {
        if (sharedPreferences == null || !sharedPreferences.contains(phone)) {
            return "";
        }

        return sharedPreferences.getString(phone, "");
    }

    public void setName(String em) {
        if (sharedPreferences == null) {
            return;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(name, em);
        editor.apply();
    }

    public void setPhone(String em) {
        if (sharedPreferences == null) {
            return;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(phone, em);
        editor.apply();
    }

    public void setEmail(String em) {
        if (sharedPreferences == null) {
            return;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(email, em);
        editor.apply();
    }


    public String getImei() {
        if (sharedPreferences == null || !sharedPreferences.contains(imei)) {
            return "";
        }

        return sharedPreferences.getString(imei, "");
    }

    public String getLocation() {
        if (sharedPreferences == null || !sharedPreferences.contains(location)) {
            return "";
        }

        return sharedPreferences.getString(location, "");
    }

    public void setImei(String em) {
        if (sharedPreferences == null) {
            return;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(imei, em);
        editor.apply();
    }

    public void setLocation(String em) {
        if (sharedPreferences == null) {
            return;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(location, em);
        editor.apply();
    }

    public void setUserType(String em) {
        if (sharedPreferences == null) {
            return;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(usertype, em);
        editor.apply();
    }

    public String getUsertype() {
        if (sharedPreferences == null || !sharedPreferences.contains(usertype)) {
            return "";
        }

        return sharedPreferences.getString(usertype, "");
    }

    public void setCountry(String em) {
        if (sharedPreferences == null) {
            return;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(country, em);
        editor.apply();
    }

    public String getCountry() {
        if (sharedPreferences == null || !sharedPreferences.contains(country)) {
            return "";
        }

        return sharedPreferences.getString(country, "");
    }

    public boolean CheckLoginStatus() {
        assert sharedPreferences != null;
        return sharedPreferences != null && sharedPreferences.contains(email);
    }

    public boolean Logout() {
        if (sharedPreferences == null) {
            return false;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        return editor.commit();
    }
}
