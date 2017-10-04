package com.example.android.baiyuweather.utilities;

import com.example.android.baiyuweather.R;

/**
 * Created by Baiyubest on 10/3/2017.
 */

public class DarkSkyWeatherUtils {
    public static int getImgIdWithJson(String str){
        if(str.equals("clear-day")||str.equals("clear-night")){
            return R.drawable.clear_day;
        }
        else if(str.equals("rain")){
            return R.drawable.rain;
        }
        else if(str.equals("snow")){
            return R.drawable.snow;
        }
        else if(str.equals("sleet")){
            return R.drawable.sleet;
        }
        else if(str.equals("wind")){
            return R.drawable.wind;
        }
        else if(str.equals("fog")){
            return R.drawable.fog;
        }
        else if(str.equals("cloudy")){
            return R.drawable.cloudy;
        }
        else if(str.equals("partly-cloudy-day")||str.equals("partly-cloudy-night")){
            return R.drawable.partly_cloudy;
        }
        else if(str.equals("hail")){
            return R.drawable.hail;
        }
        else if(str.equals("thunderstorm")){
            return R.drawable.storm;
        }
        else if(str.equals("tornado")){
            return R.drawable.tornado;
        }
        else{
            return R.drawable.flower;
        }

    }
}
