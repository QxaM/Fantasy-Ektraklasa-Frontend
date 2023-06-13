package com.kodilla.fantasyfront.views;

import com.kodilla.fantasyfront.client.PlayerClient;
import com.kodilla.fantasyfront.client.UserClient;
import com.kodilla.fantasyfront.domain.Person;
import com.kodilla.fantasyfront.domain.dto.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route
public class MainView extends VerticalLayout {

    private final UserClient userClient;
    private final PlayerClient playerClient;
    private final TextField usernameField;
    private final EmailField emailField;
    private UserDto createdUser;
    private final HorizontalLayout userAndSquad;
    private final Grid<PlayerDto> squadGrid = new Grid<>(PlayerDto.class);
    private final Grid<LeagueDto> leagueGrid = new Grid<>(LeagueDto.class);
    private final Grid<PlayerDto> playerGrid = new Grid<>(PlayerDto.class);

    @Autowired
    public MainView(UserClient userClient, PlayerClient playerClient) {
        this.userClient = userClient;
        this.playerClient = playerClient;

        squadGrid.setColumns("id", "firstname", "lastname", "age", "value", "position", "team", "points");

        usernameField = new TextField("Username");
        emailField = new EmailField("Email");
        Button createUser = new Button("Create User");
        createUser.addClickListener(event -> save());

        VerticalLayout userForm = new VerticalLayout();
        userForm.setWidth("50%");
        userForm.add(usernameField, emailField, createUser);

        userAndSquad = new HorizontalLayout();
        userAndSquad.add(squadGrid, userForm);

        leagueGrid.setColumns("id", "name");
        playerGrid.setColumns("id", "firstname", "lastname", "age", "value", "position", "team", "points");

        HorizontalLayout leaguesAndPlayers = new HorizontalLayout();
        leaguesAndPlayers.add(leagueGrid, playerGrid);
        leaguesAndPlayers.setSizeFull();
        leagueGrid.setSizeFull();
        playerGrid.setSizeFull();

        HorizontalLayout newHorizontalLayout = new HorizontalLayout();
        newHorizontalLayout.setSizeFull();

        Grid<Person> grid = new Grid<>(Person.class);
        newHorizontalLayout.add(grid, userForm);

        add(newHorizontalLayout);

        //add(userAndSquad, leaguesAndPlayers);
        showPlayers();
    }

    private void showPlayers() {
        PlayersPagedDto playersPagedDto = playerClient.getPlayers(0);
        playerGrid.setItems(playersPagedDto.getPlayer());
    }

    private void save() {
        CreateUserDto createUserDto = new CreateUserDto(
                usernameField.getValue(),
                emailField.getValue()
        );
        createdUser = userClient.createUser(createUserDto);
        Notification.show("User " + createdUser.getUsername() +  " created!");
        refresh();
    }

    private void refresh() {
        squadGrid.setItems(createdUser.getSquad().getPlayers());
    }
}
