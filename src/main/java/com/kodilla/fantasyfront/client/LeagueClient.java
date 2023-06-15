package com.kodilla.fantasyfront.client;

import com.kodilla.fantasyfront.config.EndpointConfig;
import com.kodilla.fantasyfront.domain.dto.LeagueDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class LeagueClient {

    private final RestTemplate restTemplate;
    private final EndpointConfig config;

    public LeagueDto getLeague(Long id) {
        URI url = buildLeagueUrl(id);
        return restTemplate.getForObject(url, LeagueDto.class);
    }

    private URI buildLeagueUrl(Long id) {
        return UriComponentsBuilder.fromHttpUrl(config.getUrl()
                        + config.getLeagues()
                        + "/" + id)
                .build()
                .encode()
                .toUri();
    }
}
