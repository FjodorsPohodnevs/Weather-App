package com.fjodors.weatherapp.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

public class PreferencesManager {

    public static final String PREFS_NAME = "PREF_FAVOURITE_CITY";
    public static final String FAVORITES = "FAVOURITE_CITY_LIST";

    private Context context;

    @Inject
    public PreferencesManager(Context context) {
        this.context = context;
    }

    /**
     * Save set of favorites city names to preference file
     */
    public void saveFavorites(Set<String> cityList) {
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.putStringSet(FAVORITES, cityList);

        editor.apply();
    }

    /**
     * Save favorite city name to preference file
     */
    public void addFavourite(String city) {
        Set<String> cityList = getFavorites();
        if (cityList == null)
            cityList = new HashSet<>();
        cityList.add(city);
        saveFavorites(cityList);
    }

    /**
     * Returns list of all saved city names
     */
    public Set<String> getFavorites() {
        SharedPreferences settings;
        Set<String> cityList;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            cityList = settings.getStringSet(FAVORITES, null);
            if (cityList != null) {
                cityList = new HashSet<>(cityList);
            }
        } else {
            return null;
        }

        return cityList;
    }
}
