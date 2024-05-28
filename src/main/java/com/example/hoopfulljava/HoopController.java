package com.example.hoopfulljava;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.sql.*;

public class HoopController {

    private boolean authed = false;
    private String storedTeamID = "";
    private DatabaseController dbController;
    public HoopController () {
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
    protected void onLoginButtonClick() {
        checkAuth(userfield.getText(), passfield.getText());
        if (authed) {
            teamManage.setDisable(false);
            storedTeamID = dbController.getTeamIDFromCap(userfield.getText() );
            onRefreshButtonClick(); // also refresh

            // maybe change the message to the team name
            msgLogin.setText("Welcome " + userfield.getText() + ", Authorized to edit Team: " + storedTeamID);
            msgLogin.setFill(Color.GREEN);
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
        dbController.deletePlayer(playerIDField.getText() );
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
}