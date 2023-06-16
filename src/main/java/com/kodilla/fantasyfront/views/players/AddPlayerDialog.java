package com.kodilla.fantasyfront.views.players;

import com.vaadin.flow.component.dialog.Dialog;

public class AddPlayerDialog extends Dialog {

    public AddPlayerDialog(PlayerView playerView) {
        setHeaderTitle("Confirm to add player to squad:");

        AddPlayerMenu addPlayerMenu = new AddPlayerMenu(playerView, this);

        add(addPlayerMenu);
    }
}
