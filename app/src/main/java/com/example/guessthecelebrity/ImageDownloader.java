package com.example.guessthecelebrity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;

public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
    @Override
    protected Bitmap doInBackground(String... imageUrls) {
        Log.i("Info", "Url = " + imageUrls[0]);

        Bitmap myBitmap;
        try {
            Log.i("Info", "Inside try block of doInBackground(), checkpoint 1");
            InputStream input = new java.net.URL(imageUrls[0]).openStream();
            myBitmap = BitmapFactory.decodeStream(input);

            Log.i("Info", "Inside try block of doInBackground(), checkpoint 2");
            return myBitmap;
        } catch (Exception e) {
            Log.i("Info", "Inside catch block");
            e.printStackTrace();
            return null;
        }
    }
}
