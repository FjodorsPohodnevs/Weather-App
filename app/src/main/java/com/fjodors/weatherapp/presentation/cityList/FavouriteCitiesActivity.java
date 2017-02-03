package com.fjodors.weatherapp.presentation.cityList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.fjodors.weatherapp.App;
import com.fjodors.weatherapp.R;
import com.fjodors.weatherapp.data.model.CityWeatherResponse;
import com.fjodors.weatherapp.injection.modules.CityModule;
import com.fjodors.weatherapp.presentation.base.BaseActivity;
import com.fjodors.weatherapp.presentation.cityDetails.CityForecastActivity;

import org.parceler.Parcels;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;


public class FavouriteCitiesActivity extends BaseActivity implements FavouriteCitiesContract.View, FavouriteCitiesAdapter.OnClickListener {

    private FavouriteCitiesAdapter favouriteCitiesAdapter;

    @Inject
    FavouriteCitiesContract.Presenter cityPresenter;

    @BindView(R.id.city_recycler_view)
    RecyclerView cityRecyclerView;

    @BindView(R.id.empty_view)
    TextView emptyView;

    @BindView(R.id.view_progress_layout)
    FrameLayout progressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_cities);

        App.get(this)
                .getAppComponent()
                .plus(new CityModule())
                .inject(this);

        cityPresenter.attachView(this);

        initRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cityPresenter.getFavouriteCityList();
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        cityRecyclerView.setLayoutManager(linearLayoutManager);
        favouriteCitiesAdapter = new FavouriteCitiesAdapter(this);
        cityRecyclerView.setAdapter(favouriteCitiesAdapter);
    }

    @OnClick(R.id.fab)
    void openAddFavouriteCityDialog() {

        View view = LayoutInflater.from(this).inflate(R.layout.content_edit_text, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(view)
                .setMessage(R.string.dialog_add_favourite_city_text)
                .setTitle(R.string.dialog_add_favourite_city_title)
                .setPositiveButton(R.string.dialog_confirm_favourite_city, (dialog, id) -> {
                    EditText et = (EditText) view.findViewById(R.id.city);
                    String city = et.getText().toString();
                    cityPresenter.fetchCityWeatherData(city);
                }).setNegativeButton(R.string.dialog_confirm_cancel, (dialog, id) -> {
            //Do nothing
        })
                .create()
                .show();
    }

    @Override
    public void showWeatherData(CityWeatherResponse cityWeatherResponse) {
        favouriteCitiesAdapter.addCity(cityWeatherResponse);
        hideEmptyMessage();
    }

    @Override
    public void showEmptyMessage() {
        emptyView.setVisibility(View.VISIBLE);
        cityRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void hideEmptyMessage() {
        emptyView.setVisibility(View.GONE);
        cityRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgress() {
        progressView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressView.setVisibility(View.GONE);
    }

    @Override
    public void showError(Throwable e) {
        showToast(e.getMessage());
    }

    @Override
    public void onClick(CityWeatherResponse cityWeatherResponse) {
        Intent intent = new Intent(this, CityForecastActivity.class);
        intent.putExtra(CityForecastActivity.EXTRA_CITY, Parcels.wrap(cityWeatherResponse));
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        cityPresenter.detachView();
        super.onDestroy();
    }
}
