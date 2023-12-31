package com.kodilla.fantasyfront.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class EndpointConfig {
    @Value("${endpoint.url}")
    private String url;
    @Value("${endpoint.users}")
    private String users;
    @Value("${endpoint.players}")
    private String players;
    @Value("${endpoint.users.create-squad}")
    private String createSquad;
    @Value("${endpoint.squads}")
    private String squads;
    @Value("${endpoint.leagues}")
    private String leagues;
}
