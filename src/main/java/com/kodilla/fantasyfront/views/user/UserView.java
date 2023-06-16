package com.kodilla.fantasyfront.views.user;

import com.kodilla.fantasyfront.client.LeagueClient;
import com.kodilla.fantasyfront.client.SquadClient;
import com.kodilla.fantasyfront.client.UserClient;
import com.kodilla.fantasyfront.domain.dto.LeagueDto;
import com.kodilla.fantasyfront.domain.dto.SquadDto;
import com.kodilla.fantasyfront.domain.dto.UserDto;
import com.kodilla.fantasyfront.views.main.MainView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

import java.util.List;


@Route("user")
public class UserView extends VerticalLayout implements HasUrlParameter<Long> {

    private final UserClient userClient;
    private final SquadClient squadClient;
    private final LeagueClient leagueClient;
    private final UserForm userForm;
    private final SquadForm squadForm;
    private final LeagueForm leagueForm;
    private UserDto user;

    public UserView(UserClient userClient, SquadClient squadClient, LeagueClient leagueClient) {
        this.userClient = userClient;
        this.squadClient = squadClient;
        this.leagueClient = leagueClient;

        userForm = new UserForm();
        UserMenu userMenu = new UserMenu(this, userForm);
        VerticalLayout userControls = new VerticalLayout(userForm, userMenu);
        squadForm = new SquadForm(this, userForm);
        HorizontalLayout userAndSquad = new HorizontalLayout(userControls, squadForm);

        userControls.setWidth("25%");
        squadForm.setWidth("75%");
        userAndSquad.setSizeFull();

        leagueForm = new LeagueForm(this);
        leagueForm.setWidthFull();

        add(userAndSquad, leagueForm);
    }

    public UserDto getUser() {
        return user;
    }

    public void fetchUser(Long id) {
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
        fetchUser(userId);
        refresh(user);
    }

    public void renameSquad(SquadDto squad) {
        squadClient.updateSquad(squad);
    }

    public void addPlayers(Long userId, Long squadId) {
        UI.getCurrent().navigate("/player/" + userId + "/" + squadId);
    }

    public void exitLeague(Long leagueId) {
        leagueClient.removeUser(leagueId, user.getId());
        refreshLeagues(user);
    }

    @Override
    public void setParameter(BeforeEvent event, Long parameter) {
        fetchUser(parameter);
    }

    public void refresh(UserDto user) {
        userForm.setUserId(user.getId());
        userForm.setUsername(user.getUsername());
        userForm.setEmail(user.getEmail());
        userForm.setUserPoints(user.getPoints());
        if(user.getSquad().getName() != null) {
            squadForm.setSquadName(user.getSquad().getName());
        }
        squadForm.refreshGrid(user.getSquad().getPlayers());
        refreshLeagues(user);
    }

    public void refreshLeagues(UserDto user) {
        List<LeagueDto> foundLeagues = leagueClient.getLeaguesByUserId(user.getId());
        leagueForm.refreshGrid(foundLeagues);
    }
}
