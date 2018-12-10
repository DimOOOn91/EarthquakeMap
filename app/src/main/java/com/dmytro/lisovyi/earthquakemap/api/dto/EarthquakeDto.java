package com.dmytro.lisovyi.earthquakemap.api.dto;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;


public class EarthquakeDto {

    @SerializedName("id")
    private String id;
    @SerializedName("properties")
    private EarthquakeProperties properties;
    @SerializedName("geometry")
    private EarthquakeGeometry geometry;

    public EarthquakeDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EarthquakeProperties getProperties() {
        return properties;
    }

    public void setProperties(EarthquakeProperties properties) {
        this.properties = properties;
    }

    public EarthquakeGeometry getGeometry() {
        return geometry;
    }

    public void setGeometry(EarthquakeGeometry geometry) {
        this.geometry = geometry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EarthquakeDto that = (EarthquakeDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
