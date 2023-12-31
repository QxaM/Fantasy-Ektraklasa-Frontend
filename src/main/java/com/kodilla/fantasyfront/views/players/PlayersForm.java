package com.kodilla.fantasyfront.views.players;

import com.kodilla.fantasyfront.domain.SortType;
import com.kodilla.fantasyfront.domain.dto.PlayerDto;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.List;

public class PlayersForm extends VerticalLayout {

    private final PlayersMenu playersMenu;
    private final Grid<PlayerDto> playerGrid;
    private final PagingMenu pagingMenu;

    public PlayersForm(PlayerView playerView) {

        AddPlayerDialog addPlayerDialog = new AddPlayerDialog(playerView);

        playersMenu = new PlayersMenu(playerView);

        playerGrid = new Grid<>(PlayerDto.class);
        playerGrid.setColumns("id", "firstname", "lastname", "age", "value", "position", "team", "points");
        playerGrid.setWidthFull();
        playerGrid.setAllRowsVisible(true);

        playerGrid.asSingleSelect().addValueChangeListener(event -> {
            if(!playerGrid.asSingleSelect().isEmpty()) {
                playerView.setClickedPlayer(playerGrid.asSingleSelect().getValue());
                addPlayerDialog.open();
            }
        });

        pagingMenu = new PagingMenu(playerView, this);

        add(playersMenu, playerGrid, pagingMenu, addPlayerDialog);
    }

    public SortType getSortType() {
        return playersMenu.getSortBox();
    }

    public void refreshGrid(List<PlayerDto> players) {
        playerGrid.setItems(players);
    }

    public PagingMenu getPagingMenu() {
        return pagingMenu;
    }
}
