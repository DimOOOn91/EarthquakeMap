package com.dmytro.lisovyi.earthquakemap.api;

import android.app.Application;
import android.util.Log;

import com.dmytro.lisovyi.earthquakemap.BuildConfig;
import com.dmytro.lisovyi.earthquakemap.api.dto.ApiResponce;
import com.dmytro.lisovyi.earthquakemap.api.dto.EarthquakeDto;
import com.dmytro.lisovyi.earthquakemap.api.dto.EarthquakeProperties;
import com.dmytro.lisovyi.earthquakemap.api.storage.EarthquakeDao;
import com.dmytro.lisovyi.earthquakemap.api.storage.EarthquakeDatabase;
import com.dmytro.lisovyi.earthquakemap.models.Earthquake;
import com.dmytro.lisovyi.earthquakemap.utils.DateTimeUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import androidx.lifecycle.LiveData;
import androidx.room.Room;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RepositoryImpl implements Repository {

    private final EarthquakeDatabase database;
    private final Retrofit retrofit;
    private final Gson gson = new GsonBuilder().create();

    private final EarthquakeApi earthquakeApi;
    private final EarthquakeDao earthquakeDao;
    private final Executor executor;

    public RepositoryImpl(Application application, String baseUrl, Executor executor) {
        database = Room.databaseBuilder(application,
                EarthquakeDatabase.class, "Earthquake.db")
                .build();

        retrofit = new Retrofit.Builder()
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(baseUrl)
                .build();

        this.earthquakeApi = retrofit.create(EarthquakeApi.class);
        this.earthquakeDao = database.earthquakeDao();
        this.executor = executor;
    }

    private OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(
                    new HttpLoggingInterceptor.Logger() {
                        @Override
                        public void log(String message) {
                            Log.i("OkHttp", message);
                        }
                    });

            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClientBuilder.addInterceptor(loggingInterceptor);
        }
        return httpClientBuilder.build();
    }

    @Override
    public LiveData<List<Earthquake>> getEarthquakes(long startTime, long endTime) {
        refreshEarthquakes(startTime, endTime);
        return earthquakeDao.get(startTime, endTime);
    }

    @Override
    public void refreshEarthquakes(final long startTime, final long endTime) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                String start = DateTimeUtils.getFormattedDate(startTime);
                String end = DateTimeUtils.getFormattedDate(endTime);
                earthquakeApi.getEarthqakes(start, end).enqueue(new Callback<ApiResponce>() {
                    @Override
                    public void onResponse(Call<ApiResponce> call, final Response<ApiResponce> response) {
                        if (response.isSuccessful()
                                && response.body() != null
                                && response.body().getEarthquakeDtoList().size() > 0) {
                            executor.execute(new Runnable() {
                                @Override
                                public void run() {
                                    if (earthquakeDao.countData(startTime, endTime) == 0) {
                                        // remove data from previous periods
                                        earthquakeDao.clearTable();
                                    }
                                    earthquakeDao.save(getEarthquakesFromResponce(response.body()));
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponce> call, Throwable t) {
                    }
                });
            }
        });
    }

    private List<Earthquake> getEarthquakesFromResponce(ApiResponce apiResponce) {
        List<EarthquakeDto> dtoList = apiResponce.getEarthquakeDtoList();
        if (dtoList == null || dtoList.isEmpty()) {
            return new ArrayList<>();
        }

        List<Earthquake> earthquakes = new ArrayList<>(dtoList.size());
        for (EarthquakeDto dto : dtoList) {
            EarthquakeProperties properties = dto.getProperties();
            double[] coordinates = dto.getGeometry().getCoordinates();
            earthquakes.add(
                    new Earthquake(
                            dto.getId(),
                            properties.getTime(),
                            properties.getPlace(),
                            properties.getMagnitude(),
                            coordinates[1],
                            coordinates[0])
            );
        }
        return earthquakes;
    }

}
