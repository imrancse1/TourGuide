package com.example.mytourguide;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mytourguide.Interfaces.FragmentTransectionService;

public class MainActivity extends AppCompatActivity  {
    private static int TIME_OUT= 4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent TourIntent = new Intent(MainActivity.this, MyTourGuideActivity.class);
                startActivity(TourIntent);
                finish();
            }
        },TIME_OUT);

    }



}
