package com.xpadro.testingwebapp.city;

import com.xpadro.testingwebapp.city.domain.CityData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CityController {

    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping(value = "/cities", produces = "application/json")
    public List<CityData> getCities() {
        return cityService.findAll();
    }
}
