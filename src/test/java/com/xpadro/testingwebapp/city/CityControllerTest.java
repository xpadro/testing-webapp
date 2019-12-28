package com.xpadro.testingwebapp.city;

import com.xpadro.testingwebapp.city.domain.CityData;
import com.xpadro.testingwebapp.hospital.Hospital;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CityControllerTest {
    private static final String TEST_CITY = "test";

    @Mock
    private CityService service;

    @InjectMocks
    private CityController controller;

    private CityData cityData = new CityData("test", singletonList(new Hospital()));
    private List<CityData> cities = singletonList(cityData);

    @Test
    void getCitiesShouldReturnAllCities() {
        when(service.findAll()).thenReturn(cities);

        List<CityData> result = controller.getCities();
        assertThat(result.size(), equalTo(1));
        assertSame(cityData, result.get(0));
    }

    @Test
    void getCitiesShouldReturnEmptyListIfMissing() {
        when(service.findAll()).thenReturn(emptyList());

        List<CityData> result = controller.getCities();
        assertTrue(result.isEmpty());
    }

    @Test
    void getCityShouldReturnCity() {
        when(service.find(TEST_CITY)).thenReturn(Optional.of(cityData));

        ResponseEntity<CityData> result = controller.getCity(TEST_CITY);
        assertSame(cityData, result.getBody());
    }

    @Test
    void getCityShouldReturnNotFoundIfNonExisting() {
        when(service.find(TEST_CITY)).thenReturn(Optional.empty());

        ResponseEntity<CityData> result = controller.getCity(TEST_CITY);
        assertThat(result.getStatusCodeValue(), equalTo(404));
    }
}