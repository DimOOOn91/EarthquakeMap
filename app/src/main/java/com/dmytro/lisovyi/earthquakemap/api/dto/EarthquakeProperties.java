package com.dmytro.lisovyi.earthquakemap.api.dto;

import com.google.gson.annotations.SerializedName;

public class EarthquakeProperties {

    @SerializedName("time")
    private long time;
    @SerializedName("place")
    private String place;
    @SerializedName("mag")
    private double magnitude;

    public EarthquakeProperties() {
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

}
