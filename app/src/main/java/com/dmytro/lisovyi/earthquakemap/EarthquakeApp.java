package com.dmytro.lisovyi.earthquakemap;

import android.app.Application;

import com.dmytro.lisovyi.earthquakemap.api.Repository;
import com.dmytro.lisovyi.earthquakemap.api.RepositoryImpl;

import java.util.concurrent.Executors;

public class EarthquakeApp extends Application implements ComponentProvider {

    private Repository repository;

    @Override
    public void onCreate() {
        super.onCreate();

        repository = new RepositoryImpl(this,
                getString(R.string.base_url),
                Executors.newSingleThreadExecutor());
    }

    @Override
    public Repository getRepository() {
        return repository;
    }
}
