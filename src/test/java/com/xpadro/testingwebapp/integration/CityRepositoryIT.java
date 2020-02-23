package com.xpadro.testingwebapp.integration;

import com.xpadro.testingwebapp.AbstractInitializerTest;
import com.xpadro.testingwebapp.TestingWebappApplication;
import com.xpadro.testingwebapp.city.CityRepository;
import com.xpadro.testingwebapp.city.domain.City;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@SpringBootTest(classes = TestingWebappApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class CityRepositoryIT extends AbstractInitializerTest {
    private static final String TEST_CITY = "test";

    @Autowired
    private CityRepository cityRepository;

    @Test
    public void shouldFindSavedCity() {
        City failed = new City();
        City city = new City();
        city.setId(1L);
        city.setName(TEST_CITY);
        cityRepository.save(city);

        Optional<City> result = cityRepository.findByName(TEST_CITY);

        assertThat(result.orElse(failed).getName(), equalTo(TEST_CITY));
    }
}
