package com.dmytro.lisovyi.earthquakemap.models;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Earthquake {

    @PrimaryKey
    @NonNull
    private String id;
    private long time;
    private String place;
    private double magnitude;
    private double latitude;
    private double longitude;

    public Earthquake(@NonNull String id, long time, String place,
                      double magnitude, double latitude, double longitude) {
        this.id = id;
        this.time = time;
        this.place = place;
        this.magnitude = magnitude;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public long getTime() {
        return time;
    }

    public String getPlace() {
        return place;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Earthquake that = (Earthquake) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
