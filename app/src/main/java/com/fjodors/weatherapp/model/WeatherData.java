package com.fjodors.weatherapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.io.Serializable;

@Parcel
public class WeatherData {

    @SerializedName("dt")
    @Expose
    long date;

    @SerializedName("wind")
    @Expose
    Wind wind;

    public Wind getWind() {
        return wind;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}