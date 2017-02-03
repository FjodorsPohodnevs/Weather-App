package com.fjodors.weatherapp.injection.modules;

import com.fjodors.weatherapp.data.ApiService;
import com.fjodors.weatherapp.data.PreferencesManager;
import com.fjodors.weatherapp.data.WeatherRepository;
import com.fjodors.weatherapp.injection.ActivityScope;
import com.fjodors.weatherapp.presentation.cityList.FavouriteCitiesContract;
import com.fjodors.weatherapp.presentation.cityList.FavouriteCitiesPresenter;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
public class CityModule {

    @Provides
    @ActivityScope
    WeatherRepository provideWeatherRepository(ApiService apiService) {
        return new WeatherRepository(apiService);
    }

    @Provides
    @ActivityScope
    public FavouriteCitiesContract.Presenter provideCityPresenter(WeatherRepository weatherRepository,
                                                                  PreferencesManager preferencesManager) {
        return new FavouriteCitiesPresenter(weatherRepository,
                preferencesManager,
                Schedulers.io(),
                AndroidSchedulers.mainThread());
    }
}
