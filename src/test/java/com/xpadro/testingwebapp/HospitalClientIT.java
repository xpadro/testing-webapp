package com.xpadro.testingwebapp;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.xpadro.testingwebapp.hospital.Hospital;
import com.xpadro.testingwebapp.hospital.HospitalClient;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@AutoConfigureWebClient
@DataJpaTest
@Import(HospitalClient.class)
public class HospitalClientIT {

    @Autowired
    private HospitalClient client;

    @Autowired
    private RestTemplate restTemplate;

    @Rule
    public WireMockRule wireMockServer = new WireMockRule(wireMockConfig()
            .port(8081));


    @Test
    public void test() {
        String expected = "[{\"name\":\"Hospital de Barcelona\"},{\"name\":\"Hospital Clinic\"}]";

        stubFor(get(urlEqualTo("/cities/test/hospitals"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(expected)));

        client = new HospitalClient(restTemplate);
        List<Hospital> result = client.getHospitals("test");

        assertThat(result.size(), org.hamcrest.Matchers.equalTo(2));

    }
}
