package com.fjodors.weatherapp.presentation.base;

public interface MvpPresenter<V> {
    void attachView(V mvpView);

    void detachView();
}
