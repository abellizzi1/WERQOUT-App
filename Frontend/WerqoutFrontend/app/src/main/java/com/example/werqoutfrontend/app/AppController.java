package com.example.werqoutfrontend.app;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * This class handles basic request making that is used by the server request class. Maintains a
 * request queue that can be used to queue up new volley requests to retrieve/update information on
 * the backend.
 */

public class AppController extends Application{
    public static final String TAG = AppController.class
            .getSimpleName();
    /**
     * A request queue for making volley request to the server
     */
    private RequestQueue mRequestQueue;

    /**
     * A static instance of the class
     */
    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    /**
     * Gets a static instance of the class
     * @return
     *  A static instance of the class
     */
    public static synchronized AppController getInstance() {
        return mInstance;
    }

    /**
     * Returns a request queue
     * @return
     *  A volley request queue
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    /**
     * Adds a request to the request queue along with a tag that can be used to identify
     * errors more easily during debuging
     * @param req
     *  The request being made
     * @param tag
     *  The tag to go along with the request
     * @param <T>
     *  The type of request being made
     */
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    /**
     * Adds a request to the request queue
     * @param req
     *  The request being made
     * @param <T>
     *  The type of request being made
     */
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    /**
     * Cancels all current request that are being made with a certain tag
     * @param tag
     *  The tag associated with a request
     */
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}

