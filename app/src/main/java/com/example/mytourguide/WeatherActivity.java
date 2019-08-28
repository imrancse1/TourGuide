package com.example.mytourguide;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.widget.Toast;

import com.example.mytourguide.Interfaces.WeatherLocationListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class WeatherActivity extends AppCompatActivity {
    private SearchView searchView;
    private TabLayout tab;
    private ViewPager viewPager;
    private WeatherPagerAdapter adapter;
    private boolean isPermissionGranted= false;
    private FusedLocationProviderClient client;
    private double latitude = 0.0;
    private double longitude = 0.0;
    private WeatherLocationListener listener;
    private WeatherLocationListener listenercurrent;
    private WeatherLocationListener listenertwo;
    private String city =null;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        client= LocationServices.getFusedLocationProviderClient(this);
        tab= findViewById(R.id.tablayout);
        viewPager= findViewById(R.id.viewpager);
        tab.addTab(tab.newTab().setText("current"));
        tab.addTab(tab.newTab().setText("Forcast"));
        adapter= new WeatherPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tab.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
        Intent intent= getIntent();
        city = intent.getStringExtra(SearchManager.QUERY);
        //listener.getCityname(city);
        Toast.makeText(this, city, Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        checkLocationPermission();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        searchView= (SearchView) menu.findItem(R.id.item_search).getActionView();
        SearchManager manager= (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        return true;
    }

    private void checkLocationPermission() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},111);
        } else
        {
            isPermissionGranted= true;
            getDeviceCurrentLocation();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 111) {
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                isPermissionGranted = true;
                getDeviceCurrentLocation();
            }
        }
    }

    private void getDeviceCurrentLocation() {
        if(isPermissionGranted)
        {
            client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location ==null) {

                    }
                    if(city==null)
                    {
                        latitude= location.getLatitude();
                        longitude=location.getLongitude();
                        listener.getWeatherlocation(latitude,longitude);
                        listenercurrent.getWeatherlocation(latitude,longitude);

                    }
                    else {
                        listener.getCityname(city);
                        listenercurrent.getCityname(city);
                    }

                    //Toast.makeText(context, "lats"+latitude, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private class WeatherPagerAdapter extends FragmentPagerAdapter {
        CurrentWeatherFragments currentWeatherFragments;
        ForcastWeatherFragments forcastWeatherFragments;
        public WeatherPagerAdapter(FragmentManager fm) {
            super(fm);
            currentWeatherFragments = new CurrentWeatherFragments();
            forcastWeatherFragments = new ForcastWeatherFragments();
            listener=  forcastWeatherFragments;
            listenercurrent=  currentWeatherFragments;


        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    return currentWeatherFragments;
                case 1:
                    return forcastWeatherFragments;
            }
            return null;
        }

        @Override
        public int getCount() {

            return 2;
        }
    }
}


