package com.xpadro.testingwebapp.hospital;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

/**
 * This client calls an external service. Actually, this "external service" is a web application which
 * is also running in a container on the same host machine.
 *
 * In order to be able for our test web application inside the container to access another web application on the
 * host machine, we have to follow the steps below (This works for Docker for Mac 17.05 and below):
 *
 * - Attach an IP alias to your host network interface: sudo ifconfig lo0 alias 123.123.123.123/24
 * - The client must connect to this IP instead of using 'localhost'
 */
@Service
public class HospitalClient {
    @Value("${hospital.client.url}")
    private String serviceUrl;

    private final RestTemplate restTemplate;

    @Autowired
    public HospitalClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Hospital> getHospitals(String city) {
        String url = String.format(serviceUrl, city);
        ResponseEntity<Hospital[]> response = restTemplate.getForEntity(url, Hospital[].class);
        Hospital[] hospitals = response.getBody();

        return hospitals == null ? emptyList() : asList(hospitals);
    }
}
