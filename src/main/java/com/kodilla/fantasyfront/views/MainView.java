package com.kodilla.fantasyfront.views;

import com.kodilla.fantasyfront.client.UserClient;
import com.kodilla.fantasyfront.domain.dto.*;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route
public class MainView extends VerticalLayout {

    private final UserClient userClient;
    private final TextField usernameField;
    private final EmailField emailField;


    @Autowired
    public MainView(UserClient userClient) {
        this.userClient = userClient;

        usernameField = new TextField("Username");
        emailField = new EmailField("Email");
        Button createUser = new Button("Create User");
        createUser.addClickListener(event -> saveAndExit());

        VerticalLayout formLayout = new VerticalLayout();
        formLayout.add(usernameField, emailField, createUser);
        formLayout.setAlignItems(Alignment.CENTER);

        add(formLayout);
    }

    private void saveAndExit() {
        CreateUserDto createUserDto = new CreateUserDto(
                usernameField.getValue(),
                emailField.getValue()
        );
        UserDto createdUser = userClient.createUser(createUserDto);
        Notification.show("User " + createdUser.getUsername() +  " created!");
        UI.getCurrent().navigate(UserView.class, createdUser.getId());
    }
}
