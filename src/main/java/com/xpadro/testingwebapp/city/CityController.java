package com.xpadro.testingwebapp.city;

import com.xpadro.testingwebapp.city.domain.CityData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping(value = "/cities/{cityName}", produces = "application/json")
    public ResponseEntity<CityData> getCity(@PathVariable final String cityName) {
        return cityService.find(cityName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
