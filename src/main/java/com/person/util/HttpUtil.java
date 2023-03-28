package com.person.util;

import com.person.model.Person;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class HttpUtil {
    public static void sendHttpPostRequest(String url, Person person) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Person> request = new HttpEntity<>(person, headers);
        restTemplate.postForObject(url, request, String.class);
    }
}
