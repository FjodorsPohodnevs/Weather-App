package com.fjodors.weatherapp.presentation.cityDetails;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fjodors.weatherapp.R;
import com.fjodors.weatherapp.data.model.CityWeatherResponse;
import com.fjodors.weatherapp.presentation.base.BaseActivity;

import org.parceler.Parcels;

import butterknife.BindView;

public class CityForecastActivity extends BaseActivity {

    public static final String EXTRA_CITY = "com.fjodors.weatherapp.extras.EXTRA_CITY";

    @BindView(R.id.recycler_view)
    RecyclerView forecastRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_forecast);

        CityWeatherResponse cityWeatherResponse = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_CITY));

        String cityName = cityWeatherResponse.getCity().getName();
        initToolbar(cityName);

        initRecyclerView(cityWeatherResponse);
    }

    private void initToolbar(String cityName) {
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            setTitle(cityName);
        }
    }

    private void initRecyclerView(CityWeatherResponse cityWeatherResponse) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        forecastRecyclerView.setLayoutManager(linearLayoutManager);
        CityForecastAdapter cityForecastAdapter = new CityForecastAdapter(cityWeatherResponse);
        forecastRecyclerView.setAdapter(cityForecastAdapter);
    }
}
