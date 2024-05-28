package com.example.hoopfulljava;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;

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
    private Button buttonLogin;
    @FXML
    protected void onLoginButtonClick() {
        checkAuth(userfield.getText(), passfield.getText());
        if (authed) {
            teamManage.setDisable(false);
            storedTeamID = dbController.getTeamIDFromCap(userfield.getText() );
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
                playerNameField.getText());
    }
    @FXML
    protected void onDropBtnClick() {
        dbController.deletePlayer(playerIDField.getText() );
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

}