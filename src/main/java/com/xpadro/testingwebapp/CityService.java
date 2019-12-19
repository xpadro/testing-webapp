package com.xpadro.testingwebapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    private final CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public void insert(City city) {
        cityRepository.save(city);
    }

    public List<City> findAll() {
        return cityRepository.findAll();
    }
}
