package com.example.mytourguide.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.mytourguide.ForcastWeather.Weather;
import com.example.mytourguide.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class ForcastAdapter extends RecyclerView.Adapter<ForcastAdapter.ForcastHolder>{
   private Context context;
    private List<com.example.mytourguide.ForcastWeather.List> mainFeatures;
    private  List<Weather> weatherList;

    public ForcastAdapter(Context context, List<com.example.mytourguide.ForcastWeather.List> mainFeatures
                              ) {
        this.context = context;
        this.mainFeatures = mainFeatures;


    }

    @NonNull
    @Override
    public ForcastHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ForcastHolder(LayoutInflater.from(context)
                .inflate(R.layout.forcast_row, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ForcastHolder holder, int i) {
        final com.example.mytourguide.ForcastWeather.List feature= mainFeatures.get(i);
        // final Feature feature = mainFeatures.get(i);
        holder.max.setText(String.valueOf(feature.getMain().getTempMin()));
        holder.min.setText(String.valueOf(feature.getMain().getTempMax()));
        int v= feature.getDt();
        String dateformat= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(v*10000));
        holder.date.setText(feature.getDtTxt());
        //holder.description.setText(feature.getWeather().get(0).getMain());
        holder.message.setText(feature.getWeather().get(0).getDescription());
        Picasso.get().
               load("http://openweathermap.org/img/w/"+ feature.getWeather().get(0).getIcon()+".png")
                .into(holder.imagetv);
    }

    @Override
    public int getItemCount() {
        return mainFeatures.size();
    }

    class ForcastHolder extends RecyclerView.ViewHolder {
        private TextView max, min, date, message;
        private ImageView imagetv;
        public ForcastHolder(@NonNull View itemView) {
            super(itemView);
            max= itemView.findViewById(R.id.maxTemp);
            min= itemView.findViewById(R.id.minTemp);
            date = itemView.findViewById(R.id.dateTV);
            message= itemView.findViewById(R.id.messagetv);
            imagetv= itemView.findViewById(R.id.forcastimageTV);
        }
    }
}
