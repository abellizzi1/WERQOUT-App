package com.example.werqoutfrontend.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * This utility class allows conversion between a string and bitmap and vice versa. Used to store
 * images as string on the backend.
 * @author Colin
 */
public final class BitmapStringConversion {

    /**
     * Converts a given bitmap into a string
     * @param bitmapPicture
     *  The bitmap to be converted
     * @return
     *  The string representation of the bitmap
     */
    public static String getStringFromBitmap(Bitmap bitmapPicture) {
        final int COMPRESSION_QUALITY = 100;
        String encodedImage;
        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
        bitmapPicture.compress(Bitmap.CompressFormat.JPEG, COMPRESSION_QUALITY,
                byteArrayBitmapStream);
        byte[] b = byteArrayBitmapStream.toByteArray();
        encodedImage = Base64.encodeToString(b, Base64.NO_WRAP);
        return encodedImage;
    }

    /**
     * Converts a given String into a bitmap
     * @param stringPicture
     *  The String to be converted
     * @return
     *  The bitmap representation of the string
     */
//Use this code to convert the encoded string back to bitmap image
    public static Bitmap getBitmapFromString(String stringPicture) {
        byte[] decodedString = Base64.decode(stringPicture, Base64.NO_WRAP);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }
}
