package com.kodilla.fantasyfront.views.players;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class RemovePlayerMenu extends HorizontalLayout {

    public RemovePlayerMenu(PlayerView playerView, RemovePlayerDialog removePlayerDialog) {

        Button removePlayer = new Button("Remove player");
        removePlayer.addClickListener(event -> {
            playerView.removePlayerFromSquad(playerView.getSquadId());
            removePlayerDialog.close();
        });

        Button closeRemove = new Button("Exit");
        closeRemove.addClickListener(event -> removePlayerDialog.close());

        add(removePlayer, closeRemove);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }
}
