package com.kodilla.fantasyfront.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import com.kodilla.fantasyfront.config.EndpointConfig;
import com.kodilla.fantasyfront.domain.dto.CreateUserDto;
import com.kodilla.fantasyfront.domain.dto.UserDto;
import elemental.json.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class UserClient {

    private final RestTemplate restTemplate;
    private final EndpointConfig config;

    public UserDto createUser(CreateUserDto createUserDto) {
        URI url = buildCreateUserUrl();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(createUserDto);

        HttpEntity<String> entity = new HttpEntity<>(jsonContent, headers);

        return restTemplate.postForObject(url, entity, UserDto.class);
    }

    private URI buildCreateUserUrl() {
        return UriComponentsBuilder.fromHttpUrl(config.getUrl() + config.getUsers())
                .build()
                .encode()
                .toUri();
    }
}
