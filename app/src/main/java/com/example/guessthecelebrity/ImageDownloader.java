package com.example.guessthecelebrity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
    @Override
    protected Bitmap doInBackground(String... urls) {
        Log.i("Info ImageDownloader", "Url = " + urls[0]);

        URL url;
        HttpURLConnection urlConnection;
        Bitmap myBitmap;

        try {
            Log.i("Info ImageDownloader", "Inside try block of doInBackground(), checkpoint 1");
            url = new URL(urls[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            InputStream in = urlConnection.getInputStream();
            myBitmap = BitmapFactory.decodeStream(in);

            Log.i("Info ImageDownloader", "Inside try block of doInBackground(), checkpoint 2");
            return myBitmap;
        } catch (Exception e) {
            Log.i("Info ImageDownloader", "Inside catch block");
            e.printStackTrace();
            return null;
        }
    }
}
