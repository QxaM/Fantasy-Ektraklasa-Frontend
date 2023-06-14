package com.kodilla.fantasyfront.views;

import com.kodilla.fantasyfront.client.UserClient;
import com.kodilla.fantasyfront.domain.dto.PlayerDto;
import com.kodilla.fantasyfront.domain.dto.UserDto;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
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
    private UserDto user;

    private final IntegerField userId;
    private final TextField username;
    private final TextField email;
    private final IntegerField userPoints;
    private final TextField squadName;
    private final Grid<PlayerDto> squadGrid;

    public UserView(UserClient userClient) {
        this.userClient = userClient;

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
        squadNavigation.add(createNewSquad, addPlayers);

        squadForm.add(squadName, squadGrid, squadNavigation);
        squadForm.setWidth("75%");

        HorizontalLayout userAndSquad = new HorizontalLayout();
        userAndSquad.setSizeFull();
        userAndSquad.add(userForm, squadForm);

        add(userAndSquad);
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
