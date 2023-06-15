package com.kodilla.fantasyfront.views;

import com.kodilla.fantasyfront.client.SquadClient;
import com.kodilla.fantasyfront.client.UserClient;
import com.kodilla.fantasyfront.domain.dto.LeagueDto;
import com.kodilla.fantasyfront.domain.dto.PlayerDto;
import com.kodilla.fantasyfront.domain.dto.SquadDto;
import com.kodilla.fantasyfront.domain.dto.UserDto;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;


@Route("user")
public class UserView extends VerticalLayout implements HasUrlParameter<Long> {

    private final UserClient userClient;
    private final SquadClient squadClient;
    private UserDto user;

    private final IntegerField userId;
    private final TextField username;
    private final TextField email;
    private final IntegerField userPoints;
    private final TextField squadName;
    private final Grid<PlayerDto> squadGrid;

    public UserView(UserClient userClient, SquadClient squadClient) {
        this.userClient = userClient;
        this.squadClient = squadClient;

        userId = new IntegerField("User ID");
        userId.setSizeFull();
        username = new TextField("Username");
        username.setSizeFull();
        email = new TextField("User Email");
        email.setSizeFull();
        userPoints = new IntegerField("User points");
        userPoints.setSizeFull();

        Button updateUser = new Button("Update");
        updateUser.addClickListener(event ->
                updateUser(
                    new UserDto(
                        userId.getValue().longValue(),
                        username.getValue(),
                        email.getValue(),
                        user.getSquad(),
                        userPoints.getValue()
                    )));
        Button deleteUser = new Button("Delete");
        deleteUser.addClickListener(event -> deleteUser(userId.getValue().longValue()));
        Button refreshUser = new Button("Refresh");
        refreshUser.addClickListener(event -> getUser(userId.getValue().longValue()));

        HorizontalLayout userMenu = new HorizontalLayout();
        userMenu.add(updateUser, deleteUser, refreshUser);

        VerticalLayout userForm = new VerticalLayout();
        userForm.add(userId, username, email, userPoints, userMenu);
        userForm.setWidth("25%");

        VerticalLayout squadForm = new VerticalLayout();

        squadName = new TextField("Squad name");
        squadName.setWidth("100%");
        squadName.addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER);

        squadGrid = new Grid<>(PlayerDto.class);
        squadGrid.setColumns("id", "firstname", "lastname", "age", "value", "position", "team", "points");
        squadGrid.setWidth("100%");
        squadGrid.setHeight("80%");

        HorizontalLayout squadNavigation = new HorizontalLayout();

        Button createNewSquad = new Button("New Squad");
        createNewSquad.addClickListener(event -> createNewSquad(userId.getValue().longValue(), squadName.getValue()));

        Button addPlayers = new Button("Add players");
        addPlayers.addClickListener(event -> addPlayers(userId.getValue().longValue(), user.getSquad().getId()));

        Dialog renameSquadDialog = new Dialog();
        renameSquadDialog.setHeaderTitle("Rename squad to:");

        VerticalLayout renameSquadLayout = new VerticalLayout();

        TextField newName = new TextField();
        newName.setWidthFull();

        HorizontalLayout renameButtonsLayout = new HorizontalLayout();

        Button confirmRename = new Button("Rename");
        confirmRename.addClickListener(event -> {
            SquadDto newSquad = new SquadDto(
                    user.getSquad().getId(),
                    newName.getValue(),
                    user.getSquad().getCurrentValue(),
                    user.getSquad().getPlayers()
            );
            renameSquad(newSquad);
            getUser(user.getId());
            renameSquadDialog.close();
        });

        Button exitRename = new Button("Exit");
        exitRename.addClickListener(event -> renameSquadDialog.close());

        renameButtonsLayout.add(confirmRename, exitRename);
        renameButtonsLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        renameSquadLayout.add(newName, renameButtonsLayout);
        renameSquadLayout.setAlignItems(Alignment.CENTER);

        Button renameSquad = new Button("Rename squad");
        renameSquad.addClickListener(event -> renameSquadDialog.open());

        renameSquadDialog.add(renameSquadLayout);

        squadNavigation.add(createNewSquad, renameSquad, addPlayers, renameSquadDialog);

        squadForm.add(squadName, squadGrid, squadNavigation);
        squadForm.setWidth("75%");

        HorizontalLayout userAndSquad = new HorizontalLayout();
        userAndSquad.setSizeFull();
        userAndSquad.add(userForm, squadForm);

        HorizontalLayout leagueLayout = new HorizontalLayout();

        VerticalLayout leagueControls = new VerticalLayout();

        Button showAllLeagues = new Button("All Leagues");
        showAllLeagues.addClickListener(event -> UI.getCurrent().navigate(LeaguesView.class, user.getId()));

        Button exitLeague = new Button("Exit League");

        leagueControls.add(showAllLeagues, exitLeague);
        leagueControls.setWidth("15%");

        Grid<LeagueDto> leagueGrid = new Grid<>(LeagueDto.class);
        leagueGrid.setColumns("id", "name");

        leagueLayout.add(leagueControls, leagueGrid);
        leagueLayout.setWidthFull();

        add(userAndSquad, leagueLayout);
    }

    public void getUser(Long id) {
        user = userClient.getUser(id);
        refresh(user);
    }

    public void updateUser(UserDto updatedUser) {
        UserDto newUser = userClient.updateUser(updatedUser);
        Notification.show("User " + newUser.getUsername() + " updated!");
        refresh(newUser);
    }

    public void deleteUser(Long id) {
        userClient.deleteUser(id);
        Notification.show("User deleted!");
        UI.getCurrent().navigate(MainView.class);
    }

    public void createNewSquad(Long userId, String squadName) {
        userClient.createSquad(userId, squadName);
        Notification.show("New squad " + squadName + " created!");
    }

    public void renameSquad(SquadDto squad) {
        squadClient.updateSquad(squad);
    }

    public void addPlayers(Long userId, Long squadId) {
        UI.getCurrent().navigate("/player/" + userId + "/" + squadId);
    }

    @Override
    public void setParameter(BeforeEvent event, Long parameter) {
        getUser(parameter);
    }

    public void refresh(UserDto user) {
        userId.setValue(Math.toIntExact(user.getId()));
        username.setValue(user.getUsername());
        email.setValue(user.getEmail());
        userPoints.setValue(user.getPoints());
        if(user.getSquad().getName() != null) {
            squadName.setValue(user.getSquad().getName());
        }
        squadGrid.setItems(user.getSquad().getPlayers());
    }
}
