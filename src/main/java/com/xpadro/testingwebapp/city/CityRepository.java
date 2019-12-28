package com.xpadro.testingwebapp.city;

import com.xpadro.testingwebapp.city.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    @Query("SELECT c.name FROM City c where c.name = :name")
    Optional<City> findByName(@Param("name") String name);
}
