package com.kodilla.fantasyfront.views;

import com.kodilla.fantasyfront.client.PlayerClient;
import com.kodilla.fantasyfront.domain.dto.PlayerDto;
import com.kodilla.fantasyfront.domain.dto.PlayersPagedDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.router.*;

import java.util.Arrays;
import java.util.List;

@Route("player")
public class PlayerView extends VerticalLayout implements HasUrlParameter<String> {

    private final PlayerClient playerClient;
    private Long userId;
    private Long squadId;
    private final Grid<PlayerDto> playerGrid;
    private PlayersPagedDto foundPlayers;
    private final Button nextPage;
    private final Button previousPage;
    private final TextField paging;

    private int page = 0;

    public PlayerView(PlayerClient playerClient) {
        this.playerClient = playerClient;

        playerGrid = new Grid<>(PlayerDto.class);
        playerGrid.setColumns("id", "firstname", "lastname", "age", "value", "position", "team", "points");
        playerGrid.setWidth("100%");
        playerGrid.setAllRowsVisible(true);
        initPlayers();

        HorizontalLayout pageNavigation = new HorizontalLayout();

        previousPage = new Button("<-");
        previousPage.addClickListener(event -> {
            if(page > 0) {
                --page;
                getPlayers(page);
            }
        });
        nextPage = new Button("->");
        nextPage.addClickListener(event -> {
            if(page < (foundPlayers.getPage().getFinalPage() - 1)) {
                ++page;
                getPlayers(page);
            }
        });

        paging = new TextField();
        paging.setReadOnly(true);

        previousPage.setWidth("80px");
        paging.setWidth("80px");
        paging.addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER);
        nextPage.setWidth("80px");

        pageNavigation.add(previousPage, paging, nextPage);
        pageNavigation.setWidth("100%");
        pageNavigation.setJustifyContentMode(JustifyContentMode.BETWEEN);
        refreshPaging();

        add(playerGrid, pageNavigation);
    }

    public void getPlayers(int page) {
        foundPlayers = playerClient.getPlayers(page);
        playerGrid.setItems(foundPlayers.getPlayer());
        refreshPaging();
    }

    @Override
    public void setParameter(BeforeEvent event,
                             @WildcardParameter String parameter) {
        List<String> parameters = Arrays.stream(parameter.split("/")).toList();
        userId = Long.parseLong(parameters.get(0));
        squadId = Long.parseLong(parameters.get(1));
    }

    public void initPlayers() {
        foundPlayers = playerClient.getPlayers(0);
        playerGrid.setItems(foundPlayers.getPlayer());
    }

    public void refreshPaging() {
        int currentPage = foundPlayers.getPage().getCurrentPage() + 1;
        int finalPage = foundPlayers.getPage().getFinalPage();
        paging.setValue(currentPage + "/" + finalPage);
    }
}
