package com.kodilla.fantasyfront.views.players;

import com.kodilla.fantasyfront.client.PlayerClient;
import com.kodilla.fantasyfront.client.SquadClient;
import com.kodilla.fantasyfront.domain.SortType;
import com.kodilla.fantasyfront.domain.dto.PlayerDto;
import com.kodilla.fantasyfront.domain.dto.PlayersPagedDto;
import com.kodilla.fantasyfront.domain.dto.SquadDto;
import com.kodilla.fantasyfront.domain.exception.NoBodyException;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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
    private PlayersPagedDto foundPlayers;
    private PlayerDto clickedPlayer;
    private final PlayersForm playersForm;
    private final UserSquadForm squadForm;
    private SquadDto shownSquad;

    private int page = 0;

    public PlayerView(PlayerClient playerClient, SquadClient squadClient) {
        this.playerClient = playerClient;
        this.squadClient = squadClient;

        playersForm = new PlayersForm(this);
        initPlayersAndSquad();
        refreshPaging();

        squadForm = new UserSquadForm(this);

        add(playersForm, squadForm);
    }

    public Long getSquadId() {
        return squadId;
    }

    public void setClickedPlayer(PlayerDto clickedPlayer) {
        this.clickedPlayer = clickedPlayer;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public Long getUserId() {
        return userId;
    }

    public PlayersPagedDto getFoundPlayers() {
        return foundPlayers;
    }

    public void fetchPlayers(int page, SortType sortType) {
        foundPlayers = playerClient.getPlayers(page, sortType);
        playersForm.refreshGrid(foundPlayers.getPlayer());
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

    public void removePlayerFromSquad(Long squadId) {
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
        squadForm.setSquadName(shownSquad.getName());
    }

    public void initPlayersAndSquad() {
        foundPlayers = playerClient.getPlayers(0, SortType.ID_ASCENDING);
        playersForm.refreshGrid(foundPlayers.getPlayer());
    }

    public void refreshPaging() {
        int currentPage = foundPlayers.getPage().getCurrentPage() + 1;
        int finalPage = foundPlayers.getPage().getFinalPage();
        playersForm.getPagingMenu().setPaging(currentPage + "/" + finalPage);
    }

    public void refreshSquad() {
        squadForm.refreshGrid(shownSquad.getPlayers());
    }
}
