package com.kodilla.fantasyfront.views;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

@Route("leagues")
public class LeaguesView extends VerticalLayout implements HasUrlParameter<Long> {

    @Override
    public void setParameter(BeforeEvent event, Long parameter) {

    }
}
