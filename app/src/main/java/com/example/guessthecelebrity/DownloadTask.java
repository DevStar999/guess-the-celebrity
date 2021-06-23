package com.example.guessthecelebrity;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadTask extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... urls) {
        Log.i("Info DownloadTask", "Url = " + urls[0]);

        try {
            Log.i("Info DownloadTask", "Inside try block of doInBackground(), checkpoint 3");
            Document document = Jsoup.connect(urls[0]).get();
            Element element = document.select("div.lister-list").first();
            return element.html();
        } catch (Exception e) {
            Log.i("Info", "Inside catch block");
            e.printStackTrace();

            return "Process of downloading content failed";
        }
    }
}
