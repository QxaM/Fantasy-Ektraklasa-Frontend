package com.kodilla.fantasyfront.client;

import com.kodilla.fantasyfront.config.EndpointConfig;
import com.kodilla.fantasyfront.domain.dto.SquadDto;
import com.kodilla.fantasyfront.domain.exception.NoBodyException;
import com.kodilla.fantasyfront.service.HeadersBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class SquadClient {

    private final RestTemplate restTemplate;
    private final EndpointConfig config;

    public SquadDto getSquad(Long squadId) {
        URI url = buildSquadIdUrl(squadId);
        return restTemplate.getForObject(url, SquadDto.class);
    }

    public void updateSquad(SquadDto squad) throws NoBodyException {
        URI url = buildSquadUrl();
        HttpEntity<String> entity = HeadersBuilder.buildHeaders(squad);

        restTemplate.exchange(url, HttpMethod.PUT, entity, SquadDto.class);
    }

    public SquadDto addPlayer(Long squadId, Long playerId) throws NoBodyException {
        URI url = buildAddPlayerUrl(squadId, playerId);
        ResponseEntity<SquadDto> response = restTemplate.exchange(url, HttpMethod.PUT, null, SquadDto.class);
        if (response.hasBody()) {
            return response.getBody();
        } else {
            throw new NoBodyException("No response when adding player " + playerId
                                        + " to squad " + squadId);
        }
    }

    public SquadDto removePlayer(Long squadId, Long playerId) throws NoBodyException {
        URI url = buildRemovePlayerUrl(squadId, playerId);
        ResponseEntity<SquadDto> response = restTemplate.exchange(url, HttpMethod.PUT, null, SquadDto.class);
        if (response.hasBody()) {
            return response.getBody();
        } else {
            throw new NoBodyException("No response when removing player " + playerId
                                        + " to squad " + squadId);
        }
    }

    private URI buildSquadUrl() {
        return UriComponentsBuilder.fromHttpUrl(config.getUrl()
                        + config.getSquads())
                .build()
                .encode()
                .toUri();
    }

    private URI buildSquadIdUrl(Long squadId) {
        return UriComponentsBuilder.fromHttpUrl(config.getUrl()
                        + config.getSquads()
                        + "/" + squadId)
                .build()
                .encode()
                .toUri();
    }

    private URI buildAddPlayerUrl(Long squadId, Long playerId) {
        return UriComponentsBuilder.fromHttpUrl(config.getUrl()
                        + config.getSquads()
                        + "/" + squadId
                        + "/addPlayer"
                        + "/" + playerId)
                .build()
                .encode()
                .toUri();
    }

    private URI buildRemovePlayerUrl(Long squadId, Long playerId) {
        return UriComponentsBuilder.fromHttpUrl(config.getUrl()
                        + config.getSquads()
                        + "/" + squadId
                        + "/removePlayer"
                        + "/" + playerId)
                .build()
                .encode()
                .toUri();
    }
}
