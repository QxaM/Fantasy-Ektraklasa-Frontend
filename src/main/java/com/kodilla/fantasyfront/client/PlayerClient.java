package com.kodilla.fantasyfront.client;

import com.kodilla.fantasyfront.config.EndpointConfig;
import com.kodilla.fantasyfront.domain.SortType;
import com.kodilla.fantasyfront.domain.dto.PlayersPagedDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class PlayerClient {
    private final RestTemplate restTemplate;
    private final EndpointConfig config;

    public PlayersPagedDto getPlayers(int page, SortType sortType) {
        URI url = buildGetPlayersUrl(page, sortType);

        return restTemplate.getForObject(url, PlayersPagedDto.class);
    }

    private URI buildGetPlayersUrl(int page, SortType sortType) {
        return UriComponentsBuilder.fromHttpUrl(config.getUrl() + config.getPlayers()
                        + page
                        + "/sortBy"
                        + "/" + sortType)
                .build()
                .encode()
                .toUri();
    }
}
