package com.kodilla.fantasyfront.views.user;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;

public class UserForm extends VerticalLayout {

    private final IntegerField userId;
    private final TextField username;
    private final TextField email;
    private final IntegerField userPoints;

    public UserForm() {

        userId = new IntegerField("User ID");
        userId.setSizeFull();
        username = new TextField("Username");
        username.setSizeFull();
        email = new TextField("User Email");
        email.setSizeFull();
        userPoints = new IntegerField("User points");
        userPoints.setSizeFull();

        add(userId, username, email, userPoints);
    }

    public Long getUserId() {
        return userId.getValue().longValue();
    }

    public void setUserId(Long userId) {
        this.userId.setValue(userId.intValue());
    }

    public String getUsername() {
        return username.getValue();
    }

    public void setUsername(String username) {
        this.username.setValue(username);
    }

    public String getEmail() {
        return email.getValue();
    }

    public void setEmail(String email) {
        this.email.setValue(email);
    }

    public int getUserPoints() {
        return userPoints.getValue();
    }

    public void setUserPoints(int userPoints) {
        this.userPoints.setValue(userPoints);
    }
}
