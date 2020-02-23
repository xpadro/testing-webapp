package com.xpadro.testingwebapp.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.xpadro.testingwebapp.AbstractInitializerTest;
import com.xpadro.testingwebapp.TestingWebappApplication;
import com.xpadro.testingwebapp.city.CityRepository;
import com.xpadro.testingwebapp.city.domain.City;
import com.xpadro.testingwebapp.city.domain.CityData;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TestingWebappApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class ComponentTesting extends AbstractInitializerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CityRepository cityRepository;

    @Rule
    public WireMockRule wireMockServer = new WireMockRule(wireMockConfig()
            .port(8089));

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void shouldReturnCities() throws Exception {
        setUpDatabaseData();

        String expected = "[{\"name\":\"Hospital de Barcelona\"},{\"name\":\"Hospital Clinic\"}]";
        stubFor(WireMock.get(urlEqualTo("/cities/test/hospitals"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(expected)));



        MvcResult mvcResult = this.mockMvc.perform(get("/cities"))
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        CityData[] response = objectMapper.readValue(contentAsString, CityData[].class);

        assertThat(response.length, equalTo(1));
        assertThat(response[0].getCityName(), equalTo("test"));
        assertThat(response[0].getHospitals().size(), equalTo(2));
    }

    private void setUpDatabaseData() {
        City city = new City();
        city.setId(1L);
        city.setName("test");
        cityRepository.save(city);
    }

}
