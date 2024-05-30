package com.example.hoopfulljava;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.LinkedHashMap;

import static java.awt.Font.PLAIN;

public class HoopController {
    /**
     * Fields
     */
    private boolean authed = false;
    private String storedTeamID = "";
    private DatabaseController dbController;

    /**
     * Constructor for HoopfulController
     */
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

    /**
     * Method to authenticate the login information of the user
     * @param userName
     * @param password
     */
    private void checkAuth(String userName, String password) {
        authed = dbController.login(userName, password);
    }

    /**
     * Fields
     */
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
    private TextField playerIDField;
    @FXML
    private TextField teamIDField;
    @FXML
    private TextField playerNameField;
    @FXML
    private Tab teamManage;
    @FXML
    private TextArea playerInfo;
    @FXML
    private Button buttonRefresh;
    @FXML
    private Tab tournaments;
    @FXML
    private TextArea tournamentInfo;
    @FXML
    private Button buttonLoad;
    @FXML
    private WebView mapWebView;
    boolean mapLoaded = false;
    @FXML
    private Button buttonSignOut;
    /**
     * Login button method that controls when its clicked
     */
    @FXML
    protected void onLoginButtonClick() {
        checkAuth(userfield.getText(), passfield.getText());
        if (authed) {
            teamManage.setDisable(false);
            storedTeamID = dbController.getTeamIDFromCap(userfield.getText() );
            LinkedHashMap<String,String> team = dbController.getTeamInfo(storedTeamID);
            String storedTeamName = team.get("teamName");
            String storedCaptName = team.get("captainName");
            onRefreshButtonClick(); // also refresh

            // maybe change the message to the team name
            msgLogin.setFill(Color.GREEN);
            msgLogin.setText("Welcome " + storedCaptName + ", Authorized to edit " + storedTeamName + ".");
            teamName.setText(storedTeamName);

        } else {
            msgLogin.setText("Login Failed");
            msgLogin.setFill(Color.RED);
        }
    }


    /**
     * Method that handles the clicking of the add button
     */
    @FXML
    protected void onAddBtnClick() {
        dbController.addPlayer(
                playerIDField.getText(),
                teamIDField.getText(),
                playerNameField.getText()
        );
        onRefreshButtonClick(); // also refresh
    }

    /**
     * Method that handles the clicking of the drop button
     */
    @FXML
    protected void onDropBtnClick() {
        dbController.deletePlayer(playerIDField.getText());
        onRefreshButtonClick(); // also refresh
    }

    /**
     * Method that handles the clicking of the Refresh button
     */
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


    /**
     * Method that handles the load button
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

    /**
     * Method that handles the initialization of the map view
     *
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


    /**
     * Method that handles the signout button
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
    }

}