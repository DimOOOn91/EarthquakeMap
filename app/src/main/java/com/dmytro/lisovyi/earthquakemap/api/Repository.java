package com.dmytro.lisovyi.earthquakemap.api;

import com.dmytro.lisovyi.earthquakemap.models.Earthquake;

import java.util.List;

import androidx.lifecycle.LiveData;

public interface Repository {

    LiveData<List<Earthquake>> getEarthquakes(long startTime, long endTime);

    void refreshEarthquakes(long startTime, long endTime);

}
