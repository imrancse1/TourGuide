package com.example.mytourguide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MapActivity extends AppCompatActivity {
    private Spinner location, distance;
    private TextView mess;
    private Button search;
    private String selectedplace = "";
    private String getSelecteddistance= "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        location=findViewById(R.id.placeSpinner);
        distance=findViewById(R.id.distanceSpinner);
        search=findViewById(R.id.locSearchBtn);
        mess= findViewById(R.id.hello);

        final String[] locations= getResources().getStringArray(R.array.place);
        final String[] distances= getResources().getStringArray(R.array.distance);
        ArrayAdapter<String> adapter1 =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_dropdown_item_1line,
                        locations);
        ArrayAdapter<String> adapter2 =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_dropdown_item_1line,
                        distances);

        location.setAdapter(adapter1);
        distance.setAdapter(adapter2);
        location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedplace= locations[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        distance.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getSelecteddistance= distances[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mess.setText(selectedplace +"\n"+getSelecteddistance);
                Intent valuepass= new Intent(MapActivity.this, NearbyPlaceActivity.class);
                Bundle extra= new Bundle();
                extra.putString("place", selectedplace);
                extra.putString("distance", getSelecteddistance);
                valuepass.putExtras(extra);
                startActivity(valuepass);
            }
        });
    }
}
