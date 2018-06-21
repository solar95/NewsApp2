package com.example.solar.newsapp;


import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();
    private QueryUtils() {
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "URL: ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error retrieving Json : ", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static ArrayList<NewsArticle> extractFeatureFromJSON(String articleJSON) {

        if (TextUtils.isEmpty(articleJSON)) {
            return null;
        }
        ArrayList<NewsArticle> newsList = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(articleJSON);
            JSONObject response = baseJsonResponse.getJSONObject("response");
            JSONArray results = response.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {

                JSONObject r = results.getJSONObject(i);
                String title = r.getString("webTitle");
                String webPublicationDate = r.getString("webPublicationDate");
                String link = r.getString("webUrl");
                String sectionName = r.getString("sectionName");
                JSONObject fields = r.getJSONObject("fields");
                String author = "By: "+fields.getString("byline");
                Log.e(LOG_TAG,author);

                String dateFinal = "";

                try{
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
                    Date date = format.parse(webPublicationDate);
                    DateFormat format2 = new SimpleDateFormat("dd MMM yy", Locale.getDefault());
                    dateFinal = format2.format(date);
                }catch (ParseException e){
                    Log.e(LOG_TAG, "Extracted JSON: ", e);
                }

                NewsArticle newArticle = new NewsArticle(title,sectionName,link,dateFinal,author);
                newsList.add(newArticle);
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error parsing JSON results: ", e);
        }

        return newsList;
    }


    public static List<NewsArticle> fetchData(String requestURL) {
        URL url = createUrl(requestURL);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }
        return extractFeatureFromJSON(jsonResponse);
    }


}

