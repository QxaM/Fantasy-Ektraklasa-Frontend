package com.kodilla.fantasyfront.domain.dto;

import com.kodilla.fantasyfront.domain.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDto {
    private Long id;
    private String firstname;
    private String lastname;
    private int age;
    private BigDecimal value;
    private Position position;
    private TeamDto team;
    private int points;
}
