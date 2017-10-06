package com.example.android.baiyuweather.utilities.weatherUtils;

import java.io.Serializable;

/**
 * Created by Baiyubest on 10/2/2017.
 */

public class WeatherDataHolder implements Serializable{
    public String Date;
    public String Summary;
    public String IconName;
    public int HighTemp;
    public int LowTemp;
    public int ApparentTempHigh;
    public int ApparentTempLow;
    public int Humidity;
    public int WindSpeed;
    public int WindBearing;
    public int PrecipProbability;
    public double PrecipIntensity;
    public WeatherDataHolder(){

    }

}
