# Fantasy Ekstraklasa Frontend
Welcome to Fantasy Ekstraklasa project! This project is a frontend to the application and allows for some very simple navigation and API usage.

To start navigate to http://localhost:8081

##User Creation
After entering it is necessary to create a new user - with **username** and **email**. After creating user, the page will automatically reload to a detailed user view.

![image](https://github.com/QxaM/Fantasy-Ektraklasa-Frontend/assets/109360131/f3bebad5-6d23-40e1-91c9-df3c925ce645)

## User View
### User form and controls
On the top-left side of user view there is a user details form with userId, username, email and user points (based on players in user's squad):

![image](https://github.com/QxaM/Fantasy-Ektraklasa-Frontend/assets/109360131/cd8e7248-3867-4fd6-9cca-52fed36d2ada)

All fields are editable and using button `Update` will update a user. Button `refresh` refreshes current user from database. Button `Delete` will delete current user and return to User Creation view.

### Squad list and squad controls
On the top-right side of user view there is a squad list, that lists all players currently added to the squad. Below that there are squad controls.

![image](https://github.com/QxaM/Fantasy-Ektraklasa-Frontend/assets/109360131/0771c5b7-aa03-4f54-85eb-5e92b00162b3)

Initially no squad is created (empty Squad name), so it is necessary to enter `Squad name` and create user with button `New Squad`. When the process is successful notification will appear.

![image](https://github.com/QxaM/Fantasy-Ektraklasa-Frontend/assets/109360131/7a8cb670-9cb4-4780-a879-10dc213917c2)

Button `Rename squad` allows to rename a squad. Button `Add players` changes view to Players view - that allows to add players to a squad. Squad with players added looks as below:

![image](https://github.com/QxaM/Fantasy-Ektraklasa-Frontend/assets/109360131/977edf89-f223-412f-bf7b-444fce6f417b)

### League list and controls
On the bottom of the view there is a list of all leagues, that current user is a part of.

![image](https://github.com/QxaM/Fantasy-Ektraklasa-Frontend/assets/109360131/6260a498-5c81-4791-bacd-9ea8cbf27463)

Button `All Leagues` changes view to Leagues view, that allows to enter created leagues. Button `Exit League` exits the currently set league.

![image](https://github.com/QxaM/Fantasy-Ektraklasa-Frontend/assets/109360131/d7a031d0-baeb-4b17-b9e9-7e25a390d2bb)

## Players View
Players View allows to add users to current squad. At the top, there are controls, that allows to return to previous page and change sorting type of players list. Directly below there is a Players list.

![image](https://github.com/QxaM/Fantasy-Ektraklasa-Frontend/assets/109360131/4617dfa6-92fd-4e4d-b26f-ecdea47593f9)

Clicking on any Player will open a dialog, that allows adding player to a squad.

![image](https://github.com/QxaM/Fantasy-Ektraklasa-Frontend/assets/109360131/1271b640-8826-4343-b8c8-44d6106873c6)

Since the list of players is paged by repository, directly below players list there are paging controls button. Right below there is a list showing a squad:

![image](https://github.com/QxaM/Fantasy-Ektraklasa-Frontend/assets/109360131/31e02a13-af66-4c30-8dd1-d59c07711956)

Clicking on players in the squad below players list will open a dialog, that allows players removal from squad:

![image](https://github.com/QxaM/Fantasy-Ektraklasa-Frontend/assets/109360131/29c1808f-43c3-4b40-9325-a5e50576f7d8)

## League View
League view allows to create new Leagues with button `Create new` at the top of the page. After clicking this button a dialog window will open, that allows entering a name and actually creating a league:

![image](https://github.com/QxaM/Fantasy-Ektraklasa-Frontend/assets/109360131/c0dff55b-7e40-4d94-96d6-45d1ad9c7d9c)

Pressing a league will open dialog box, that allows to:
- `Show League` - show a detailed view of a league
- `Enter League` - enter league as a current user
- `Delete League` - delete selected league
- `Exit` - exit without any action
  
![image](https://github.com/QxaM/Fantasy-Ektraklasa-Frontend/assets/109360131/0ee9154c-7143-4c78-b2ea-737db8a0352e)

## Detailed League view
Detailed league view allows to view a detailed view, with all its users and theirs score:

![image](https://github.com/QxaM/Fantasy-Ektraklasa-Frontend/assets/109360131/48178c89-0a74-4eb1-8962-002bb641ff7b)
