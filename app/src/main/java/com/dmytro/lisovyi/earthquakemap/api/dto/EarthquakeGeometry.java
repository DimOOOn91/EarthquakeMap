package com.dmytro.lisovyi.earthquakemap.api.dto;

import com.google.gson.annotations.SerializedName;

public class EarthquakeGeometry {

    @SerializedName("coordinates")
    private double[] coordinates;

    public EarthquakeGeometry() {
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }

}
