package com.fjodors.weatherapp.data;

import com.fjodors.weatherapp.data.model.CityWeatherResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("forecast")
    Observable<CityWeatherResponse> getCityWeatherData(@Query("q") String city,
                                                       @Query("appid") String appid);
}
