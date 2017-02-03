package com.fjodors.weatherapp.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;

import com.fjodors.weatherapp.R;

public class BitmapUtils {

    /**
     * Rotate wind direction icon depending on degrees
     */
    public static BitmapDrawable getRotatedWindDirectionIcon(double windDirection, Context context) {
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_navigation_black_24dp);
        // Getting width & height of the given image.
        int w = bmp.getWidth();
        int h = bmp.getHeight();
        // Setting post rotate to wind direction degrees
        Matrix mtx = new Matrix();
        mtx.postRotate((float) windDirection);
        // Rotating Bitmap
        Bitmap rotatedBMP = Bitmap.createBitmap(bmp, 0, 0, w, h, mtx, true);
        return new BitmapDrawable(rotatedBMP);
    }
}
