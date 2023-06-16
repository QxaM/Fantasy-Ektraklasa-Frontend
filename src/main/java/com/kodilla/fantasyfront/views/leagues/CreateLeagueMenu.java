package com.kodilla.fantasyfront.views.leagues;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class CreateLeagueMenu extends HorizontalLayout {

    public CreateLeagueMenu(LeaguesView leaguesView, CreateLeagueDialog createLeagueDialog) {

        Button create = new Button("Create");
        create.addClickListener(event -> {
            String leagueName = createLeagueDialog.getLeagueName();
            leaguesView.createLeague(leagueName);
            createLeagueDialog.close();
        });

        Button exitCreate = new Button("Exit");
        exitCreate.addClickListener(event -> createLeagueDialog.close());

        add(create, exitCreate);
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
    }
}
