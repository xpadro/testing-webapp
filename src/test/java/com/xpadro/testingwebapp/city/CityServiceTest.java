package com.xpadro.testingwebapp.city;

import com.xpadro.testingwebapp.city.domain.City;
import com.xpadro.testingwebapp.city.domain.CityData;
import com.xpadro.testingwebapp.hospital.Hospital;
import com.xpadro.testingwebapp.hospital.HospitalClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {
    private static final String TEST_CITY = "test";

    private City city = new City();
    private Hospital hospital = new Hospital();
    private List<City> cities = singletonList(city);
    private List<Hospital> hospitals = singletonList(hospital);
    private CityData failed = new CityData("fail", emptyList());

    @Mock
    private CityRepository repository;

    @Mock
    private HospitalClient client;

    @InjectMocks
    private CityService service;

    @BeforeEach
    public void setUp() {
        city.setName(TEST_CITY);
    }

    @Test
    void findAllShouldReturnAllCityData() {
        when(repository.findAll()).thenReturn(cities);
        when(client.getHospitals(TEST_CITY)).thenReturn(hospitals);

        List<CityData> result = service.findAll();
        assertThat(result.size(), equalTo(1));
        assertThat(result.get(0).getCityName(), equalTo(TEST_CITY));
        assertThat(result.get(0).getHospitals().size(), equalTo(1));
    }

    @Test
    void findAllShouldReturnEmptyListIfMissing() {
        when(repository.findAll()).thenReturn(emptyList());

        List<CityData> result = service.findAll();
        assertTrue(result.isEmpty());
    }

    @Test
    void findShouldMapAndReturnCityData() {
        when(repository.findByName(TEST_CITY)).thenReturn(Optional.of(city));
        when(client.getHospitals(TEST_CITY)).thenReturn(hospitals);

        Optional<CityData> result = service.find(TEST_CITY);
        assertTrue(result.isPresent());
        assertThat(result.orElse(failed).getCityName(), equalTo(TEST_CITY));
        assertSame(hospitals, result.orElse(failed).getHospitals());
    }

    @Test
    void findShouldReturnEmptyIfNotFound() {
        when(repository.findByName(TEST_CITY)).thenReturn(Optional.empty());

        Optional<CityData> result = service.find(TEST_CITY);
        assertFalse(result.isPresent());
    }
}