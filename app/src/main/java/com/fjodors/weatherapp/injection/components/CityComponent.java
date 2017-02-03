package com.fjodors.weatherapp.injection.components;

import com.fjodors.weatherapp.injection.ActivityScope;
import com.fjodors.weatherapp.injection.modules.CityModule;
import com.fjodors.weatherapp.presentation.cityList.FavouriteCitiesActivity;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = CityModule.class)
public interface CityComponent {

    void inject(FavouriteCitiesActivity favouriteCitiesActivity);
}
