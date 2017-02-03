package com.fjodors.weatherapp;

import com.fjodors.weatherapp.data.PreferencesManager;
import com.fjodors.weatherapp.data.WeatherRepository;
import com.fjodors.weatherapp.data.model.CityWeatherResponse;
import com.fjodors.weatherapp.model.City;
import com.fjodors.weatherapp.presentation.cityList.FavouriteCitiesContract;
import com.fjodors.weatherapp.presentation.cityList.FavouriteCitiesPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FavouriteCitiesPresenterTest {

    @Mock
    WeatherRepository weatherRepository;

    @Mock
    FavouriteCitiesContract.View view;

    @Mock
    PreferencesManager preferencesManager;

    private FavouriteCitiesContract.Presenter favouriteCitiesPresenter;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        favouriteCitiesPresenter = new FavouriteCitiesPresenter(weatherRepository,
                preferencesManager,
                Schedulers.trampoline(),
                Schedulers.trampoline());
        favouriteCitiesPresenter.attachView(view);
    }

    @Test
    public void correctCityWeatherDataTest() {
        // given
        CityWeatherResponse cityWeatherResponse = getDummyCityWeatherResponse("Riga");
        when(weatherRepository.getCityWeather(anyString())).thenReturn(Observable.just(cityWeatherResponse));
        // when
        favouriteCitiesPresenter.fetchCityWeatherData("Riga");
        String cityName = cityWeatherResponse.getCity().getName();
        // then
        verify(preferencesManager).addFavourite(cityName);
        verify(view).showWeatherData(cityWeatherResponse);
        verify(view).hideProgress();
    }

    @Test
    public void errorResponseTest() {
        // given
        Throwable e = new Throwable("Error");
        when(weatherRepository.getCityWeather(anyString())).thenReturn(Observable.error(e));
        // when
        favouriteCitiesPresenter.fetchCityWeatherData("Riga");
        // then
        verify(view).showError(e);
        verify(view).hideProgress();
    }

    @Test
    public void loadFavouriteCitiesTest() {
        // given
        CityWeatherResponse cityWeatherResponse = getDummyCityWeatherResponse("Riga");

        Set<String> favouriteCityList = new HashSet<>();
        favouriteCityList.add("Riga");
        favouriteCityList.add("Tallinn");
        when(preferencesManager.getFavorites()).thenReturn(favouriteCityList);
        when(weatherRepository.getCityWeather(anyString())).thenReturn(Observable.just(cityWeatherResponse));
        // when
        favouriteCitiesPresenter.getFavouriteCityList();
        // then
        verify(view, atLeast(1)).showWeatherData(cityWeatherResponse);
        verify(view, atLeast(1)).hideProgress();
    }

    @Test
    public void loadFavouriteCitiesEmptyTest() {
        // given
        when(preferencesManager.getFavorites()).thenReturn(null);
        // when
        favouriteCitiesPresenter.getFavouriteCityList();
        // then
        verify(view).showEmptyMessage();
    }

    private CityWeatherResponse getDummyCityWeatherResponse(String cityName) {
        CityWeatherResponse cityWeatherResponse = new CityWeatherResponse();
        City city = new City();
        city.setName(cityName);
        cityWeatherResponse.setCity(city);
        return cityWeatherResponse;
    }
}
