package com.dmytro.lisovyi.earthquakemap.api.storage;

import com.dmytro.lisovyi.earthquakemap.models.Earthquake;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface EarthquakeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void save(List<Earthquake> earthquakes);

    @Query("DELETE from earthquake")
    public void clearTable();

    @Query("SELECT * from earthquake WHERE time > :startTime AND time < :endTime")
    public LiveData<List<Earthquake>> get(long startTime, long endTime);

    @Query("SELECT COUNT() from earthquake WHERE time > :startTime AND time < :endTime")
    public int countData(long startTime, long endTime);

}
