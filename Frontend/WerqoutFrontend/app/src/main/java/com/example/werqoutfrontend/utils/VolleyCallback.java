package com.example.werqoutfrontend.utils;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * An interface that can be implemented to simulate callback functions. Used mainly for working with
 * objects returned by GET requests.
 */
public interface VolleyCallback {
    void onSuccess(JSONObject result);
    void onSuccess(JSONArray result);
}
