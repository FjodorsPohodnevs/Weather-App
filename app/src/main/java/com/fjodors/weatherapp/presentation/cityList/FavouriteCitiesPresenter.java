package com.fjodors.weatherapp.presentation.cityList;

import com.fjodors.weatherapp.data.PreferencesManager;
import com.fjodors.weatherapp.data.WeatherRepository;
import com.fjodors.weatherapp.presentation.base.BasePresenter;

import java.util.Set;

import javax.inject.Inject;

import io.reactivex.Scheduler;

public class FavouriteCitiesPresenter extends BasePresenter<FavouriteCitiesContract.View> implements FavouriteCitiesContract.Presenter {

    private final Scheduler mainScheduler;
    private final Scheduler ioScheduler;

    private WeatherRepository weatherRepository;
    private PreferencesManager preferencesManager;

    @Inject
    public FavouriteCitiesPresenter(WeatherRepository weatherRepository,
                                    PreferencesManager preferencesManager,
                                    Scheduler ioScheduler,
                                    Scheduler mainScheduler) {
        this.weatherRepository = weatherRepository;
        this.preferencesManager = preferencesManager;
        this.mainScheduler = mainScheduler;
        this.ioScheduler = ioScheduler;
    }

    @Override
    public void fetchCityWeatherData(String city) {
        getView().showProgress();
        weatherRepository.getCityWeather(city)
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
                .subscribe(weatherResponse -> {
                    String cityName = weatherResponse.getCity().getName();
                    preferencesManager.addFavourite(cityName);

                    getView().showWeatherData(weatherResponse);
                    getView().hideProgress();
                }, e -> {
                    getView().showError(e);
                    getView().hideProgress();
                });
    }

    @Override
    public void getFavouriteCityList() {
        Set<String> cityList = preferencesManager.getFavorites();
        if (cityList != null) {
            for (String city : cityList) {
                fetchCityWeatherData(city);
            }
        } else {
            //If no data show message to add favourite city
            getView().showEmptyMessage();
        }
    }
}
