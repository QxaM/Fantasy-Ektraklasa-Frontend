package com.kodilla.fantasyfront.views.main;

import com.kodilla.fantasyfront.client.UserClient;
import com.kodilla.fantasyfront.domain.dto.*;
import com.kodilla.fantasyfront.views.user.UserView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route
public class MainView extends VerticalLayout {

    private final UserClient userClient;


    @Autowired
    public MainView(UserClient userClient) {
        this.userClient = userClient;

        FormLayout formLayout = new FormLayout(this);

        add(formLayout);
    }

    public void saveAndExit(String userName, String email) {
        CreateUserDto createUserDto = new CreateUserDto(
                userName,
                email
        );
        UserDto createdUser = userClient.createUser(createUserDto);
        Notification.show("User " + createdUser.getUsername() +  " created!");
        UI.getCurrent().navigate(UserView.class, createdUser.getId());
    }
}
