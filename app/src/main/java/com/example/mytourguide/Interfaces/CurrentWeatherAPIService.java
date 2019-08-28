package com.example.mytourguide.Interfaces;

import com.example.mytourguide.currentWeather.WeatherCurrentData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface CurrentWeatherAPIService {
    @GET
    Call<WeatherCurrentData> getWeatherdetails(@Url String endurl);
}
