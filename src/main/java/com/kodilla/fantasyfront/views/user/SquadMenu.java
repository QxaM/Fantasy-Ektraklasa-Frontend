package com.kodilla.fantasyfront.views.user;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class SquadMenu extends HorizontalLayout {

    public SquadMenu(UserView userView, UserForm userForm, SquadForm squadForm) {

        RenameSquadDialog renameSquadDialog = new RenameSquadDialog(userView);

        Button createNewSquad = new Button("New Squad");
        createNewSquad.addClickListener(event -> userView.createNewSquad(
                userForm.getUserId(),
                squadForm.getSquadName()));

        Button addPlayers = new Button("Add players");
        addPlayers.addClickListener(event -> userView.addPlayers(
                userForm.getUserId(),
                userView.getUser().getSquad().getId()));

        Button renameSquad = new Button("Rename squad");
        renameSquad.addClickListener(event -> renameSquadDialog.open());

        add(createNewSquad, renameSquad, addPlayers, renameSquadDialog);
    }
}
