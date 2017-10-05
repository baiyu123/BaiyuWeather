package com.example.android.baiyuweather;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.android.baiyuweather.databinding.ActivityWeatherDetailBinding;
import com.example.android.baiyuweather.utilities.weatherUtils.DarkSkyWeatherUtils;
import com.example.android.baiyuweather.utilities.weatherUtils.WeatherDataHolder;

public class WeatherActivityDetail extends AppCompatActivity {
    private ActivityWeatherDetailBinding mDetailBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);
        mDetailBinding = DataBindingUtil.setContentView(this,R.layout.activity_weather_detail);
        Intent intent = getIntent();
        //fetching data
        WeatherDataHolder weatherData = null;
        if(intent.hasExtra("Weatherdata")){
            weatherData = (WeatherDataHolder) intent.getSerializableExtra("Weatherdata");
        }
        if(weatherData != null) {
            mDetailBinding.apparentTemp.setText(Integer.toString(weatherData.ApparentTempHigh)+"°");
            mDetailBinding.detailDate.setText(weatherData.Date);
            mDetailBinding.detailHighTemp.setText(Integer.toString(weatherData.HighTemp)+"°");
            mDetailBinding.detailLowTemp.setText(Integer.toString(weatherData.LowTemp)+"°");
            mDetailBinding.humidity.setText(Integer.toString(weatherData.Humidity)+"%");
            mDetailBinding.summary.setText(weatherData.Summary);
            mDetailBinding.windspeed.setText(Integer.toString(weatherData.WindSpeed)+"m/s");
            String direction = DarkSkyWeatherUtils.getWindDirection(weatherData.WindBearing,this);
            mDetailBinding.windbearing.setText(direction);
            int imgId = DarkSkyWeatherUtils.getImgIdWithJson(weatherData.IconName);
            mDetailBinding.detailImgIcon.setImageResource(imgId);
        }
        //back button
        ActionBar actionBar = this.getSupportActionBar();

        // Set the action bar back button to look like an up button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
