package com.example.android.baiyuweather.utilities;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import com.example.android.baiyuweather.utilities.SunshineDateUtils;

/**
 * Created by Baiyubest on 10/2/2017.
 */

public class DarkSkyJsonUtils {



    public static WeatherDataHolder[] getSimpleWeatherStringsFromJson(Context context, String forecastJsonStr)
            throws JSONException {


        final String OWM_MESSAGE_CODE = "cod";



        /* String array to hold each day's weather String */
        WeatherDataHolder[] parsedWeatherDatas = new WeatherDataHolder[7];

        JSONObject forecastJson = new JSONObject(forecastJsonStr);

        /* Is there an error? */
        if (forecastJson.has(OWM_MESSAGE_CODE)) {
            int errorCode = forecastJson.getInt(OWM_MESSAGE_CODE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    /* Location invalid */
                    return null;
                default:
                    /* Server probably down */
                    return null;
            }
        }
        WeatherDataHolder holder = new WeatherDataHolder();
        JSONObject currWeatherObj = forecastJson.getJSONObject("currently");
        String temp = currWeatherObj.getString("temperature");

        JSONArray dailyWeatherArray = forecastJson.getJSONObject("daily").getJSONArray("data");

        for(int i = 0; i < 7; i++){
            JSONObject dailyObj = dailyWeatherArray.getJSONObject(i);
            long dateTimeMillis = (long)dailyObj.getInt("time")*1000;
            parsedWeatherDatas[i] = new WeatherDataHolder();
            parsedWeatherDatas[i].Date = SunshineDateUtils.getFriendlyDateString(context, dateTimeMillis, false);
            parsedWeatherDatas[i].Summary = dailyObj.getString("summary");
            parsedWeatherDatas[i].IconName = dailyObj.getString("icon");
            parsedWeatherDatas[i].HighTemp = (int)Math.round(dailyObj.getDouble("temperatureMax"));
            parsedWeatherDatas[i].LowTemp = (int)Math.round(dailyObj.getDouble("temperatureMin"));
            parsedWeatherDatas[i].ApparentTempHigh = (int)Math.round(dailyObj.getDouble("apparentTemperatureMax"));
            parsedWeatherDatas[i].ApparentTempLow = (int)Math.round(dailyObj.getDouble("apparentTemperatureMin"));
            parsedWeatherDatas[i].Humidity = (int)(dailyObj.getDouble("humidity")*100);
            parsedWeatherDatas[i].WindSpeed = (int)Math.round(dailyObj.getDouble("windSpeed"));
            parsedWeatherDatas[i].WindBearing = dailyObj.getInt("windBearing");
            parsedWeatherDatas[i].PrecipProbability = (int)(dailyObj.getDouble("precipProbability")*100);
        }


        return parsedWeatherDatas;
    }


}
