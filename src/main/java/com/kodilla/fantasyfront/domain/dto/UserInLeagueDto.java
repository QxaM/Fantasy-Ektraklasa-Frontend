package com.kodilla.fantasyfront.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInLeagueDto {
    private Long id;
    private String username;
    private String squadName;
}
