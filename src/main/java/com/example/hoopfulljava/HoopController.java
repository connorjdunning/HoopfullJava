package com.example.hoopfulljava;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;

public class HoopController {

    private boolean authed = false;
    private String storedTeamID = "";

    private Captain captain;

    private Team team;
    private DatabaseController dbController;

    public HoopController() {
        dbController = new DatabaseController();

//        try {
//            conn = DriverManager.getConnection(url, userName, pass);
//            System.out.println("CONNECTED");
//        } catch(Exception e) {
//            System.out.println(e.getMessage());
//        }
    }

//    // DB INFO ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//    private final String url = "jdbc:mysql://localhost:3306/hoopfuldb";
//    private final String userName ="root";
//    private final String pass = "";
//    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ DATABASE INFO
//    private Connection conn = null;

    private void checkAuth(String userName, String password) {
        authed = dbController.login(userName, password);
    }

    @FXML
    private TextField userfield;
    @FXML
    private PasswordField passfield;
    @FXML
    private Text msgLogin;
    @FXML
    private Button buttonLogin;
    @FXML
    private Text teamName;
    @FXML
    private TableView<Player> playerInfoTable;
    @FXML
    private TableColumn<Player, String> playerIdCol;
    @FXML
    private TableColumn<Player, String> nameCol;


    /**
     * When team captain logs in it creates a Captain and a Team for that
     * captain. Checks if captain is authorized to log in or not.
     */
    @FXML
    protected void onLoginButtonClick() {
        checkAuth(userfield.getText(), passfield.getText());
        if (authed) {
            teamManage.setDisable(false);
            //Create a captain
            captain = dbController.getTeamCaptain(userfield.getText());
            storedTeamID = captain.getTeamID();

            //Create the team
            team = new Team(dbController.getTeamName(storedTeamID),captain, storedTeamID);
            onRefreshButtonClick(); // also refresh

            //Welcome Message
            msgLogin.setFill(Color.GREEN);
            msgLogin.setText("Welcome " + captain.getName() + ", Authorized to edit " + team.getTeamName() + ".");
            teamName.setText(team.getTeamName());

            // Make it so each colum displays the correct values from Player
            playerIdCol.setCellValueFactory(new PropertyValueFactory<Player, String>("playerID"));
            nameCol.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));

        } else {
            msgLogin.setText("Login Failed");
            msgLogin.setFill(Color.RED);
        }
    }

    @FXML
    private TextField playerIDField;
    @FXML
    private TextField teamIDField;
    @FXML
    private TextField playerNameField;
    @FXML
    private Label warnLabel;

    /**
     * Checks if the input fields are not empty. If the input
     * fields are not empty it uses the Team addPlayer method
     * to add the player to a team and database.
     */
    @FXML
    protected void onAddBtnClick() {
        if(emptyField()) {
            warnLabel.setText("Empty field unable to add player.");
        } else {
            Player newPlayer = new Player(playerNameField.getText(), playerIDField.getText(), captain.getTeamID());
            team.addPlayer(newPlayer);
            onRefreshButtonClick(); // also refresh
        }
    }

    /**
     * Uses the Team remove to remove the player from the team
     * and database when the button is clicked.
     */
    @FXML
    protected void onDropBtnClick() {
        Player rePlayer = playerInfoTable.getSelectionModel().getSelectedItem();

        team.removePlayer(rePlayer.getPlayerID());

        onRefreshButtonClick(); // also refresh
    }

    @FXML
    private Tab teamManage;
    @FXML
    private TextArea playerInfo;
    @FXML
    private Button buttonRefresh;

    /**
     * Resets the player information table when button is clicked,
     * so it can show the most current information.
     */
    @FXML
    protected void onRefreshButtonClick() {
        playerInfoTable.getItems().clear();
        /*
          Grabbing players in the team based on the team id and
           populating that team.
         */
        team.setTeam(dbController.getPlayers(team));

        playerInfoTable.getItems().addAll(team.getTeam());
    }


    @FXML
    private Tab tournaments;
    @FXML
    private TextArea tournamentInfo;
    @FXML
    private Button buttonLoad;

    /**
     * Grabs the information of the upcoming tournaments to
     * the upcoming tournaments in the text area.
     */
    @FXML
    protected void onLoadButtonClick() {

        String[] tournaments = dbController.getTournament();

        // StringBuilder to concatenate the player info
        StringBuilder tournamentInfoBuilder = new StringBuilder();

        // append each player's information
        for (String tournament : tournaments) {
            tournamentInfoBuilder.append(tournament).append("\n");
        }

        tournamentInfo.setText(tournamentInfoBuilder.toString());
    }

    //public final void setOnLoadButtonClicked(EventHandler<? super MouseEvent> value) {
    //    onLoadButtonClick();
    //}

    @FXML
    private WebView mapWebView;
    boolean mapLoaded = false;

    /**
     * Be able to display a Google map with checking if the
     * is displayed in the map tab.
     */
    @FXML
    private void initMapView() {
        if (!mapLoaded) {
            WebEngine webEngine = mapWebView.getEngine();
            String mapUrl = "https://www.google.com/maps/d/embed?mid=1DI5SODfRVzVP7Ou8z3oFypzvHG1w7EQ&ehbc=2E312F&noprof=1";
            webEngine.load(mapUrl);
            mapLoaded = true;
        }

    }

    @FXML
    private Button buttonSignOut;

    /**
     * When the button is pressed sign ou the player
     * and clear the team captain id fields and team.
     */
    @FXML
    private void onSignOutButtonClick() {
        //Disable team management and hide signOutButton
        teamManage.setDisable(true);
        //clear the ID
        storedTeamID = "";
        //clear fields
        msgLogin.setText("");
        userfield.setText("");
        passfield.setText("");
        teamName.setText("");
        playerInfo.setText("");
        storedTeamID = "";
        //Clear team and captain
        team = null;
        captain = null;
    }

    public boolean emptyField(){
        return(playerIDField.getText().equals("") || playerNameField.getText().equals("")
                || playerIDField.getText().equals(" ") ||playerNameField.getText().equals(" "));
    }

}