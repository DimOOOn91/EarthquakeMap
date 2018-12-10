package com.dmytro.lisovyi.earthquakemap.api;

import com.dmytro.lisovyi.earthquakemap.api.dto.ApiResponce;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EarthquakeApi {

    @GET("fdsnws/event/1/query?format=geojson")
    Call<ApiResponce> getEarthqakes(@Query("starttime") String startTime,
                                    @Query("endtime") String endTime);

}
