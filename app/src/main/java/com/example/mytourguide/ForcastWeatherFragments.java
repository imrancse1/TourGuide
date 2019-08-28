package com.example.mytourguide;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.mytourguide.Adapters.ForcastAdapter;
import com.example.mytourguide.ForcastWeather.WeatherData;
import com.example.mytourguide.Interfaces.ForcastWeatherAPIService;
import com.example.mytourguide.Interfaces.WeatherLocationListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class ForcastWeatherFragments extends Fragment implements WeatherLocationListener {
    private RecyclerView forcastview;
    public static final String BASE_URL = "http://api.openweathermap.org/";
    public static final String appid= "380199723cebdb85ef2e16cc30cee5b6";
    public static final String units="metric";
    public static final int cnt= 7;
    //double
    private ForcastAdapter adapter;
    public ForcastWeatherFragments() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forcast_weather_fragments, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        forcastview= view.findViewById(R.id.forcastrecycler);


    }


    @Override
    public void getWeatherlocation(double lat, double lon) {
        String lats=String.valueOf(lat);
        String lons = String.valueOf(lon);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        ForcastWeatherAPIService fservice= retrofit.create(ForcastWeatherAPIService.class);
        String endUrl = String.format("data/2.5/forecast?lat=%s&lon=%s&cnt=%d&units=metric&appid=%s",lats,lons, cnt, appid);
        fservice.getWeatherData(endUrl).enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                if (response.isSuccessful()) {
                    WeatherData maindata = response.body();
                    List<com.example.mytourguide.ForcastWeather.List> featureList = maindata.getList();
                    //List<Weather> weatherList =
                    ForcastAdapter adapter = new ForcastAdapter(getActivity(), featureList);
                    LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    forcastview.setLayoutManager(llm);
                    forcastview.setAdapter(adapter);
                    // Toast.makeText(getActivity(), lats, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {

            }
        });
    }

    @Override
    public void getCityname(String cname) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        ForcastWeatherAPIService fservice= retrofit.create(ForcastWeatherAPIService.class);
        String endUrl = String.format("data/2.5/forecast?q=%s&cnt=%d&units=metric&appid=%s",cname, cnt, appid);
        fservice.getWeatherData(endUrl).enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                if (response.isSuccessful()) {
                    WeatherData maindata = response.body();
                    List<com.example.mytourguide.ForcastWeather.List> featureList = maindata.getList();
                    //List<Weather> weatherList =
                    ForcastAdapter adapter = new ForcastAdapter(getActivity(), featureList);
                    LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    forcastview.setLayoutManager(llm);
                    forcastview.setAdapter(adapter);
                    // Toast.makeText(getActivity(), lats, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {

            }
        });

    }
}
