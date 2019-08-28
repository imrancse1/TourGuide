package com.example.mytourguide;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mytourguide.Adapters.LocationAdapter;
import com.example.mytourguide.Interfaces.NearbyService;
import com.example.mytourguide.PojoClasses.RetrofitClient;
import com.example.mytourguide.nearby.NearbyLocation;
import com.example.mytourguide.nearby.Result;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NearbyPlaceActivity extends AppCompatActivity {
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION =111;
    private TextView value;
    private RecyclerView recyclerView;
    private LocationAdapter adapter;
    private FusedLocationProviderClient client;
    //private boolean mLocationPermissionGranted=true;
    private double latitude = 0.0;
    private double longitude = 0.0;
    private int distance= 0;
    private String places= "";
    private boolean isPermissionGranted= false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_place);

        value=findViewById(R.id.getvalue);
        recyclerView=findViewById(R.id.nearRecyler);
        client= LocationServices.getFusedLocationProviderClient(this);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        places = extras.getString("place");
        String dis = extras.getString("distance");
        distance= Integer.parseInt(dis);
        value.setText(places+"\n"+dis);
        checkLocationPermission();
    }
    @Override
    protected void onResume() {
        super.onResume();
        checkLocationPermission();

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
                    latitude= location.getLatitude();
                    longitude=location.getLongitude();
                    final String APIKEY= "AIzaSyB6IZ6J3XLAGSvdNR13ya82VSH23yY-QLI";
                    String endUrl=String.format("place/nearbysearch/json?location=%f,%f&radius=%d&type=%s&key=%s",
                            latitude, longitude,distance,places, APIKEY);
                    NearbyService service = RetrofitClient.getClient().create(NearbyService.class);
                    service.getNearbyplaces(endUrl).enqueue(new Callback<NearbyLocation>() {
                        @Override
                        public void onResponse(Call<NearbyLocation> call, Response<NearbyLocation> response) {
                            NearbyLocation location = response.body();
                            List<Result> resultList = location.getResults();
                            LocationAdapter adapter = new LocationAdapter(NearbyPlaceActivity.this, resultList);
                            LinearLayoutManager llm = new LinearLayoutManager(NearbyPlaceActivity.this);
                            llm.setOrientation(LinearLayoutManager.VERTICAL);
                            recyclerView.setLayoutManager(llm);
                            recyclerView.setAdapter(adapter);
                        }

                        @Override
                        public void onFailure(Call<NearbyLocation> call, Throwable t) {

                        }
                    });



                    Toast.makeText(NearbyPlaceActivity.this, "lats"+latitude, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


}

