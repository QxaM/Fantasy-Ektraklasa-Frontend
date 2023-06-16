package com.kodilla.fantasyfront.views.leagues;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class CreateLeagueDialog extends Dialog {

    private final TextField leagueName;

    public CreateLeagueDialog(LeaguesView leaguesView) {
        setHeaderTitle("Create new league");

        VerticalLayout createDialogLayout = new VerticalLayout();

        leagueName = new TextField("League name");
        leagueName.setWidthFull();

        CreateLeagueMenu createLeagueMenu = new CreateLeagueMenu(leaguesView, this);

        createDialogLayout.add(leagueName, createLeagueMenu);
        createDialogLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        add(createDialogLayout);
    }

    public String getLeagueName() {
        return leagueName.getValue();
    }
}
