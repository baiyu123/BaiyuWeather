package com.example.android.baiyuweather.utilities.notificationUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.android.baiyuweather.utilities.NetworkUtils;
import com.example.android.baiyuweather.utilities.weatherUtils.DarkSkyJsonUtils;
import com.example.android.baiyuweather.utilities.weatherUtils.WeatherDataHolder;

import java.net.URL;

/**
 * Created by Baiyubest on 10/5/2017.
 */

public class WeatherNotificationOnBoot extends BroadcastReceiver {
    static final String ACTION_BOOT_COMPLETED = "android.intent.action.BOOT_COMPLETED";
    static final String ACTION_BOOT_POWERON = "android.intent.action.QUICKBOOT_POWERON";
    Context mContext;
    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        if(intent.getAction().equalsIgnoreCase(ACTION_BOOT_COMPLETED)||intent.getAction().equalsIgnoreCase(ACTION_BOOT_POWERON)) {
           new WeatherQuery().execute();
        }
    }


    public class WeatherQuery extends AsyncTask<Void, Void, WeatherDataHolder[]> {



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected WeatherDataHolder[] doInBackground(Void... params) {
            URL weatherRequeswtUrl = NetworkUtils.buildUrl(mContext);
            WeatherDataHolder[] weatherData = null;
            try{
                String jsonWeatherResponse = NetworkUtils.getResponseFromHttpUrl(weatherRequeswtUrl);
                weatherData = DarkSkyJsonUtils.getWeatherDataFromJson(mContext,jsonWeatherResponse);

            }
            catch (Exception e){
                e.printStackTrace();
                return null;
            }
            return weatherData;
        }

        @Override
        protected void onPostExecute(WeatherDataHolder[] weatherDataHolders) {
            if(weatherDataHolders[0].PrecipProbability>10) {
                ReminderTasks.executeTask(mContext, ReminderTasks.ACTION_REMIND_USER_UMBRELLA,weatherDataHolders[0]);
            }
        }
    }
}
