package com.example.mytourguide;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mytourguide.Interfaces.FragmentTransectionService;

public class MyTourGuideActivity extends AppCompatActivity implements LoginFragments.LoginInterface, FragmentTransectionService {
    private FragmentManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tour_guide);
        manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        LoginFragments loginFragments= new LoginFragments();
        ft.add(R.id.fragmentscontainer, loginFragments);
        ft.commit();
    }


    @Override
    public void loginSuccess() {
        manager= getSupportFragmentManager();
        FragmentTransaction ft= manager.beginTransaction();
        EventDashBoardFragments eventDashBoardFragments= new EventDashBoardFragments();
        ft.replace(R.id.fragmentscontainer, eventDashBoardFragments);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void SignupPage() {
        manager= getSupportFragmentManager();
        FragmentTransaction ft= manager.beginTransaction();
        SignUpFragments signUpFragments= new SignUpFragments();
        ft.replace(R.id.fragmentscontainer, signUpFragments);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void EventAddBtn() {
        manager= getSupportFragmentManager();
        FragmentTransaction ft= manager.beginTransaction();
        EventAddFragments addFragments= new EventAddFragments();
        ft.replace(R.id.fragmentscontainer, addFragments);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void getEventDetails(String EventId, String amount) {
        Bundle bundle = new Bundle();
        bundle.putString("EventId", EventId);
        bundle.putString("budget", amount);
        manager= getSupportFragmentManager();
        FragmentTransaction ft= manager.beginTransaction();
        EventDetailsFragments eventDetailsFragments = new EventDetailsFragments();
        eventDetailsFragments.setArguments(bundle);
        ft.replace(R.id.fragmentscontainer, eventDetailsFragments);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void signupSuccess() {
        manager= getSupportFragmentManager();
        FragmentTransaction ft= manager.beginTransaction();
        EventDashBoardFragments eventDashBoardFragments= new EventDashBoardFragments();
        ft.replace(R.id.fragmentscontainer, eventDashBoardFragments);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void eventAddComplete() {
        manager= getSupportFragmentManager();
        FragmentTransaction ft= manager.beginTransaction();
        EventDashBoardFragments eventDashBoardFragments= new EventDashBoardFragments();
        ft.replace(R.id.fragmentscontainer, eventDashBoardFragments);
        ft.addToBackStack(null);
        ft.commit();
    }


}

