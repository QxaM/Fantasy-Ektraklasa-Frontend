package com.kodilla.fantasyfront.views;

import com.kodilla.fantasyfront.client.PlayerClient;
import com.kodilla.fantasyfront.client.SquadClient;
import com.kodilla.fantasyfront.domain.dto.PlayerDto;
import com.kodilla.fantasyfront.domain.dto.PlayersPagedDto;
import com.kodilla.fantasyfront.domain.dto.SquadDto;
import com.kodilla.fantasyfront.domain.exception.NoBodyException;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.WildcardParameter;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Arrays;
import java.util.List;

@Route("player")
public class PlayerView extends VerticalLayout implements HasUrlParameter<String> {

    private final PlayerClient playerClient;
    private final SquadClient squadClient;
    private Long userId;
    private Long squadId;
    private final Grid<PlayerDto> playerGrid;
    private PlayersPagedDto foundPlayers;
    private final TextField paging;
    private PlayerDto clickedPlayer;
    private SquadDto shownSquad;
    private final TextField textField;
    private final Grid<PlayerDto> squadGrid;

    private int page = 0;

    public PlayerView(PlayerClient playerClient, SquadClient squadClient) {
        this.playerClient = playerClient;
        this.squadClient = squadClient;

        VerticalLayout playerLayout = new VerticalLayout();

        Button returnButton = new Button("Return to user");
        returnButton.addClickListener(event -> UI.getCurrent().navigate(UserView.class, userId));

        playerGrid = new Grid<>(PlayerDto.class);
        playerGrid.setColumns("id", "firstname", "lastname", "age", "value", "position", "team", "points");
        playerGrid.setWidth("100%");
        playerGrid.setAllRowsVisible(true);
        initPlayersAndSquad();

        Dialog addPlayerDialog = new Dialog();
        addPlayerDialog.setHeaderTitle("Confirm to add player to squad:");

        Button addPlayer = new Button("Add player");
        addPlayer.addClickListener(event -> addPlayerToSquad(squadId));

        Button close = new Button("Exit");
        close.addClickListener(event -> addPlayerDialog.close());

        HorizontalLayout confirmButtonsLayout = new HorizontalLayout();
        confirmButtonsLayout.add(addPlayer, close);
        confirmButtonsLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        addPlayerDialog.add(confirmButtonsLayout);

        playerGrid.asSingleSelect().addValueChangeListener(event -> {
            if(!playerGrid.asSingleSelect().isEmpty()) {
                clickedPlayer = playerGrid.asSingleSelect().getValue();
                addPlayerDialog.open();
            }
        });

        HorizontalLayout pageNavigation = new HorizontalLayout();

        Button previousPage = new Button("<-");
        previousPage.addClickListener(event -> {
            if(page > 0) {
                --page;
                getPlayers(page);
            }
        });
        Button nextPage = new Button("->");
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

        playerLayout.add(returnButton, playerGrid, pageNavigation, addPlayerDialog);

        VerticalLayout squadLayout = new VerticalLayout();

        textField = new TextField();
        textField.setWidthFull();
        textField.addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER);
        textField.setReadOnly(true);

        squadGrid = new Grid<>(PlayerDto.class);
        squadGrid.setColumns("id", "firstname", "lastname", "age", "value", "position", "team", "points");
        squadGrid.setAllRowsVisible(true);

        Dialog removePlayerDialog = new Dialog();
        removePlayerDialog.setHeaderTitle("Confirm remove player:");

        Button removePlayer = new Button("Remove player");
        removePlayer.addClickListener(event -> removePlayerFromSquad(squadId));

        Button closeRemove = new Button("Exit");
        closeRemove.addClickListener(event -> removePlayerDialog.close());

        HorizontalLayout removeButtonsLayout = new HorizontalLayout();
        removeButtonsLayout.add(removePlayer, closeRemove);
        removeButtonsLayout.setJustifyContentMode(JustifyContentMode.CENTER);;
        removePlayerDialog.add(removeButtonsLayout);

        squadGrid.asSingleSelect().addValueChangeListener(event -> {
            if(!squadGrid.asSingleSelect().isEmpty()) {
                clickedPlayer = squadGrid.asSingleSelect().getValue();
                removePlayerDialog.open();
            }
        });

        squadLayout.add(textField, squadGrid, removePlayerDialog);

        add(playerLayout, squadLayout);
    }

    public void getPlayers(int page) {
        foundPlayers = playerClient.getPlayers(page);
        playerGrid.setItems(foundPlayers.getPlayer());
        refreshPaging();
    }

    public void addPlayerToSquad(Long squadId) {
        try {
            shownSquad = squadClient.addPlayer(squadId, clickedPlayer.getId());
            Notification.show("Added player " + clickedPlayer.getId() + " to squad!");
        } catch (NoBodyException | HttpClientErrorException e) {
            Notification.show(e.getMessage());
        } finally {
            refreshSquad();
        }
    }

    private void removePlayerFromSquad(Long squadId) {
        try {
            shownSquad = squadClient.removePlayer(squadId, clickedPlayer.getId());
            Notification.show("Removed player " + clickedPlayer.getId() + " from squad!");
        } catch (NoBodyException | HttpClientErrorException e) {
            Notification.show(e.getMessage());
        } finally {
            refreshSquad();
        }
    }


    @Override
    public void setParameter(BeforeEvent event,
                             @WildcardParameter String parameter) {
        List<String> parameters = Arrays.stream(parameter.split("/")).toList();
        userId = Long.parseLong(parameters.get(0));
        squadId = Long.parseLong(parameters.get(1));
        shownSquad = squadClient.getSquad(squadId);
        refreshSquad();
        textField.setValue(shownSquad.getName());
    }

    public void initPlayersAndSquad() {
        foundPlayers = playerClient.getPlayers(0);
        playerGrid.setItems(foundPlayers.getPlayer());
    }

    public void refreshPaging() {
        int currentPage = foundPlayers.getPage().getCurrentPage() + 1;
        int finalPage = foundPlayers.getPage().getFinalPage();
        paging.setValue(currentPage + "/" + finalPage);
    }

    public void refreshSquad() {
        squadGrid.setItems(shownSquad.getPlayers());
    }
}
