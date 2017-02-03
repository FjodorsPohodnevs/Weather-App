package com.fjodors.weatherapp.injection.components;

import com.fjodors.weatherapp.injection.modules.AppModule;
import com.fjodors.weatherapp.injection.modules.CityModule;
import com.fjodors.weatherapp.injection.modules.NetworkModule;
import com.fjodors.weatherapp.injection.modules.PreferencesManagerModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class,
        NetworkModule.class,
        PreferencesManagerModule.class
})
public interface AppComponent {

    CityComponent plus(CityModule cityModule);
}
