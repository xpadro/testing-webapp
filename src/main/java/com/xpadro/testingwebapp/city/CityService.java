package com.xpadro.testingwebapp.city;

import com.xpadro.testingwebapp.city.domain.CityData;
import com.xpadro.testingwebapp.hospital.Hospital;
import com.xpadro.testingwebapp.hospital.HospitalClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

@Service
public class CityService {

    private final CityRepository cityRepository;
    private final HospitalClient hospitalClient;

    @Autowired
    public CityService(CityRepository cityRepository, HospitalClient hospitalClient) {
        this.cityRepository = cityRepository;
        this.hospitalClient = hospitalClient;
    }

    List<CityData> findAll() {
        return cityRepository.findAll()
                .stream()
                .map(city -> getCityHospitals(city.getName()))
                .collect(Collectors.toList());
    }

    CityData find(final String cityName) {
        return cityRepository.findByName(cityName)
                .map(city -> getCityHospitals(city.getName()))
                .orElse(new CityData(cityName, emptyList()));
    }

    private CityData getCityHospitals(String cityName) {
        List<Hospital> hospitals = hospitalClient.getHospitals(cityName);
        return new CityData(cityName, hospitals);
    }
}
