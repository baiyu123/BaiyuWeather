/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.baiyuweather.utilities.notificationUtils;

import android.content.Context;
import android.os.AsyncTask;

import com.example.android.baiyuweather.MainActivity;
import com.example.android.baiyuweather.utilities.NetworkUtils;
import com.example.android.baiyuweather.utilities.weatherUtils.DarkSkyJsonUtils;
import com.example.android.baiyuweather.utilities.weatherUtils.WeatherDataHolder;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.firebase.jobdispatcher.RetryStrategy;

import java.net.URL;

// COMPLETED (3) WaterReminderFirebaseJobService should extend from JobService
public class WeatherBackgroundJobService extends JobService {

    private AsyncTask mBackground;
    private Context mContext;

    // COMPLETED (4) Override onStartJob
    /**
     * The entry point to your Job. Implementations should offload work to another thread of
     * execution as soon as possible.
     *
     * This is called by the Job Dispatcher to tell us we should start our job. Keep in mind this
     * method is run on the application's main thread, so we need to offload work to a background
     * thread.
     *
     * @return whether there is more work remaining.
     */
    @Override
    public boolean onStartJob(final JobParameters jobParameters) {

        mContext = WeatherBackgroundJobService.this;
        mBackground = new WeatherQuery().execute();
        return true;
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
            if(weatherDataHolders[1].PrecipProbability>20) {
                ReminderTasks.executeTask(mContext, ReminderTasks.ACTION_REMIND_USER_UMBRELLA,weatherDataHolders[0]);
            }
        }
    }
    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        // COMPLETED (12) If mBackgroundTask is valid, cancel it
        // COMPLETED (13) Return true to signify the job should be retried
        if (mBackground != null) mBackground.cancel(true);
        return true;
    }
}