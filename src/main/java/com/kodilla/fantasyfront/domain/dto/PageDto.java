package com.kodilla.fantasyfront.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PageDto {
    private int currentPage;
    private int finalPage;
}
