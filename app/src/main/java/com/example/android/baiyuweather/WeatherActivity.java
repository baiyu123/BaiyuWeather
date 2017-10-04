package com.example.android.baiyuweather;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.android.baiyuweather.NetworkUtils;
import com.example.android.baiyuweather.utilities.DarkSkyJsonUtils;
import com.example.android.baiyuweather.utilities.ForecastAdapter;
import com.example.android.baiyuweather.utilities.WeatherDataHolder;

import java.net.URL;

public class WeatherActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<WeatherDataHolder[]> {
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

    }

    @Override
    public Loader<WeatherDataHolder[]> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<WeatherDataHolder[]>(this) {
            WeatherDataHolder[] mWeatherData = null;
            @Override
            protected void onStartLoading(){
                mLoadingIndicator.setVisibility(View.VISIBLE);
                if(mWeatherData != null){
                    deliverResult(mWeatherData);
                }
                else{
                    forceLoad();
                }
            }

            @Override
            public WeatherDataHolder[] loadInBackground() {
                URL weatherRequeswtUrl = NetworkUtils.buildUrl(this.getContext());
                WeatherDataHolder[] weatherData = null;
                try{
                    String jsonWeatherResponse = NetworkUtils.getResponseFromHttpUrl(weatherRequeswtUrl);
                    weatherData = DarkSkyJsonUtils.getSimpleWeatherStringsFromJson(this.getContext(),jsonWeatherResponse);

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

    private void setRecycleView(WeatherDataHolder[] data){
        //recycling view
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_forecast);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mForecastAdapter = new ForecastAdapter(this);
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
}
