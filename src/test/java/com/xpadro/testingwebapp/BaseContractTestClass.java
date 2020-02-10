package com.xpadro.testingwebapp;

import com.xpadro.testingwebapp.city.CityController;
import com.xpadro.testingwebapp.city.CityService;
import com.xpadro.testingwebapp.city.domain.CityData;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.Optional;

@SpringBootTest(classes = TestingWebappApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class BaseContractTestClass {

    @Autowired
    private CityController cityController;

    @MockBean
    private CityService cityService;

    @Before
    public void setup() {
        RestAssuredMockMvc.standaloneSetup(cityController);

        Mockito.when(cityService.find("Barcelona"))
                .thenReturn(Optional.of(new CityData("Barcelona", Collections.emptyList())));
    }
}
