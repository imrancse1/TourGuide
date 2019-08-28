package com.example.mytourguide.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mytourguide.Interfaces.FragmentTransectionService;
import com.example.mytourguide.PojoClasses.EventModel;
import com.example.mytourguide.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventViewHolder>{
    private List<EventModel> eventModelList;
    private Context context;
    private DatabaseReference rootRef;
    private DatabaseReference userRef;
    private DatabaseReference eventRef;
    private FirebaseUser user;
    private FragmentTransectionService ftservice;

    public EventListAdapter(List<EventModel> eventModelList, Context context) {
        this.eventModelList = eventModelList;
        this.context = context;
        user = FirebaseAuth.getInstance().getCurrentUser();
        rootRef = FirebaseDatabase.getInstance().getReference();
        userRef = rootRef.child(user.getUid());
        eventRef = userRef.child("Event");
        ftservice= (FragmentTransectionService) context;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.event_row, viewGroup, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int i) {
        long today= System.currentTimeMillis();

        final EventModel eventModel = eventModelList.get(i);
        holder.EventName.setText(eventModel.getEventName());
        holder.CreatedDate.setText(eventModel.getPlanDate());
        holder.JourneyDate.setText(eventModel.getJourneyDate());


        long millisecondsFromNow =  eventModel.getTime()-today;
        long second= millisecondsFromNow/1000;
        long minute= second/60;
        long hr= minute/60;
        double days= hr/24;
        int dayss= (int) days;
        holder.RemainDate.setText(""+dayss+" Days Remain");
        Toast.makeText(context, "Milliseconds to future date="+millisecondsFromNow, Toast.LENGTH_SHORT).show();
        //Toast.makeText(context, eventModel.getJourneyDate(), Toast.LENGTH_SHORT).show();
        holder.cardrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ftservice.getEventDetails(eventModel.getEventId(), eventModel.getBudget());
                //Toast.makeText(context, eventModel.getEventId(), Toast.LENGTH_LONG).show();
            }
        });
        holder.removeevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = eventModel.getEventId();
                eventRef.child(id).removeValue();
            }
        });


    }

    @Override
    public int getItemCount() {
        return eventModelList.size();
    }

    class EventViewHolder extends RecyclerView.ViewHolder {
      private TextView EventName, CreatedDate, JourneyDate, RemainDate;
      private CardView cardrow;
      private Button removeevent;
      public EventViewHolder(@NonNull View itemView) {
          super(itemView);
          EventName= itemView.findViewById(R.id.EventNameTV);
          CreatedDate= itemView.findViewById(R.id.CreatedDateTV);
          JourneyDate= itemView.findViewById(R.id.JourneyDateTV);
          cardrow= itemView.findViewById(R.id.eventCard);
          RemainDate= itemView.findViewById(R.id.warningMessage);
          removeevent=itemView.findViewById(R.id.deleterow);
      }
  }
}
