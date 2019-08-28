package com.example.mytourguide;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mytourguide.Dialog.DateDialogBox;
import com.example.mytourguide.Interfaces.FragmentTransectionService;
import com.example.mytourguide.PojoClasses.EventModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventAddFragments extends Fragment {
    private DatabaseReference rootRef;
    private DatabaseReference userRef;
    private DatabaseReference eventRef;
    private FirebaseUser user;
    private EditText EventName, StartLocation, Destination, Budget;
    private Button Date, CreateEvent;
    private String doj="";
    private long journeydate;
    private FragmentTransectionService service;
    private DatePickerDialog.OnDateSetListener listener;


    public EventAddFragments() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        service= (FragmentTransectionService) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_add_fragments, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventName=view.findViewById(R.id.EventNameET);
        StartLocation= view.findViewById(R.id.StartLocET);
        Destination= view.findViewById(R.id.DestinationET);
        Budget= view.findViewById(R.id.BudgetET);
        Date= view.findViewById(R.id.EventDateBtn);
        CreateEvent= view.findViewById(R.id.CreateEventBtn);
        user = FirebaseAuth.getInstance().getCurrentUser();
        rootRef = FirebaseDatabase.getInstance().getReference();
        userRef = rootRef.child(user.getUid());
        eventRef = userRef.child("Event");
        Date= view.findViewById(R.id.EventDateBtn);
        Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                DatePickerDialog datePickerDialog= new DatePickerDialog(getActivity(), null, 2000, 01, 24);
//                datePickerDialog.show();
                Calendar todaysCalender = Calendar.getInstance();
                int year = todaysCalender.get(Calendar.YEAR);
                int month = todaysCalender.get(Calendar.MONTH);
                int day = todaysCalender.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog =
                        new DatePickerDialog(getActivity(), listener, year, month, day);
                datePickerDialog.show();

            }
        });
         listener =
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        SimpleDateFormat simpleDateFormat =
                                new SimpleDateFormat("EEE, MMM dd, yyyy");
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(i, i1, i2);

                        doj = simpleDateFormat.format(calendar.getTime());
                        long today= System.currentTimeMillis();
                        journeydate= calendar.getTimeInMillis();
                        if(journeydate-today>0) {
                            Date.setText(doj);
                        }
                        else
                        {
                            DialogBox();
                        }
                    }
                };
        CreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //this long shoud be the tour day

                java.util.Date cdate= Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("EEE MMM dd yyyy");
                String name= EventName.getText().toString();
                String Startloc= StartLocation.getText().toString();
                String destination= Destination.getText().toString();
                String date= doj;

                String budget=Budget.getText().toString();
                String E_id= eventRef.push().getKey();
                String CurrentDate=df.format(cdate);
                EventModel eventModel= new EventModel(E_id,name,Startloc,destination,date,budget, CurrentDate,journeydate);
                eventRef.child(E_id).setValue(eventModel)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getContext(), "success", Toast.LENGTH_LONG).show();
                                service.eventAddComplete();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
    }
    public void DialogBox() {
        DateDialogBox dialogBox= new DateDialogBox();
        dialogBox.show(getFragmentManager(), "example");
    }
}
