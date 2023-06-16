package com.kodilla.fantasyfront.views.players;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class AddPlayerMenu extends HorizontalLayout {

    public AddPlayerMenu(PlayerView playerView, AddPlayerDialog addPlayerDialog) {

        Button addPlayer = new Button("Add player");
        addPlayer.addClickListener(event -> {
            playerView.addPlayerToSquad(playerView.getSquadId());
            addPlayerDialog.close();
        });

        Button close = new Button("Exit");
        close.addClickListener(event -> addPlayerDialog.close());

        add(addPlayer, close);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }
}
