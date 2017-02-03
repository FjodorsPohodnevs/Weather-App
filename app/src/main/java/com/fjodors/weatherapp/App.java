package com.fjodors.weatherapp;

import android.app.Application;
import android.content.Context;

import com.fjodors.weatherapp.injection.components.AppComponent;
import com.fjodors.weatherapp.injection.components.DaggerAppComponent;
import com.fjodors.weatherapp.injection.modules.AppModule;
import com.fjodors.weatherapp.injection.modules.NetworkModule;

public class App extends Application {

    private AppComponent appComponent;

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
