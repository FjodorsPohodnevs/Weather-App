package com.fjodors.weatherapp.presentation.cityList;

import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fjodors.weatherapp.R;
import com.fjodors.weatherapp.Utils.BitmapUtils;
import com.fjodors.weatherapp.data.model.CityWeatherResponse;
import com.fjodors.weatherapp.model.WeatherData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavouriteCitiesAdapter extends RecyclerView.Adapter<FavouriteCitiesAdapter.WeatherViewHolder> {

    private List<CityWeatherResponse> cityWeatherList;
    private OnClickListener onClickListener;

    public FavouriteCitiesAdapter(OnClickListener onClickListener) {
        cityWeatherList = new ArrayList<>();
        this.onClickListener = onClickListener;
    }

    @Override
    public WeatherViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city, parent, false);
        return new WeatherViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final WeatherViewHolder holder, final int position) {

        CityWeatherResponse cityWeatherResponse = cityWeatherList.get(position);

        //Get current weather to show with city name
        WeatherData currentWeather = cityWeatherResponse.getWeatherDataList().get(0);

        String windSpeed = String.valueOf(currentWeather.getWind().getSpeed()) + " m/s";
        String cityName = cityWeatherResponse.getCity().getName();
        BitmapDrawable windDirectionIcon = BitmapUtils.getRotatedWindDirectionIcon(currentWeather.getWind().getDeg(),
                holder.itemView.getContext());

        holder.windDirection.setImageDrawable(windDirectionIcon);
        holder.windSpeed.setText(windSpeed);
        holder.city.setText(cityName);

        holder.itemView.setOnClickListener(view -> onClickListener.onClick(cityWeatherResponse));
    }

    public void addCity(CityWeatherResponse cityWeatherResponse) {

        int itemPosition = getPosition(cityWeatherResponse);

        //Update item if already is in the list to evade duplicates, otherwise add new item
        if (itemPosition != -1) {
            notifyItemChanged(getPosition(cityWeatherResponse));
        } else {
            cityWeatherList.add(cityWeatherResponse);
            notifyItemInserted(cityWeatherList.size() - 1);
        }
    }


    private int getPosition(CityWeatherResponse cityWeatherResponse) {
        return cityWeatherList.indexOf(cityWeatherResponse);
    }


    @Override
    public int getItemCount() {
        if (cityWeatherList != null) {
            return cityWeatherList.size();
        } else {
            return 0;
        }
    }

    public interface OnClickListener {
        void onClick(CityWeatherResponse cityWeatherResponse);
    }


    public static class WeatherViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.wind_direction)
        ImageView windDirection;
        @BindView(R.id.wind_speed)
        TextView windSpeed;
        @BindView(R.id.city)
        TextView city;

        View v;

        public WeatherViewHolder(View v) {
            super(v);
            this.v = v;
            ButterKnife.bind(this, v);
        }
    }
}