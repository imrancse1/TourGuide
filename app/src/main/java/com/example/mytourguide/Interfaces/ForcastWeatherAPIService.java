package com.example.mytourguide.Interfaces;



import com.example.mytourguide.ForcastWeather.WeatherData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ForcastWeatherAPIService {

    @GET
    Call<WeatherData> getWeatherData(@Url String endurl);
}
