package com.example.android.baiyuweather;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.android.baiyuweather.utilities.NetworkUtils;
import com.example.android.baiyuweather.utilities.notificationUtils.ReminderTasks;
import com.example.android.baiyuweather.utilities.notificationUtils.ReminderUtils;
import com.example.android.baiyuweather.utilities.notificationUtils.WeatherBackgroundJobService;
import com.example.android.baiyuweather.utilities.weatherUtils.DarkSkyJsonUtils;
import com.example.android.baiyuweather.utilities.weatherUtils.WeatherDataHolder;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    ImageView mSeeWeatherIcon;
    ImageView mGoHomeIcon;
    ImageView mSearchDictIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSeeWeatherIcon = (ImageView) findViewById(R.id.weather_icon);
        mGoHomeIcon = (ImageView) findViewById(R.id.home_icon);
        mSearchDictIcon = (ImageView) findViewById(R.id.dictionary_icon);
        setListener();

    }

    private void setListener(){
        mSeeWeatherIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
                startActivity(intent);
            }
        });

        mGoHomeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uriStr = "geo:0,0?q="+getString(R.string.home_address);
                Uri uri = Uri.parse(uriStr);
                showMap(uri);
            }
        });

        mSearchDictIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,DictionaryActivity.class);
                startActivity(intent);
            }
        });

        //starting intent service


    }



    public void showMap(Uri geoLocation) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
