package com.kodilla.fantasyfront.views.user;

import com.kodilla.fantasyfront.domain.dto.LeagueDto;
import com.kodilla.fantasyfront.views.leagues.LeaguesView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class LeagueMenu extends VerticalLayout {

    public LeagueMenu(UserView userView, LeagueForm leagueForm) {

        Button showAllLeagues = new Button("All Leagues");
        showAllLeagues.addClickListener(event -> UI.getCurrent().navigate(
                LeaguesView.class,
                userView.getUser().getId()));

        Button exitLeague = new Button("Exit League");
        exitLeague.addClickListener(event -> {
            Grid<LeagueDto> leagueGrid = leagueForm.getLeagueGrid();

            if (!leagueGrid.asSingleSelect().isEmpty()) {
                Long leagueId = leagueGrid.asSingleSelect().getValue().getId();
                userView.exitLeague(leagueId);
            }
        });

        add(showAllLeagues, exitLeague);
    }
}
