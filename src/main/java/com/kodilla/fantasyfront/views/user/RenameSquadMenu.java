package com.kodilla.fantasyfront.views.user;

import com.kodilla.fantasyfront.domain.dto.SquadDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class RenameSquadMenu extends HorizontalLayout {

    public RenameSquadMenu(UserView userView, RenameSquadDialog renameSquadDialog) {

        Button confirmRename = new Button("Rename");
        confirmRename.addClickListener(event -> {
            SquadDto newSquad = new SquadDto(
                    userView.getUser().getSquad().getId(),
                    renameSquadDialog.getNewName(),
                    userView.getUser().getSquad().getCurrentValue(),
                    userView.getUser().getSquad().getPlayers()
            );
            userView.renameSquad(newSquad);
            userView.fetchUser(userView.getUser().getId());
            renameSquadDialog.close();
        });

        Button exitRename = new Button("Exit");
        exitRename.addClickListener(event -> renameSquadDialog.close());

        add(confirmRename, exitRename);
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
    }
}
