package com.kodilla.fantasyfront.views;

import com.kodilla.fantasyfront.client.UserClient;
import com.kodilla.fantasyfront.domain.dto.UserDto;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
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

    public UserView(UserClient userClient) {
        this.userClient = userClient;

        userId = new IntegerField("User ID");
        username = new TextField("Username");
        email = new TextField("User Email");
        userPoints = new IntegerField("User points");

        VerticalLayout userForm = new VerticalLayout();
        userForm.add(userId, username, email, userPoints);

        add(userForm);
    }

    @Override
    public void setParameter(BeforeEvent event, Long parameter) {
        user = userClient.getUser(parameter);
        userId.setValue(Math.toIntExact(user.getId()));
        username.setValue(user.getUsername());
        email.setValue(user.getEmail());
        userPoints.setValue(user.getPoints());
    }
}
