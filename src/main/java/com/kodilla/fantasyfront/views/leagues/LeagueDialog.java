package com.kodilla.fantasyfront.views.leagues;

import com.kodilla.fantasyfront.views.user.UserView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class LeagueDialog extends Dialog {

    public LeagueDialog(LeaguesView leaguesView, LeaguesForm leaguesForm) {

        VerticalLayout leagueDialogLayout = new VerticalLayout();

        Button showLeague = new Button("Show League");
        showLeague.addClickListener(event -> {
            Long leagueId = leaguesForm.getLeagueId();
            Long userId = leaguesView.getUserId();

            UI.getCurrent().navigate("/leagueDetail/" + userId + "/" + leagueId);
        });

        Button enterLeague = new Button("Enter League");
        enterLeague.addClickListener(event -> {
            Long leagueId = leaguesForm.getLeagueId();
            Long userId = leaguesView.getUserId();

            leaguesView.enterLeague(leagueId, userId);
            UI.getCurrent().navigate(UserView.class, userId);
        });

        Button deleteLeague = new Button("Delete League");
        deleteLeague.addClickListener(event -> {
            Long leagueId = leaguesForm.getLeagueId();

            leaguesView.deleteLeague(leagueId);
            close();
        });

        Button exit = new Button("Exit");
        exit.addClickListener(event -> close());

        leagueDialogLayout.add(showLeague, enterLeague, deleteLeague, exit);
        leagueDialogLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        add(leagueDialogLayout);
    }
}
