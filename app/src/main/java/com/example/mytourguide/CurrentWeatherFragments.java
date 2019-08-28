package com.example.mytourguide;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.mytourguide.Interfaces.CurrentWeatherAPIService;
import com.example.mytourguide.Interfaces.WeatherLocationListener;
import com.squareup.picasso.Picasso;
import com.example.mytourguide.currentWeather.WeatherCurrentData;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentWeatherFragments extends Fragment implements WeatherLocationListener {
    public static final String BASE_URL = "https://api.openweathermap.org/";
    public static final String appid= "4942fc690413b0cc3321b07256fe0df5";
    public static final String units="metric";
    private TextView temp, max, min, humidity,date, place, test;
    private Button celcius, farenhite;
    private  ImageView pic;
    public CurrentWeatherFragments() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_current_weather_fragments, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        temp = view.findViewById(R.id.temptv);
        max=view.findViewById(R.id.maxtemptv);
        min= view.findViewById(R.id.mintemptv);
        humidity=view.findViewById(R.id.humiditytv);
        date= view.findViewById(R.id.datetv);
        place=view.findViewById(R.id.placetv);
        celcius=view.findViewById(R.id.cbtn);
        farenhite=view.findViewById(R.id.fbtn);
        pic= view.findViewById(R.id.imagetv);
        test= view.findViewById(R.id.testtv);



    }


    @Override
    public void getWeatherlocation(double lat, double lon) {
         String lats=String.valueOf(lat);
         String lons= String.valueOf(lon);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        CurrentWeatherAPIService weatherAPIService = retrofit.create(CurrentWeatherAPIService.class);
        String endUrl = String.format("data/2.5/weather?lat=%s&lon=%s&units=metric&appid=%s",lats,lons, appid);
        weatherAPIService.getWeatherdetails(endUrl).enqueue(new Callback<WeatherCurrentData>() {
            @Override
            public void onResponse(Call<WeatherCurrentData> call, Response<WeatherCurrentData> response) {
                WeatherCurrentData currentData = response.body();


                temp.setText(String.valueOf(currentData.getMain().getTemp())+getString(R.string.degree_c));
                max.setText(String.valueOf(currentData.getMain().getTempMax()));
                min.setText(String.valueOf(currentData.getMain().getTempMin()));
                humidity.setText(String.valueOf(currentData.getMain().getHumidity()));
                place.setText(currentData.getName());
                int value = currentData.getDt();
                Long d= new Long(value);
                String dates=new SimpleDateFormat("dd/MM/yyyy").format(new Date(d*1000));
                date.setText(dates);

                Picasso.get().
                        load("http://openweathermap.org/img/w/"+ currentData.getWeather().get(0).getIcon()+".png")
                       .into(pic);


            }

            @Override
            public void onFailure(Call<WeatherCurrentData> call, Throwable t) {

            }
        });

    }

    @Override
    public void getCityname(String cname) {
        test.setText(cname);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        CurrentWeatherAPIService weatherAPIService = retrofit.create(CurrentWeatherAPIService.class);
        String endUrl = String.format("data/2.5/weather?q=%s&units=metric&appid=%s",cname, appid);
        weatherAPIService.getWeatherdetails(endUrl).enqueue(new Callback<WeatherCurrentData>() {
            @Override
            public void onResponse(Call<WeatherCurrentData> call, Response<WeatherCurrentData> response) {
                WeatherCurrentData currentData = response.body();


                temp.setText(String.valueOf(currentData.getMain().getTemp())+getString(R.string.degree_c));
                max.setText(String.valueOf(currentData.getMain().getTempMax()));
                min.setText(String.valueOf(currentData.getMain().getTempMin()));
                humidity.setText(String.valueOf(currentData.getMain().getHumidity()));
                place.setText(currentData.getName());
                int value = currentData.getDt();
                Long d= new Long(value);
                String dates=new SimpleDateFormat("dd/MM/yyyy").format(new Date(d*1000));
                date.setText(dates);

                Picasso.get().
                        load("http://openweathermap.org/img/w/"+ currentData.getWeather().get(0).getIcon()+".png")
                        .into(pic);


            }

            @Override
            public void onFailure(Call<WeatherCurrentData> call, Throwable t) {

            }
        });


    }
}
