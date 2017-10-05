package com.example.android.baiyuweather.utilities;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;

import com.example.android.baiyuweather.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Baiyubest on 10/2/2017.
 */

public final class NetworkUtils {
    private static final String WEATHER_URL = "https://api.darksky.net/forecast/";

    public static URL buildUrl(Context context){
        String key = context.getString(R.string.key);
        String latitude = context.getString(R.string.latitude);
        String longtitude = context.getString(R.string.longtitude);
        String langQuery = "lang=zh-tw";
        String unitQuery = "units=si";
        String str = WEATHER_URL+ key+"/"+latitude+","+longtitude+"?"+langQuery+"&"+unitQuery;
        URL url = null;
        try{
            url = new URL(str);
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }
    public static String dictionaryEntries() {
        final String language = "en";
        final String word = "Ace";
        final String word_id = word.toLowerCase(); //word id is case sensitive and lowercase is required
        return "https://od-api.oxforddictionaries.com:443/api/v1/entries/" + language + "/" + word_id;
    }


    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
