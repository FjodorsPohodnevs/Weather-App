package com.fjodors.weatherapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Wind {

    @SerializedName("speed")
    @Expose
    double speed;
    @SerializedName("deg")
    @Expose
    double deg;

    public double getSpeed() {
        return speed;
    }

    public double getDeg() {
        return deg;
    }
}