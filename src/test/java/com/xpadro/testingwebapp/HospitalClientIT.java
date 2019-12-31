package com.xpadro.testingwebapp;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.xpadro.testingwebapp.hospital.Hospital;
import com.xpadro.testingwebapp.hospital.HospitalClient;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.Assert.assertThat;

@SpringBootTest(classes = TestingWebappApplication.class)
@RunWith(SpringRunner.class)
public class HospitalClientIT {

    @Autowired
    private HospitalClient client;

    @Rule
    public WireMockRule wireMockServer = new WireMockRule(wireMockConfig()
            .port(8089));


    @Test
    public void shouldMapServiceResponse() {
        String expected = "[{\"name\":\"Hospital de Barcelona\"},{\"name\":\"Hospital Clinic\"}]";

        stubFor(get(urlEqualTo("/cities/test/hospitals"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(expected)));

        List<Hospital> result = client.getHospitals("test");

        assertThat(result.size(), org.hamcrest.Matchers.equalTo(2));
    }
}
