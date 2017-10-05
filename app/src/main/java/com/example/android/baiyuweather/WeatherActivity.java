package com.example.android.baiyuweather;

import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.android.baiyuweather.utilities.NetworkUtils;
import com.example.android.baiyuweather.utilities.weatherUtils.DarkSkyJsonUtils;
import com.example.android.baiyuweather.utilities.ForecastAdapter;
import com.example.android.baiyuweather.utilities.weatherUtils.WeatherDataHolder;

import java.net.URL;

public class WeatherActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<WeatherDataHolder[]>, ForecastAdapter.ForecastAdapterOnClickHandler {
    private int loaderId = 1;
    private ProgressBar mLoadingIndicator;
    private RecyclerView mRecyclerView;
    private ForecastAdapter mForecastAdapter;
    private WeatherDataHolder[] mWeatherData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        //initialize loader
        Bundle bundleForLoader = null;
        LoaderManager.LoaderCallbacks<WeatherDataHolder[]> callback = WeatherActivity.this;
        getSupportLoaderManager().initLoader(loaderId, bundleForLoader, callback);
        mLoadingIndicator = (ProgressBar)findViewById(R.id.loading_indicator);
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

    @Override
    public Loader<WeatherDataHolder[]> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<WeatherDataHolder[]>(this) {
            WeatherDataHolder[] mWeatherData = null;
            @Override
            protected void onStartLoading(){
                if(mWeatherData != null){
                    deliverResult(mWeatherData);
                }
                else{
                    mLoadingIndicator.setVisibility(View.VISIBLE);
                    forceLoad();
                }
            }

            //get data from the api
            @Override
            public WeatherDataHolder[] loadInBackground() {
                URL weatherRequeswtUrl = NetworkUtils.buildUrl(this.getContext());
                WeatherDataHolder[] weatherData = null;
                try{
                    String jsonWeatherResponse = NetworkUtils.getResponseFromHttpUrl(weatherRequeswtUrl);
                    weatherData = DarkSkyJsonUtils.getWeatherDataFromJson(this.getContext(),jsonWeatherResponse);

                }
                catch (Exception e){
                    e.printStackTrace();
                    return null;
                }
                return weatherData;
            }
            public void deliverResult(WeatherDataHolder[] data){
                mWeatherData = data;
                super.deliverResult(data);
            }
        };
    }
    //start building recycleview
    private void setRecycleView(WeatherDataHolder[] data){
        //recycling view
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_forecast);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mForecastAdapter = new ForecastAdapter(this,this);
        mForecastAdapter.setWeatherData(data);
        mRecyclerView.setAdapter(mForecastAdapter);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadFinished(Loader<WeatherDataHolder[]> loader, WeatherDataHolder[] data) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        setRecycleView(data);
    }

    @Override
    public void onLoaderReset(Loader<WeatherDataHolder[]> loader) {

    }

    @Override
    public void onClick(WeatherDataHolder weatherForDay) {
        Intent weatherDetailIntent = new Intent(this, WeatherActivityDetail.class);
        Bundle bundle = new Bundle();
        weatherDetailIntent.putExtra("Weatherdata",weatherForDay);
        startActivity(weatherDetailIntent);
    }
}
