package com.example.werqoutfrontend.utils;

import android.graphics.Bitmap;
/**
 * An interface that can be implemented to simulate callback functions for images.
 * Used mainly for working with images returned by image requests.
 * @author Colin
 */
public interface VolleyCallbackImage {
    void onSuccess(Bitmap response);
}
