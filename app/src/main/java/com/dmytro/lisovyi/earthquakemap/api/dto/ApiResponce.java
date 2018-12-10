package com.dmytro.lisovyi.earthquakemap.api.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponce {

    @SerializedName("features")
    private List<EarthquakeDto> earthquakeDtoList;

    public ApiResponce() {
    }

    public List<EarthquakeDto> getEarthquakeDtoList() {
        return earthquakeDtoList;
    }

    public void setEarthquakeDtoList(List<EarthquakeDto> earthquakeDtoList) {
        this.earthquakeDtoList = earthquakeDtoList;
    }
}
