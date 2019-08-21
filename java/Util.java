package com.example.gagan.dailyserices;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by gagan on 16/3/17.
 */

public class Util {
    public static boolean isNetworkConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo!=null && networkInfo.isConnected());
    }

    String city;
    public static String profileset="Skip";
    public static int nodialog=0,tutorornot,spinnerselected=0;
    public static String TutorSelectedbyuseremail,DialogName,DialogNameinput;
     public static Double latitude=30.9010;
    public static String registerorin="Register as a Tutor";

    public static int spinneron=0,cityon=0;
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public static Double longitude=75.8573;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProfileset() {
        return profileset;
    }

    public void setProfileset(String profileset) {
        this.profileset = profileset;
    }




    public static final String KEY_NAME = "userName";
    public static final String KEY_EMAIL = "userEmail";
    public static final String KEY_PHONE = "userPhone";
    public static final String PREFS_NAME = "personPrefs";




}
