package com.kodilla.fantasyfront.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeagueDto {
    private Long id;
    private String name;
    private List<UserInLeagueDto> users;
}
