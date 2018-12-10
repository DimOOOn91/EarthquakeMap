package com.dmytro.lisovyi.earthquakemap.api.storage;


import com.dmytro.lisovyi.earthquakemap.models.Earthquake;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Earthquake.class}, version = 1)
public abstract class EarthquakeDatabase extends RoomDatabase {

    public abstract EarthquakeDao earthquakeDao();

}
