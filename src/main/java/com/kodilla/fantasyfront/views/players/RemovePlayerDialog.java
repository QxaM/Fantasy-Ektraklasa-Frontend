package com.kodilla.fantasyfront.views.players;

import com.vaadin.flow.component.dialog.Dialog;

public class RemovePlayerDialog extends Dialog {

    public RemovePlayerDialog(PlayerView playerView) {

        setHeaderTitle("Confirm remove player:");

        RemovePlayerMenu removePlayerMenu = new RemovePlayerMenu(playerView, this);

        add(removePlayerMenu);
    }
}
