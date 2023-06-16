package com.kodilla.fantasyfront.views;

import com.kodilla.fantasyfront.client.LeagueClient;
import com.kodilla.fantasyfront.domain.dto.LeagueDto;
import com.kodilla.fantasyfront.domain.dto.UserInLeagueDto;
import com.kodilla.fantasyfront.views.leagues.LeaguesView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.WildcardParameter;

import java.util.Arrays;
import java.util.List;

@Route("leagueDetail")
public class LeagueDetailView extends VerticalLayout implements HasUrlParameter<String> {

    private final LeagueClient leagueClient;
    private Long userId;
    private final TextField leagueName;
    Grid<UserInLeagueDto> userGrid;

    public LeagueDetailView(LeagueClient leagueClient) {
        this.leagueClient = leagueClient;

        Button returnButton = new Button("Return to leagues");
        returnButton.addClickListener(event -> {
            UI.getCurrent().navigate(LeaguesView.class, userId);
        });

        leagueName = new TextField();
        leagueName.setWidthFull();
        leagueName.addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER);

        userGrid = new Grid<>(UserInLeagueDto.class);
        userGrid.setColumns("id", "username", "squadName", "points");

        add(returnButton, userGrid);
    }

    @Override
    public void setParameter(BeforeEvent event,
                             @WildcardParameter String parameter) {
        List<String> parameters = Arrays.stream(parameter.split("/")).toList();
        userId = Long.parseLong(parameters.get(0));
        Long leagueId = Long.parseLong(parameters.get(1));

        LeagueDto foundLeague = getLeague(leagueId);
        leagueName.setValue(foundLeague.getName());
        userGrid.setItems(foundLeague.getUsers());
    }

    public LeagueDto getLeague(Long leagueId) {
        return leagueClient.getLeague(leagueId);
    }
}
