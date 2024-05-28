package com.example.hoopfulljava;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.*;

public class HoopController {

    private boolean authed = false;
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
    }
}