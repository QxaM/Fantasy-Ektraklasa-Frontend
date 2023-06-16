package com.kodilla.fantasyfront.views.players;

import com.kodilla.fantasyfront.domain.dto.PlayersPagedDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;

public class PagingMenu extends HorizontalLayout {

    private final TextField paging;

    public PagingMenu(PlayerView playerView) {

        Button previousPage = new Button("<-");
        previousPage.addClickListener(event -> {
            int page = playerView.getPage();

            if(page > 0) {
                --page;
                playerView.fetchPlayers(page);
                playerView.setPage(page);
            }
        });

        Button nextPage = new Button("->");
        nextPage.addClickListener(event -> {
            int page = playerView.getPage();
            PlayersPagedDto foundPlayers = playerView.getFoundPlayers();

            if(page < (foundPlayers.getPage().getFinalPage() - 1)) {
                ++page;
                playerView.fetchPlayers(page);
                playerView.setPage(page);
            }
        });

        paging = new TextField();
        paging.setReadOnly(true);

        previousPage.setWidth("80px");
        paging.setWidth("80px");
        nextPage.setWidth("80px");
        paging.addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER);

        add(previousPage, paging, nextPage);
        setWidthFull();
        setJustifyContentMode(JustifyContentMode.BETWEEN);
    }

    public void setPaging(String paging) {
        this.paging.setValue(paging);
    }
}
