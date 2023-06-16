package com.kodilla.fantasyfront.views.players;

import com.kodilla.fantasyfront.domain.dto.PlayerDto;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;

import java.util.List;

public class UserSquadForm extends VerticalLayout {

    private final TextField squadName;
    private final Grid<PlayerDto> squadGrid;

    public UserSquadForm(PlayerView playerView) {

        squadName = new TextField();
        squadName.setWidthFull();
        squadName.addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER);
        squadName.setReadOnly(true);

        RemovePlayerDialog removePlayerDialog = new RemovePlayerDialog(playerView);

        squadGrid = new Grid<>(PlayerDto.class);
        squadGrid.setColumns("id", "firstname", "lastname", "age", "value", "position", "team", "points");
        squadGrid.setAllRowsVisible(true);

        squadGrid.asSingleSelect().addValueChangeListener(event -> {
            if(!squadGrid.asSingleSelect().isEmpty()) {
                playerView.setClickedPlayer(squadGrid.asSingleSelect().getValue());
                removePlayerDialog.open();
            }
        });

        add(squadName, squadGrid, removePlayerDialog);
    }

    public void setSquadName(String squadName) {
        this.squadName.setValue(squadName);
    }

    public void refreshGrid(List<PlayerDto> players) {
        this.squadGrid.setItems(players);
    }
}
