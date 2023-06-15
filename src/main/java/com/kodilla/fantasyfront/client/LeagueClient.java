package com.kodilla.fantasyfront.client;

import com.kodilla.fantasyfront.config.EndpointConfig;
import com.kodilla.fantasyfront.domain.dto.LeagueDto;
import com.kodilla.fantasyfront.domain.exception.NoBodyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LeagueClient {

    private final RestTemplate restTemplate;
    private final EndpointConfig config;

    public List<LeagueDto> getLeagues() throws NoBodyException {
        URI url = buildLeagueUrl();
        LeagueDto[] response = restTemplate.getForObject(url, LeagueDto[].class);
        if (response != null) {
            return List.of(response);
        } else {
            throw new NoBodyException("No response getting leagues");
        }
    }

    public LeagueDto getLeague(Long id) {
        URI url = buildLeagueIdUrl(id);
        return restTemplate.getForObject(url, LeagueDto.class);
    }

    public LeagueDto createLeague(String leagueName) {
        URI url = buildCreateLeagueUrl(leagueName);
        return restTemplate.postForObject(url, null, LeagueDto.class);
    }

    public void deleteLeague(Long id) {
        URI url = buildLeagueIdUrl(id);
        restTemplate.delete(url);
    }
    public void addUser(Long leagueId, Long userId) {
        URI url = buildAddUserUrl(leagueId, userId);
        restTemplate.put(url, LeagueDto.class);
    }

    public void removeUser(Long leagueId, Long userId) {
        URI url = buildRemoveUserUrl(leagueId, userId);
        restTemplate.put(url, LeagueDto.class);
    }

    private URI buildLeagueUrl() {
        return UriComponentsBuilder.fromHttpUrl(config.getUrl()
                        + config.getLeagues())
                .build()
                .encode()
                .toUri();
    }

    private URI buildLeagueIdUrl(Long id) {
        return UriComponentsBuilder.fromHttpUrl(config.getUrl()
                        + config.getLeagues()
                        + "/" + id)
                .build()
                .encode()
                .toUri();
    }

    private URI buildCreateLeagueUrl(String leagueName) {
        return UriComponentsBuilder.fromHttpUrl(config.getUrl()
                        + config.getLeagues()
                        + "/" + leagueName)
                .build()
                .encode()
                .toUri();
    }

    private URI buildAddUserUrl(Long leagueId, Long userId) {
        return UriComponentsBuilder.fromHttpUrl(config.getUrl()
                        + config.getLeagues()
                        + "/" + leagueId
                        + "/" + "addUser"
                        + "/" + userId)
                .build()
                .encode()
                .toUri();
    }

    private URI buildRemoveUserUrl(Long leagueId, Long userId) {
        return UriComponentsBuilder.fromHttpUrl(config.getUrl()
                        + config.getLeagues()
                        + "/" + leagueId
                        + "/" + "removeUser"
                        + "/" + userId)
                .build()
                .encode()
                .toUri();
    }
}
