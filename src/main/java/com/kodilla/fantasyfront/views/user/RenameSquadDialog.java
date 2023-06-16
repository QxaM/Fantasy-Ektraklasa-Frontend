package com.kodilla.fantasyfront.views.user;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class RenameSquadDialog extends Dialog {

    private final TextField newName;

    public RenameSquadDialog(UserView userView) {
        setHeaderTitle("Rename squad to:");

        newName = new TextField();
        newName.setWidthFull();

        RenameSquadMenu renameSquadMenu = new RenameSquadMenu(userView, this);

        VerticalLayout dialogLayout = new VerticalLayout();
        dialogLayout.add(newName, renameSquadMenu);
        dialogLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        add(dialogLayout);
    }

    public String getNewName() {
        return newName.getValue();
    }
}
