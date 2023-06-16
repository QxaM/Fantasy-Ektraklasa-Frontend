package com.kodilla.fantasyfront.views.user;

import com.kodilla.fantasyfront.domain.dto.UserDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class UserMenu extends HorizontalLayout {

    public UserMenu(UserView userView, UserForm userForm) {

        Button updateUser = new Button("Update");
        updateUser.addClickListener(event ->
                userView.updateUser(new UserDto(
                        userForm.getUserId(),
                        userForm.getUsername(),
                        userForm.getEmail(),
                        userView.getUser().getSquad(),
                        userForm.getUserPoints()
                )));

        Button deleteUser = new Button("Delete");
        deleteUser.addClickListener(event -> userView.deleteUser(userForm.getUserId()));

        Button refreshUser = new Button("Refresh");
        refreshUser.addClickListener(event -> userView.fetchUser(userForm.getUserId()));

        add(updateUser, deleteUser, refreshUser);
    }
}
