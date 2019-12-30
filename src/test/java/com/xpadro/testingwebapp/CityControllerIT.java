package com.xpadro.testingwebapp;

import com.xpadro.testingwebapp.city.CityRepository;
import com.xpadro.testingwebapp.city.domain.City;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MySQLContainerProvider;

import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@SpringBootTest(classes = TestingWebappApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(initializers = {CityControllerIT.Initializer.class})
public class CityControllerIT {
    private static final String TEST_CITY = "test";

    @Autowired
    private CityRepository cityRepository;

    @ClassRule
    public static final JdbcDatabaseContainer<?> mySQLContainer = new MySQLContainerProvider()
            .newInstance()
            .withDatabaseName("database-integration-test")
            .withUsername("testuser")
            .withPassword("testpwd");

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + mySQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + mySQLContainer.getUsername(),
                    "spring.datasource.password=" + mySQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

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
