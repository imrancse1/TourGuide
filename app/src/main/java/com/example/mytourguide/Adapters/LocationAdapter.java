package com.example.mytourguide.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.mytourguide.R;
import com.example.mytourguide.SingleMapActivity;
import com.example.mytourguide.nearby.Result;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationHolder>{
    private Context context;
    private List<Result> resultList;

    public LocationAdapter(Context context, List<Result> resultList) {
        this.context = context;
        this.resultList = resultList;
    }

    @NonNull
    @Override
    public LocationHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new LocationHolder(LayoutInflater.from(context)
                .inflate(R.layout.location_row, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LocationHolder holder, int i) {
        final Result result= resultList.get(i);
        holder.name.setText(result.getName());
        holder.singlerow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapload= new Intent(context, SingleMapActivity.class);
                Bundle extra= new Bundle();
                extra.putDouble("lats",result.getGeometry().getLocation().getLat());
                extra.putDouble("lons",result.getGeometry().getLocation().getLng());
                extra.putString("name", result.getName());
                extra.putString("imref", result.getPhotos().get(0).getPhotoReference());
                extra.putString("road", result.getVicinity());
                mapload.putExtras(extra);
                context.startActivity(mapload);
            }
        });
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    class LocationHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private CardView singlerow;

        public LocationHolder(@NonNull View itemView) {
            super(itemView);
            name= itemView.findViewById(R.id.NameTV);
            singlerow=itemView.findViewById(R.id.singlemap);

        }
    }
}
