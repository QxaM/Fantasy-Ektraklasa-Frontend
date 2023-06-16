package com.kodilla.fantasyfront.views.main;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;

public class FormLayout extends VerticalLayout {

    public FormLayout(MainView mainView) {

        TextField usernameField = new TextField("Username");
        EmailField emailField = new EmailField("Email");
        Button createUser = new Button("Create User");
        createUser.addClickListener(event -> mainView.saveAndExit(usernameField.getValue(), emailField.getValue()));

        add(usernameField, emailField, createUser);
        setAlignItems(Alignment.CENTER);
    }
}
