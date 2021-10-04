package com.example.werqoutfrontend.utils;
import org.json.JSONArray;
import org.json.JSONObject;
public interface VolleyCallback {
    void onSuccess(JSONObject result);
    void onSuccess(JSONArray result);
}
