package com.fjodors.weatherapp.data;

import com.fjodors.weatherapp.data.model.CityWeatherResponse;
import com.fjodors.weatherapp.injection.modules.NetworkModule;

import javax.inject.Inject;

import io.reactivex.Observable;

public class WeatherRepository {

    private ApiService apiService;

    @Inject
    public WeatherRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    /**
     * Returns weather forecast data about specific city
     */
    public Observable<CityWeatherResponse> getCityWeather(String city) {
        return apiService.getCityWeatherData(city, NetworkModule.API_KEY);
    }
}
