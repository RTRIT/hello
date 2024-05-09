package com.example.google_authentication.Helper;

import android.content.Context;
import android.content.SharedPreferences;


public class MySharePreference {
    private static final String PREF_NAME = "tokenSet";
    private static final String KEY_TOKEN = "token";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor; //Chỉnh sửa với sharedPreference
    private static MySharePreference instance;

    //Initialize SharedPreference and SharedPreferences.Editor
    private MySharePreference(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static synchronized MySharePreference getInstance(Context context) {
        if (instance == null) {
            instance = new MySharePreference(context.getApplicationContext());
        }
        return instance;
    }


    public void saveToken(String token) {
        editor.putString(KEY_TOKEN, token).apply();
    }

    public String getToken() {
        return sharedPreferences.getString(KEY_TOKEN, null);
    }

    public void clear() {
        editor.clear().apply();
    }
}
