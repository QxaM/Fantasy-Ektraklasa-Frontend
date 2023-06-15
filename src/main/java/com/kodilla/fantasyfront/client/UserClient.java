package com.kodilla.fantasyfront.client;

import com.google.gson.Gson;
import com.kodilla.fantasyfront.config.EndpointConfig;
import com.kodilla.fantasyfront.domain.dto.CreateUserDto;
import com.kodilla.fantasyfront.domain.dto.UserDto;
import com.kodilla.fantasyfront.domain.exception.NoBodyException;
import com.kodilla.fantasyfront.service.HeadersBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
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
        URI url = buildUserUrl();
        HttpEntity<String> entity = HeadersBuilder.buildHeaders(createUserDto);

        return restTemplate.postForObject(url, entity, UserDto.class);
    }

    public UserDto updateUser(UserDto userDto) throws NoBodyException {
        URI url = buildUserUrl();
        HttpEntity<String> entity = HeadersBuilder.buildHeaders(userDto);

        ResponseEntity<UserDto> response = restTemplate
                .exchange(url, HttpMethod.PUT, entity, UserDto.class);

        if(response.hasBody()) {
            return response.getBody();
        } else {
            throw new NoBodyException("Update response with no body");
        }
    }

    public void deleteUser(Long id) {
        URI url = buildUserIdUrl(id);
        restTemplate.delete(url);
    }

    public void createSquad(Long id, String squadName) {
        URI url = buildCreateSquadUrl(id, squadName);
        restTemplate.put(url, null);
    }

    public UserDto getUser(Long id) {
        URI url = buildUserIdUrl(id);
        return restTemplate.getForObject(url, UserDto.class);
    }

    private URI buildUserUrl() {
        return UriComponentsBuilder.fromHttpUrl(config.getUrl() + config.getUsers())
                .build()
                .encode()
                .toUri();
    }

    private URI buildUserIdUrl(Long id) {
        return UriComponentsBuilder.fromHttpUrl(config.getUrl()
                        + config.getUsers()
                        + "/" + id)
                .build()
                .encode()
                .toUri();
    }

    private URI buildCreateSquadUrl(Long id, String squadName) {
        return UriComponentsBuilder.fromHttpUrl(config.getUrl()
                        + config.getUsers()
                        + "/" + id
                        + config.getCreateSquad()
                        + "/" + squadName)
                .build()
                .encode()
                .toUri();
    }
}
