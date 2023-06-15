package com.kodilla.fantasyfront.views;

import com.kodilla.fantasyfront.client.LeagueClient;
import com.kodilla.fantasyfront.domain.dto.LeagueDto;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

@Route("leagues")
public class LeaguesView extends VerticalLayout implements HasUrlParameter<Long> {

    private final LeagueClient leagueClient;
    private Long userId;

    public LeaguesView(LeagueClient leagueClient) {
        this.leagueClient = leagueClient;

        Button returnButton = new Button("Return to user");
        returnButton.addClickListener(event -> UI.getCurrent().navigate(UserView.class, userId));

        Grid<LeagueDto> leagueGrid = new Grid<>(LeagueDto.class);
        leagueGrid.setColumns("id", "name");

        Dialog leagueDialog = new Dialog();

        Button showLeague = new Button("Show League");
        Button addLeague = new Button("Add League");
        Button exit = new Button("Exit");
        exit.addClickListener(event -> leagueDialog.close());

        leagueDialog.add(showLeague, addLeague, exit);

        leagueGrid.asSingleSelect().addValueChangeListener(event -> leagueDialog.open());

        add(returnButton, leagueGrid, leagueDialog);
    }

    @Override
    public void setParameter(BeforeEvent event, Long parameter) {
        userId = parameter;
    }
}
