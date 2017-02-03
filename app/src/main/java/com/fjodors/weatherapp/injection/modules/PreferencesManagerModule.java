package com.fjodors.weatherapp.injection.modules;

import android.content.Context;

import com.fjodors.weatherapp.data.PreferencesManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PreferencesManagerModule {

    @Provides
    @Singleton
    public PreferencesManager providePreferencesManager(Context context) {
        return new PreferencesManager(context);
    }
}
