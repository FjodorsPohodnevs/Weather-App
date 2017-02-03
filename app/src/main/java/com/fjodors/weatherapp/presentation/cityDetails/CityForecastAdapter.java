package com.fjodors.weatherapp.presentation.cityDetails;

import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fjodors.weatherapp.R;
import com.fjodors.weatherapp.Utils.BitmapUtils;
import com.fjodors.weatherapp.Utils.DateUtil;
import com.fjodors.weatherapp.Utils.TimeUtil;
import com.fjodors.weatherapp.data.model.CityWeatherResponse;
import com.fjodors.weatherapp.model.WeatherData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CityForecastAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int FORECAST = 0;
    private final int DATE = 1;

    private List<WeatherData> weatherDataList;

    public CityForecastAdapter(CityWeatherResponse cityWeatherResponse) {
        this.weatherDataList = cityWeatherResponse.getWeatherDataList();
    }

    @Override
    public int getItemViewType(int position) {

        WeatherData currentWeather = weatherDataList.get(position);
        WeatherData previousWeather;

        // Check if previous day is yesterday to show date view layout
        if (position - 1 > 0) {
            previousWeather = weatherDataList.get(position - 1);

            long dateSeconds = currentWeather.getDate();
            long dateSecondsPrevious = previousWeather.getDate();

            if (!DateUtil.isSameDay(dateSeconds, dateSecondsPrevious)) {
                return DATE;
            }

            // By default show date at first position
        } else if (position == 0) {
            return DATE;
        }
        return FORECAST;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == FORECAST) {
            View weatherView = inflater.inflate(R.layout.item_forecast, parent, false);
            viewHolder = new WeatherViewHolder(weatherView);
        } else {
            View dateView = inflater.inflate(R.layout.item_forecast_date, parent, false);
            viewHolder = new WeatherDateViewHolder(dateView);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        WeatherData currentWeather = weatherDataList.get(position);

        long dateSeconds = currentWeather.getDate();
        //Date object need milliseconds, so we need to transform date seconds to milliseconds
        Long dateMilliSeconds = TimeUtil.secondsToMilliSeconds(dateSeconds);

        if (holder.getItemViewType() == FORECAST) {
            WeatherViewHolder weatherHolder = (WeatherViewHolder) holder;

            String windSpeed = String.valueOf(currentWeather.getWind().getSpeed()) + " m/s";
            BitmapDrawable windDirectionIcon = BitmapUtils.getRotatedWindDirectionIcon(currentWeather.getWind().getDeg(),
                    holder.itemView.getContext());
            String time = DateUtil.formatTime(dateMilliSeconds);

            weatherHolder.windDirection.setImageDrawable(windDirectionIcon);
            weatherHolder.windSpeed.setText(windSpeed);
            weatherHolder.date.setText(time);

        } else {
            WeatherDateViewHolder dateHolder = (WeatherDateViewHolder) holder;
            String date = DateUtil.formatDate(dateMilliSeconds);
            dateHolder.date.setText(date);
        }
    }

    @Override
    public int getItemCount() {
        if (weatherDataList != null) {
            return weatherDataList.size();
        } else {
            return 0;
        }
    }

    public static class WeatherViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.wind_direction)
        ImageView windDirection;
        @BindView(R.id.wind_speed)
        TextView windSpeed;
        @BindView(R.id.date)
        TextView date;

        View view;

        public WeatherViewHolder(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
        }
    }

    public static class WeatherDateViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.date)
        TextView date;

        public WeatherDateViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}