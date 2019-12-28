package com.xpadro.testingwebapp.city;

import com.xpadro.testingwebapp.city.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
}
