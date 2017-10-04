package com.example.android.baiyuweather.utilities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.baiyuweather.R;

/**
 * Created by Baiyubest on 10/3/2017.
 */

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastAdapterViewHolder> {
    private static final int VIEW_TYPE_TODAY = 0;
    private static final int VIEW_TYPE_FUTURE_DAY = 1;

    private Context mContext;
    private boolean mUseTodayLayout;
    private WeatherDataHolder[] mWeatherData;

    public ForecastAdapter(Context context){
        mContext = context;
        mUseTodayLayout = true;
    }

    @Override
    public ForecastAdapter.ForecastAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId;
        switch (viewType){
            case VIEW_TYPE_TODAY:{
                layoutId = R.layout.list_item_view_today;
                break;
            }
            case VIEW_TYPE_FUTURE_DAY:{
                layoutId = R.layout.list_item_view;
                break;
            }
            default:
                throw new IllegalArgumentException("Invalid view type, value of " + viewType);
        }
        View view = LayoutInflater.from(mContext).inflate(layoutId,parent,false);
        view.setFocusable(true);
        return new ForecastAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ForecastAdapter.ForecastAdapterViewHolder holder, int position) {
        int imgId = DarkSkyWeatherUtils.getImgIdWithJson(mWeatherData[position].IconName);
        holder.iconView.setImageResource(imgId);
        holder.dateView.setText(mWeatherData[position].Date);
        holder.lowTempView.setText(Integer.toString(mWeatherData[position].LowTemp)+"°");
        holder.highTempView.setText(Integer.toString(mWeatherData[position].HighTemp)+"°");
        holder.descriptionView.setText(mWeatherData[position].Summary);
    }

    @Override
    public int getItemViewType(int position) {
        if (mUseTodayLayout && position == 0) {
            return VIEW_TYPE_TODAY;
        } else {
            return VIEW_TYPE_FUTURE_DAY;
        }
    }

    @Override
    public int getItemCount() {
        if(mWeatherData != null) {
            return mWeatherData.length;
        }
        return 0;
    }

    public void setWeatherData(WeatherDataHolder[] data){
        mWeatherData = data;
    }

    class ForecastAdapterViewHolder extends RecyclerView.ViewHolder {
        final ImageView iconView;
        final TextView dateView;
        final TextView descriptionView;
        final TextView highTempView;
        final TextView lowTempView;
        public ForecastAdapterViewHolder(View itemView) {
            super(itemView);
            iconView = (ImageView) itemView.findViewById(R.id.weather_icon);
            dateView = (TextView) itemView.findViewById(R.id.date_text);
            descriptionView = (TextView) itemView.findViewById(R.id.weather_discription);
            highTempView = (TextView) itemView.findViewById(R.id.high_temp);
            lowTempView = (TextView) itemView.findViewById(R.id.min_temp);
        }
    }
}
