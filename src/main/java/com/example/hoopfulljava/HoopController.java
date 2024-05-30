package com.example.hoopfulljava;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import java.awt.event.MouseEvent;
import java.util.LinkedHashMap;

public class HoopController {

    private boolean authed = false;
    private String storedTeamID = "";
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
    private Label teamName;

    @FXML
    protected void onLoginButtonClick() {
        checkAuth(userfield.getText(), passfield.getText());
        if (authed) {
            teamManage.setDisable(false);
            storedTeamID = dbController.getTeamIDFromCap(userfield.getText() );
            LinkedHashMap<String,String> team = dbController.getTeamInfo(storedTeamID);
            onRefreshButtonClick(); // also refresh


            // maybe change the message to the team name
            msgLogin.setText("Welcome " + userfield.getText() + ", Authorized to edit " + team.get("teamName") + ".");
            teamName.setText(team.get("teamName"));
            msgLogin.setFill(Color.GREEN);
            buttonSignOut.setVisible(true);
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
    protected void onAddBtnClick() {
        dbController.addPlayer(
                playerIDField.getText(),
                teamIDField.getText(),
                playerNameField.getText()
        );
        onRefreshButtonClick(); // also refresh
    }

    @FXML
    protected void onDropBtnClick() {
        dbController.deletePlayer(playerIDField.getText());
        onRefreshButtonClick(); // also refresh
    }

    @FXML
    private Tab teamManage;
    @FXML
    private TextArea playerInfo;
    @FXML
    private Button buttonRefresh;

    @FXML
    protected void onRefreshButtonClick() {

        String[] players = dbController.getPlayerArray(storedTeamID);

        // StringBuilder to concatenate the player info
        StringBuilder playerInfoBuilder = new StringBuilder();

        // append each player's information
        for (String player : players) {
            playerInfoBuilder.append(player).append("\n");
        }

        playerInfo.setText(playerInfoBuilder.toString());
    }


    @FXML
    private Tab tournaments;
    @FXML
    private TextArea tournamentInfo;
    @FXML
    private Button buttonLoad;

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
    @FXML
    private void onSignOutButtonClick() {
        //Disable team management and hide signOutButton
        teamManage.setDisable(true);
        buttonSignOut.setVisible(false);

        //clear the ID
        storedTeamID = "";

        //clear fields
        msgLogin.setText("");
        userfield.setText("");
        passfield.setText("");
        teamName.setText("");
        playerInfo.setText("");
        storedTeamID = "";
    }

}