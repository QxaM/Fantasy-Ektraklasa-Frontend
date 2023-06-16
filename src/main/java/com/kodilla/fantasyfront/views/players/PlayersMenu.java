package com.kodilla.fantasyfront.views.players;

import com.kodilla.fantasyfront.domain.SortType;
import com.kodilla.fantasyfront.views.user.UserView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class PlayersMenu extends HorizontalLayout {

    private final ComboBox<SortType> sortBox;

    public PlayersMenu(PlayerView playerView) {

        Button returnButton = new Button("Return to user");
        returnButton.addClickListener(event -> UI.getCurrent().navigate(UserView.class, playerView.getUserId()));

        sortBox = new ComboBox<>("Sorting");
        sortBox.setItems(SortType.values());
        sortBox.setWidth("250px");
        sortBox.setValue(SortType.ID_ASCENDING);
        sortBox.addValueChangeListener(event -> playerView.fetchPlayers(playerView.getPage(), getSortBox()));

        setAlignItems(Alignment.CENTER);
        add(returnButton, sortBox);
    }

    public SortType getSortBox() {
        return sortBox.getValue();
    }
}
