package com.example.mytourguide;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

public class SingleMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double deslat=0.0;
    private double deslon=0.0;
    private String lname="";
    private TextView nameTv, placeTv;
    private ImageView locimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        nameTv=findViewById(R.id.placenameTV);
        placeTv=findViewById(R.id.placeTV);
        locimage=findViewById(R.id.placeImV);
        Intent intent=getIntent();
        Bundle extra= intent.getExtras();
        deslat=extra.getDouble("lats");
        deslon=extra.getDouble("lons");
        lname=extra.getString("name");
        placeTv.setText(lname);
        nameTv.setText(extra.getString("road"));
        final String APi="AIzaSyBxIa_N-bqyH6P687uXWEwz2FW2JJaYD1I";
        String imgurl=String.format("https://maps.googleapis.com/maps/api/place/photo?photoreference=%s&maxwidth=600&key=%s",
                extra.getString("imref"), APi );
        Picasso.get().
                load(imgurl)
                .into(locimage);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(deslat, deslon);
        mMap.addMarker(new MarkerOptions().position(sydney).title(lname));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 14f));
    }
}
