package com.kodilla.fantasyfront.service;

import com.google.gson.Gson;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class HeadersBuilder {

    public static HttpEntity<String> buildHeaders(Object object) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(object);

        return new HttpEntity<>(jsonContent, headers);
    }

}
