package com.kodilla.fantasyfront.views.leagues;

import com.kodilla.fantasyfront.domain.dto.LeagueDto;
import com.kodilla.fantasyfront.views.user.UserView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class LeaguesForm extends VerticalLayout {

    private final Grid<LeagueDto> leagueGrid;
    private Long leagueId;

    public LeaguesForm(LeaguesView leaguesView) {

        LeagueDialog leagueDialog = new LeagueDialog(leaguesView, this);

        leagueGrid = new Grid<>(LeagueDto.class);
        leagueGrid.setColumns("id", "name");

        leagueGrid.asSingleSelect().addValueChangeListener(event -> {
            if (!leagueGrid.asSingleSelect().isEmpty()) {
                leagueId = leagueGrid.asSingleSelect().getValue().getId();
                leagueDialog.open();
            }
        });

        add(leagueGrid, leagueDialog);
    }

    public Long getLeagueId() {
        return leagueId;
    }
}
