package com.example.mytourguide;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mytourguide.Adapters.EventListAdapter;
import com.example.mytourguide.Interfaces.FragmentTransectionService;
import com.example.mytourguide.PojoClasses.EventModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventDashBoardFragments extends Fragment {
    private Button eventadd, Weather, Map;
    private RecyclerView eventRecycler;
    private FragmentTransectionService ftservices;
    private DatabaseReference rootRef;
    private DatabaseReference userRef;
    private DatabaseReference eventRef;
    private FirebaseUser user;
    private List<EventModel> eventModelList = new ArrayList<>();
    private EventListAdapter adapter;

    public EventDashBoardFragments() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ftservices= (FragmentTransectionService) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_dash_board_fragments, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        eventadd= view.findViewById(R.id.eventaddbtn);
        eventRecycler= view.findViewById(R.id.eventRecycler);
        Weather= view.findViewById(R.id.WeatherActivityLoadBtn);
        Map= view.findViewById(R.id.MapActivityLoadBtn);
        user = FirebaseAuth.getInstance().getCurrentUser();
        rootRef = FirebaseDatabase.getInstance().getReference();
        userRef = rootRef.child(user.getUid());
        eventRef = userRef.child("Event");
        eventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                eventModelList.clear();
                for (DataSnapshot d : dataSnapshot.getChildren()){
                    EventModel eventModel = d.getValue(EventModel.class);
                    eventModelList.add(eventModel);
                }
                adapter = new EventListAdapter(eventModelList, getActivity());
                eventRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                eventRecycler.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(), MapActivity.class);
                startActivity(intent);
            }
        });
        eventadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               ftservices.EventAddBtn();
            }
        });
        Weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), WeatherActivity.class));
            }
        });
    }
}
