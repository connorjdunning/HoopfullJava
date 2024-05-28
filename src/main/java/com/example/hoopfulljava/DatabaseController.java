package com.example.hoopfulljava;

import java.sql.*;


// EACH METHOD HOLDS BASE INPUTS FOR NOW WILL UPDATE THEM TO BOXES WHENEVER NEEDED
public class DatabaseController {
    

    public Connection connect() {

        String url = "jdbc:mysql://localhost:3306/hoopfuldb";
        String userName = "root";
        String pass = "";

        try {
            //try connecting to the database
            Connection con = DriverManager.getConnection(url, userName, pass);
            //placeholder print for successful connection
            System.out.println("success!");
            return con;
        } catch (SQLException e) {
            //place holder catch
            e.printStackTrace();
        }


        return null;

    }

    public void insertPlayer(String pID, String tID, String pName) {

        try {
        //conect to the database using the connect method
        Connection con = connect();




        String add = "INSERT INTO players (playerID, teamID, playerName) VALUES (?,?,?);";
        String query = "SELECT * FROM players JOIN teams ON players.teamID = teams.teamID;";

        PreparedStatement ps = con.prepareStatement(add);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);

        if (rs.next() == true) {

            if (rs.getString("playerID").equals(pID) || rs.getString("teamID").equals(tID)) {
                return;
            }
        }
        ps.setString(1, pID);
        //NEED ERROR CHECKING FOR TEAM ID AS WILL BE FOREIGN KEY
        ps.setString(2, tID);
        ps.setString(3, pName);
        ps.execute();
        
        } catch (Exception e) {
            e.printStackTrace();
            
        }



    }

    public void createTeam(String tID, String tName, int amountPlayers, String captainID, String captainName) {

        try {
            //conect to the database using the connect method
            Connection con = connect();

    
            String add = "INSERT INTO teams (teamID, teamName, amountOfPlayers, captainID, captainName) VALUES (?,?,?,?,?);";
            
            String checkA = "SELECT * FROM teams JOIN captain ON teams.captainID = captain.captainID;";


            PreparedStatement ps = con.prepareStatement(add);


            Statement st = con.createStatement();
            ResultSet rs1 = st.executeQuery(checkA);
            
            //NEED ERROR CHECKING FOR CAPTAIN ID AS WILL BE FOREIGN KEY
            if (rs1.next() == true) {
                
            
            if (rs1.getString("teamID").equals(tID) || rs1.getString("captainID").equals(captainID)) {
                //check if teamID and captainID already exist
                System.out.println("Constraint found, cannot add team.");
                return;
            } 
        }
            ps.setString(1, tID);
            
            ps.setString(2, tName);
            ps.setInt(3, amountPlayers);
            ps.setString(4, captainID);
            ps.setString(5, captainName);
            
            ps.execute();
            
            } catch (Exception e) {
                e.printStackTrace();
                
            }
    
    
    
        }
    
        public void createCaptain(String captainID, String captainName, String teamID, String userName, String pass) {

            try {
                //conect to the database using the connect method
                Connection con = connect();
    
    
                //string used for SQL command to add 
                String add = "INSERT INTO captain (captainID, captainName, teamID, userName, pass) VALUES (?,?,?,?,?);";
                //String used to check if user name and pass already exist
                String checkB = "SELECT * FROM captain JOIN account ON captain.userName = account.userName;";

                //prepared statement for using the SQL command with user input
                PreparedStatement ps = con.prepareStatement(add);
                //regular statement and result set for checking if user name and pass already exist
                Statement st = con.createStatement();
                
                ResultSet rs2 = st.executeQuery(checkB);

                if (rs2.next() == true) {
                    
                    if (rs2.getString("captainID").equals(captainID) || !rs2.getString("userName").equals(userName) && !rs2.getString("pass").equals(pass)) {
                    //check if captain already exist
                    System.out.println("Constraint found, cannot add captain.");
                    return;
                    }
                }
                ps.setString(1, captainID);
                ps.setString(2, captainName);
                ps.setString(3, teamID);
               
                
                    ps.setString(4, userName);
                    ps.setString(5, pass);
                
                ps.execute();
            
                } catch (Exception e) {
                    e.printStackTrace();
                    
                }
            
        }

        public void createAccount(String userName, String pass) {

            try {
                //conect to the database using the connect method
                Connection con = connect();
                
                String add = "INSERT INTO account (userName, pass) VALUES (?,?);";
                String query = "SELECT * FROM account;";
                PreparedStatement ps = con.prepareStatement(add);
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(query);
                
               
            if (rs.next() == true) {
                if (rs.getString("userName").equals(userName) && rs.getString("pass").equals(pass)) {
                    //check if username and pass already exist
                    System.out.println("constraint found, cannot add account");
                    return;
                } 
            }
            
                ps.setString(1, userName);
                ps.setString(2, pass);
                
                ps.execute();
             
            
                } catch (Exception e) {
                    e.printStackTrace();
                    
                }

        }
    
        public void updatePassword(String userName, String pass) {

            try {
                //conect to the database using the connect method
                Connection con = connect();
    
                String add = "UPDATE account SET pass = ? WHERE userName = ?;";
                
                PreparedStatement ps = con.prepareStatement(add);
                
                ps.setString(1, pass);
                ps.setString(2, userName);
                
                ps.execute();
    
                } catch (Exception e) {
                    e.printStackTrace();
                    
                }

        }

        public void deleteAccount(String userName) {

            try {
                //conect to the database using the connect method
                Connection con = connect();
    
                String add = "DELETE FROM account WHERE userName = ?;";
                
                PreparedStatement ps = con.prepareStatement(add);
                
                ps.setString(1, userName);
                
                ps.execute();
    
                } catch (Exception e) {
                    e.printStackTrace();
                    
                }
        }

        public void updateCaptain(String captainID, String captainName, String teamID, String userName, String pass) {

            try {
                //conect to the database using the connect method
                Connection con = connect();
    
                String add = "UPDATE captain SET captainName = ?, teamID = ?, userName = ?, pass = ? WHERE captainID = ?;";
                
                PreparedStatement ps = con.prepareStatement(add);
                
                ps.setString(1, captainName);
                ps.setString(2, teamID);
                ps.setString(3, userName);
                ps.setString(4, pass);
                ps.setString(5, captainID);
                
                ps.execute();
    
                } catch (Exception e) {
                    e.printStackTrace();
                    
                }
        }

        public void deleteCaptain(String captainID) {

            try {
                //conect to the database using the connect method
                Connection con = connect();
    
                String add = "DELETE FROM captain WHERE captainID = ?;";
                
                PreparedStatement ps = con.prepareStatement(add);
                
                ps.setString(1, captainID);
                
                ps.execute();
    
                } catch (Exception e) {
                    e.printStackTrace();
                    
                }
        }


        public void updateTeam(String tID, String tName, int amountPlayers, String captainID, String captainName) {

            try {
                //conect to the database using the connect method
                Connection con = connect();
    
                String add = "UPDATE players SET teamName = ?, amountOfPlayers = ?, captainID = ?, captainName = ? WHERE teamID = ?;";
                
                PreparedStatement ps = con.prepareStatement(add);
                
                ps.setString(1, tName);
                ps.setInt(2, amountPlayers);
                ps.setString(3, captainID);
                ps.setString(4, captainName);
                ps.setString(5, tID);
                
                ps.execute();
    
                } catch (Exception e) {
                    e.printStackTrace();
                    
                }
        }

        public void deleteTeam(String tID) {

            try {
                //conect to the database using the connect method
                Connection con = connect();
    
                String add = "DELETE FROM players WHERE teamID = ?;";
                
                PreparedStatement ps = con.prepareStatement(add);
                
                ps.setString(1, tID);
                
                ps.execute();
    
                } catch (Exception e) {
                    e.printStackTrace();
                    
                }
        }

        public void updatePlayer(String pID, String tID, String pName) {

            try {
                //conect to the database using the connect method
                Connection con = connect();
    
                String add = "UPDATE players SET teamID = ?, playerName = ? WHERE playerID = ?;";
                
                PreparedStatement ps = con.prepareStatement(add);
                
                ps.setString(1, tID);
                ps.setString(2, pName);
                ps.setString(3, pID);
                
                ps.execute();
    
                } catch (Exception e) {
                    e.printStackTrace();
                    
                }
        }

        public void deletePlayer(String pID) {

            try {
                //conect to the database using the connect method
                Connection con = connect(); 
    
                String add = "DELETE FROM players WHERE playerID = ?;";
                
                PreparedStatement ps = con.prepareStatement(add);
                
                ps.setString(1, pID);
                
                ps.execute();
    
                } catch (Exception e) {
                    e.printStackTrace();
                    
                }
        }



}