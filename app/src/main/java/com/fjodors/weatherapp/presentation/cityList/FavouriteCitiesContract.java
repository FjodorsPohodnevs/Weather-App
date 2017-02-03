package com.fjodors.weatherapp.presentation.cityList;

import com.fjodors.weatherapp.data.model.CityWeatherResponse;
import com.fjodors.weatherapp.presentation.base.MvpPresenter;

public interface FavouriteCitiesContract {

    interface View {
        void showWeatherData(CityWeatherResponse cityWeatherResponse);

        void showProgress();

        void hideProgress();

        void showEmptyMessage();

        void hideEmptyMessage();

        void showError(Throwable e);
    }

    interface Presenter extends MvpPresenter<View> {
        void fetchCityWeatherData(String city);

        void getFavouriteCityList();
    }
}
