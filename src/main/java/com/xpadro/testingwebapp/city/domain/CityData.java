package com.xpadro.testingwebapp.city.domain;

import com.xpadro.testingwebapp.hospital.Hospital;

import java.io.Serializable;
import java.util.List;

public class CityData implements Serializable {
    private String cityName;
    private List<Hospital> hospitals;

    public CityData() {
    }

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
