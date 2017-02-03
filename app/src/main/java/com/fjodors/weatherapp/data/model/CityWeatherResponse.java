package com.fjodors.weatherapp.data.model;


import com.fjodors.weatherapp.model.City;
import com.fjodors.weatherapp.model.WeatherData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class CityWeatherResponse {

    @SerializedName("city")
    City city;
    @SerializedName("list")
    @Expose
    public List<WeatherData> weatherDataList;

    public List<WeatherData> getWeatherDataList() {
        return weatherDataList;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CityWeatherResponse that = (CityWeatherResponse) o;

        return city.getName().equalsIgnoreCase(that.city.getName());
    }
}