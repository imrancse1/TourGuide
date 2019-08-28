package com.example.mytourguide.Interfaces;





import com.example.mytourguide.nearby.NearbyLocation;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface NearbyService {
    @GET
    Call<NearbyLocation>getNearbyplaces(@Url String endUrl);

}
