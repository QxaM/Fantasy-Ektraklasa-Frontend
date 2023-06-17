package com.kodilla.fantasyfront.views.leagues;

import com.kodilla.fantasyfront.client.LeagueClient;
import com.kodilla.fantasyfront.domain.dto.LeagueDto;
import com.kodilla.fantasyfront.domain.exception.NoBodyException;
import com.kodilla.fantasyfront.views.user.UserView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Route("leagues")
public class LeaguesView extends VerticalLayout implements HasUrlParameter<Long> {

    private final LeagueClient leagueClient;
    private Long userId;
    private List<LeagueDto> foundLeagues;
    private final Grid<LeagueDto> leagueGrid;

    public LeaguesView(LeagueClient leagueClient) {
        this.leagueClient = leagueClient;

        LeaguesMenu leaguesMenu = new LeaguesMenu(this);

        leagueGrid = new Grid<>(LeagueDto.class);
        leagueGrid.setColumns("id", "name");

        Dialog leagueDialog = new Dialog();

        VerticalLayout leagueDialogLayout = new VerticalLayout();

        Button showLeague = new Button("Show League");
        showLeague.addClickListener(event -> {
            Long leagueId = leagueGrid.asSingleSelect().getValue().getId();
            UI.getCurrent().navigate("/leagueDetail/" + userId + "/" + leagueId);
        });

        Button enterLeague = new Button("Enter League");
        enterLeague.addClickListener(event -> {
            Long leagueId = leagueGrid.asSingleSelect().getValue().getId();
            enterLeague(leagueId, userId);
            UI.getCurrent().navigate(UserView.class, userId);
        });

        Button deleteLeague = new Button("Delete League");
        deleteLeague.addClickListener(event -> {
            Long leagueId = leagueGrid.asSingleSelect().getValue().getId();
            deleteLeague(leagueId);
            leagueDialog.close();
        });

        Button exit = new Button("Exit");
        exit.addClickListener(event -> leagueDialog.close());

        leagueDialogLayout.add(showLeague, enterLeague, deleteLeague, exit);
        leagueDialogLayout.setAlignItems(Alignment.CENTER);

        leagueDialog.add(leagueDialogLayout);

        leagueGrid.asSingleSelect().addValueChangeListener(event -> {
            if (!leagueGrid.asSingleSelect().isEmpty()) {
                leagueDialog.open();
            }
        });

        add(leaguesMenu, leagueGrid, leagueDialog);
    }

    public Long getUserId() {
        return userId;
    }

    @Override
    public void setParameter(BeforeEvent event, Long parameter) {
        userId = parameter;
        fetchLeagues();
    }

    public void fetchLeagues() {
        try {
            foundLeagues = leagueClient.getLeagues();
            refreshLeaguesGrid();
        } catch (NoBodyException e) {
            Notification.show(e.getMessage());
        }
    }

    public void createLeague(String leagueName) {
        LeagueDto createdLeague = leagueClient.createLeague(leagueName);
        Notification.show("Create league: " + createdLeague.getName());
        fetchLeagues();
    }

    public void deleteLeague(Long id) {
        leagueClient.deleteLeague(id);
        Notification.show("Delete league: " + id);
        fetchLeagues();
    }

    public void enterLeague(Long leagueId, Long userId) {
        try {
            leagueClient.addUser(leagueId, userId);
        } catch (HttpClientErrorException e) {
            Notification.show(e.getMessage());
        }
    }

    public void refreshLeaguesGrid() {
        leagueGrid.setItems(foundLeagues);
    }
}

