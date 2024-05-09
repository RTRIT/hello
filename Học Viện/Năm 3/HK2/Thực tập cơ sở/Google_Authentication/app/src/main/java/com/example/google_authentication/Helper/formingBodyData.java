package com.example.google_authentication.Helper;

import org.json.JSONException;
import org.json.JSONObject;

public class formingBodyData {
    public JSONObject formingBodyDataLogin(String usrname, String pass){
        JSONObject postData = new JSONObject();
        try{
            postData.put("username", usrname);
            postData.put("password", pass);

        }catch (JSONException e){
            e.printStackTrace();
        }
        return postData;
    }

    public JSONObject formingBodyDataLoginOauth(String usrname){
        JSONObject postData = new JSONObject();
        try{
            postData.put("username", usrname);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return postData;
    }
}
