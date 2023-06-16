package com.kodilla.fantasyfront.views.leagues;

import com.kodilla.fantasyfront.views.user.UserView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class LeaguesMenu extends HorizontalLayout {

    public LeaguesMenu(LeaguesView leaguesView) {

        CreateLeagueDialog createLeagueDialog = new CreateLeagueDialog(leaguesView);

        Button returnButton = new Button("Return to user");
        returnButton.addClickListener(event -> UI.getCurrent().navigate(UserView.class, leaguesView.getUserId()));

        Button createLeague = new Button("Create new");
        createLeague.addClickListener(event -> createLeagueDialog.open());

        add(returnButton, createLeague, createLeagueDialog);
    }
}
