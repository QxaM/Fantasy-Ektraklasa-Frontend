package com.kodilla.fantasyfront.views.user;

import com.kodilla.fantasyfront.domain.dto.LeagueDto;
import com.kodilla.fantasyfront.domain.dto.PlayerDto;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.util.List;

public class LeagueForm extends HorizontalLayout {

    private final Grid<LeagueDto> leagueGrid;

    public LeagueForm(UserView userView) {

        LeagueMenu leagueMenu = new LeagueMenu(userView, this);

        leagueGrid = new Grid<>(LeagueDto.class);
        leagueGrid.setColumns("id", "name");

        leagueMenu.setWidth("15%");

        add(leagueMenu, leagueGrid);
    }

    public Grid<LeagueDto> getLeagueGrid() {
        return leagueGrid;
    }

    public void refreshGrid(List<LeagueDto> leagues) {
        leagueGrid.setItems(leagues);
    }
}
