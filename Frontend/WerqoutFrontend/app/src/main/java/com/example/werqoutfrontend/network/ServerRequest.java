package com.example.werqoutfrontend.network;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.werqoutfrontend.app.AppController;
import com.example.werqoutfrontend.utils.Const;
import com.example.werqoutfrontend.utils.VolleyCallback;
import com.example.werqoutfrontend.utils.VolleyCallbackImage;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ServerRequest{
    /**
     * A tag to be used while debugging an error
     */
    private String TAG = ServerRequest.class.getSimpleName();
    /**
     * A tag to be used while debugging an error with JSON object requests
     */
    private String tag_json_obj_post = "jobj_req_post";
    /**
     * A tag to be used while debugging an error with image requests
     */
    private String tag_image_request = "image_request";

    /**
     * Sends a JSON object as a post request to the server
     * @param object
     *  The JSON object that is being posted to the server
     */
    public void jsonPostRequest(JSONObject object)
    {
        JsonObjectRequest jsonLogin = new JsonObjectRequest(Request.Method.POST,
                Const.CURRENT_URL, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };
        AppController.getInstance().addToRequestQueue(jsonLogin,
                tag_json_obj_post);
    }

    /**
     * Gets a JSON object from the server
     * @param callback
     *  The callback interface allows for the received object to be worked with
     *  once the object is returned from the server
     * @param url
     *  The url path used for making the get request
     */
    public void jsonGetRequest(VolleyCallback callback, String url)
    {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess(response);
                        Log.d(TAG, response.toString());
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

            }
        })
        {
            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        AppController.getInstance().addToRequestQueue(jsonObjReq,
                tag_json_obj_post);
    }

    /**
     * Used to get a JSON array from the server
     * @param callback
     *  The callback interface allows for the received object to be worked with
     *  once the object is returned from the server
     * @param url
     *  The url path used for making the get request
     */
    public void jsonArrayRequest(VolleyCallback callback, String url)
    {
        JsonArrayRequest jsonLogin = new JsonArrayRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };
        AppController.getInstance().addToRequestQueue(jsonLogin,
                tag_json_obj_post);
    }
    /**
     * Used to get a image from the server or anywhere else online
     * @param callback
     *  The callback interface allows for the received object to be worked with
     *  once the object is returned from the server
     * @param url
     *  The url path used for making the get request
     */
    public void imageRequest(VolleyCallbackImage callback, String url)
    {
        ImageRequest image = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                Log.d(TAG, response.toString());
                callback.onSuccess(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });

        AppController.getInstance().addToRequestQueue(image, tag_image_request);
    }

    /**
     * A method that allows for multiple different types of JSON Object request to be made. The request
     * type is specified through the requestType int, 0 for a GET, 1 for a POST, 2 for a PUT, and 3
     * for a DELETE request.
     * @param url
     *  The url path that is used for making the object request
     * @param requestType
     *  The type of JSON object request, 0 for a GET, 1 for a POST, 2 for a PUT, and 3
     *      * for a DELETE request.
     * @param object
     *  The JSON object that is used during the request
     */
    public void jsonObjectRequest(String url, int requestType, JSONObject object)
    {
        //GET = 0, POST = 1, PUT = 2, and DELETE = 3
        JsonObjectRequest request = new JsonObjectRequest(requestType, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
//            @Override
//            protected Map<String, String> getParams()
//            {
//                Map <String,String> params = new HashMap<String, String>();
//                params.put("profilePicture", Const.DEFAULT_PROFILE_PICTURE);
//                return params;
//            }
        };
        AppController.getInstance().addToRequestQueue(request,
                tag_json_obj_post);
    }
}

