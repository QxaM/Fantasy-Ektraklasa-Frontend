package com.kodilla.fantasyfront.views.user;

import com.kodilla.fantasyfront.domain.dto.PlayerDto;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;

import java.util.List;

public class SquadForm extends VerticalLayout {

    private final TextField squadName;
    private final Grid<PlayerDto> squadGrid;

    public SquadForm(UserView userView, UserForm userForm) {

        squadName = new TextField("Squad name");
        squadName.setWidth("100%");
        squadName.addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER);

        squadGrid = new Grid<>(PlayerDto.class);
        squadGrid.setColumns("id", "firstname", "lastname", "age", "value", "position", "team", "points");
        squadGrid.setWidth("100%");
        squadGrid.setHeight("80%");

        SquadMenu squadMenu = new SquadMenu(userView, userForm, this);

        add(squadName, squadGrid, squadMenu);
    }

    public String getSquadName() {
        return squadName.getValue();
    }

    public void setSquadName(String squadName) {
        this.squadName.setValue(squadName);
    }

    public void refreshGrid(List<PlayerDto> players) {
        this.squadGrid.setItems(players);
    }
}
