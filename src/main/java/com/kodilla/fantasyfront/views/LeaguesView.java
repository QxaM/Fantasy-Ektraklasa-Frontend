package com.kodilla.fantasyfront.views;

import com.kodilla.fantasyfront.client.LeagueClient;
import com.kodilla.fantasyfront.domain.dto.LeagueDto;
import com.kodilla.fantasyfront.domain.exception.NoBodyException;
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

import java.util.List;

@Route("leagues")
public class LeaguesView extends VerticalLayout implements HasUrlParameter<Long> {

    private final LeagueClient leagueClient;
    private Long userId;
    private List<LeagueDto> foundLeagues;
    private final Grid<LeagueDto> leagueGrid;

    public LeaguesView(LeagueClient leagueClient) {
        this.leagueClient = leagueClient;

        HorizontalLayout leagueControl = new HorizontalLayout();

        Button returnButton = new Button("Return to user");
        returnButton.addClickListener(event -> UI.getCurrent().navigate(UserView.class, userId));

        Dialog createLeagueDialog = new Dialog();
        createLeagueDialog.setHeaderTitle("Create new league");

        Button createLeague = new Button("Create new");
        createLeague.addClickListener(event -> createLeagueDialog.open());

        VerticalLayout createDialogLayout = new VerticalLayout();

        TextField leagueName = new TextField("League name");
        leagueName.setWidthFull();

        HorizontalLayout createLeagueControls = new HorizontalLayout();

        Button create = new Button("Create");
        create.addClickListener(event -> {
            createLeague(leagueName.getValue());
            createLeagueDialog.close();
        });

        Button exitCreate = new Button("Exit");
        exitCreate.addClickListener(event -> createLeagueDialog.close());

        createLeagueControls.add(create, exitCreate);
        createLeagueControls.setJustifyContentMode(JustifyContentMode.CENTER);

        createDialogLayout.add(leagueName, createLeagueControls);
        createDialogLayout.setAlignItems(Alignment.CENTER);
        createLeagueDialog.add(createDialogLayout);

        leagueControl.add(returnButton, createLeague, createLeagueDialog);

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

        add(leagueControl, leagueGrid, leagueDialog);
    }

    @Override
    public void setParameter(BeforeEvent event, Long parameter) {
        userId = parameter;
        getLeagues();
    }

    public void getLeagues() {
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
        getLeagues();
    }

    public void deleteLeague(Long id) {
        leagueClient.deleteLeague(id);
        Notification.show("Delete league: " + id);
        getLeagues();
    }

    public void enterLeague(Long leagueId, Long userId) {
        leagueClient.addUser(leagueId, userId);
    }

    public void refreshLeaguesGrid() {
        leagueGrid.setItems(foundLeagues);
    }
}

