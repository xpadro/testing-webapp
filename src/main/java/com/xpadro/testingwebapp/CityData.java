package com.xpadro.testingwebapp;

import java.util.List;

public class CityData {
    private final String cityName;
    private final List<Hospital> hospitals;

    public CityData(String cityName, List<Hospital> hospitals) {
        this.cityName = cityName;
        this.hospitals = hospitals;
    }

    public String getCityName() {
        return cityName;
    }

    public List<Hospital> getHospitals() {
        return hospitals;
    }
}
