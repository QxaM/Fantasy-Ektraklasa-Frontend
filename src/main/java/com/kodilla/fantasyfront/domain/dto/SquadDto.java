package com.kodilla.fantasyfront.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SquadDto {
    private Long id;
    private String name;
    private BigDecimal currentValue = BigDecimal.ZERO;
    private List<PlayerDto> players;
}
