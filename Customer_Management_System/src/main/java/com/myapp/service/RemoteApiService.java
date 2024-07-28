package com.myapp.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.myapp.entity.Customer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
 
import jakarta.annotation.PostConstruct;
import java.util.Collections;


@Service
public class RemoteApiService {

    @Autowired
    private RestTemplate restTemplate;

    private final String AUTH_URL = "https://qa.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp";
    private final String CUSTOMER_URL = "https://qa.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=get_customer_list";
    private final String LOGIN_ID = "test@sunbasedata.com";
    private final String PASSWORD = "Test@123";

    @PostConstruct
    private void init() {
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);
    }

    public String authenticate() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> requestBody = Map.of(
            "login_id", LOGIN_ID,
            "password", PASSWORD
        );

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> responseEntity = restTemplate.exchange(AUTH_URL, HttpMethod.POST, requestEntity, Map.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            Map<String, String> responseBody = responseEntity.getBody();
            return responseBody.get("token");
        } else {
            throw new RuntimeException("Failed to authenticate: " + responseEntity.getStatusCode());
        }
    }

    public List<Customer> fetchCustomers(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List<Customer>> responseEntity = restTemplate.exchange(
                CUSTOMER_URL, HttpMethod.GET, entity, new ParameterizedTypeReference<List<Customer>>() {
                });

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        } else {
            throw new RuntimeException("Failed to fetch customers: " + responseEntity.getStatusCode());
        }
    }
}
