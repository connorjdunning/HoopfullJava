package com.example.hoopfulljava;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


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
            System.out.println("CONNECTED");
            return con;
        } catch (SQLException e) {
            //place holder catch
            e.printStackTrace();
        }


        return null;

    }

    public boolean login(String user, String pass) {

        System.out.println(user + " " + pass);

        try {
            //conect to the database using the connect method
            Connection con = connect();

            String query = "SELECT pass FROM hoopfuldb.captain WHERE userName = ?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, user);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {  // Check if the ResultSet has any results
                if (pass.equals(rs.getString("pass"))) {
                    System.out.println("Login Success");
                    return true;
                } else {
                    System.out.println("Login FAILED");
                    return false;
                }
            } else {
                System.out.println("User not found");
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
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

    public void addPlayer(String pID, String tID, String pName) {

        try {
            //conect to the database using the connect method
            Connection con = connect();

            String add = "INSERT INTO hoopfuldb.players (playerID, teamID, playerName) VALUES (?, ?, ?);";

            PreparedStatement ps = con.prepareStatement(add);

            ps.setString(1, pID);
            ps.setString(2, tID);
            ps.setString(3, pName);

            System.out.println(ps.toString() );

            ps.execute();

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

        public void deletePlayer(String pID) {

            try {
                //conect to the database using the connect method
                Connection con = connect(); 
    
                String add = "DELETE FROM hoopfuldb.players WHERE playerID = ?;";
                
                PreparedStatement ps = con.prepareStatement(add);
                
                ps.setString(1, pID);
                
                ps.execute();
    
            } catch (Exception e) {
                e.printStackTrace();

            }
        }

    /**
     *
     * @param userName
     * @return The teamID of the passed captain
     */
    public String getTeamIDFromCap(String userName) {

        String teamID = null;

        try {
            // Connect to the database using the connect method
            Connection con = connect();

            String query = "SELECT teamID FROM hoopfuldb.captain WHERE userName = ?;";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, userName);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                teamID = rs.getString("TeamID");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return teamID;
    }

    /**
     *
     * @param teamID
     * @return An array representing all the players on the given team
     */
    public String[] getPlayerArray(String teamID) {
        // ol' faithful
        List<String> players = new ArrayList<>();

        try {
            // Connect to the database using the connect method
            Connection con = connect();

            String query = "SELECT playerID, teamID, playerName FROM hoopfuldb.players WHERE teamID = ?;";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, teamID);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String playerID = rs.getString("playerID");
                String playerName = rs.getString("playerName");
                // Format each player row as a string and add to the list
                players.add("playerID: " + playerID + "\tteamID: " + teamID + "\tplayerName: " + playerName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Convert the list to an array and return
        return players.toArray(new String[0]);
    }




}