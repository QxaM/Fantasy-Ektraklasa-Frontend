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
}
