package com.example.hoopfulljava;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


// EACH METHOD HOLDS BASE INPUTS FOR NOW WILL UPDATE THEM TO BOXES WHENEVER NEEDED
public class DatabaseController {


    /**
     * Establishes a connection to the database controller and
     * gives back a connection. Make sure to input your database
     * credentials.
     *
     * @return a Connection to the database.
     */
    public Connection connect() {

        String url = "jdbc:mysql://localhost:3306/hoopfulDB";
        String userName = "root";
        String pass = "Hello1234";

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

    /**
     * Grabs all the information from the Tournaments table
     * and returns the information in a string.
     *
     * @return A String array of the all the tournaments in
     *         the Tournament table
     */
    public String[] getTournament() {
        // ol' faithful
        List<String> tournaments = new ArrayList<>();

        try {
            // Connect to the database using the connect method
            Connection con = connect();

            String query = "SELECT tournamentName, amountOfTeams, location FROM hoopfuldb.Touranments";

            PreparedStatement ps = con.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String tournamentName = rs.getString("tournamentName");
                String amountOfTeams = rs.getString("amountOfTeams");
                String location = rs.getString("location");
                tournaments.add(tournamentName + "\nNumber of Teams: " + amountOfTeams + "\nLocation: " + location + "\n\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Convert the list to an array and return
        return tournaments.toArray(new String[0]);
    }

    /**
     * Get the Name from a team based on the team ID of a
     * captain.
     *
     * @param teamID - a string of the team ID for the team being
     *               searched for.
     *
     * @return a string of the name of the team.
     */
    public String getTeamName(String teamID) {

        String teamName = "";

        try {
            // Connect to the database using the connect method
            Connection con = connect();

            String query = "SELECT teamName, captainName FROM hoopfuldb.teams WHERE teamID = ?;";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, teamID);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                teamName = rs.getString("teamName");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return teamName;
    }

    /**
     * Checks if the Captain username and pass word is in the table
     * using a sql query and returns a boolean if the team captain
     * used the right credentials.
     *
     * @param user - The username of the team captain
     * @param pass - the password of the team captain
     *
     * @return A boolean of id the right credentials of the team
     *         captain were the correct inputs.
     */
    public boolean login(String user, String pass) {

        System.out.println(user + " " + pass);

        try {
            //connect to the database using the connect method
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
                //connect to the database using the connect method
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

    /**
     * Add a player to the database using a sql query that inputs
     * the necessary inputs for the player.
     *
     * @param pID - A string of the id for the player
     * @param tID - A string of the team id for that player
     * @param pName - A string of the name of the player
     */
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

    /**
     * Deletes the player of the database using a sql query that takes in the
     * id of the player.
     *
     * @param pID - A string of the player iD of the player to be removed.
     */
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
     * Get the make a captain from the table of captain using
     * the username of the captain and return that captain.
     *
     * @param userName - A string of the username the captain input
     *
     * @return A Captain object of the captain that was pulled based
     *         on the username.
     */
    public Captain getTeamCaptain(String userName) {

        Captain captain = null;

        try {
            // Connect to the database using the connect method
            Connection con = connect();

            String query = "SELECT captainName, teamID FROM hoopfuldb.captain WHERE userName = ?;";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, userName);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                captain = new Captain(rs.getString("captainName") ,rs.getString("teamID"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return captain;
    }

    /**
     * Uses a sql query and pulls the team from the table based
     * on the team ID of the team pass in through the parameter
     * and returns the team in qa LinkedList.
     *
     * @param team - A Team object of the team created used for
     *               getting the team ID.
     *
     * @return A linkedList<Player> that has the whole team pulled
     *         from the team ID.
     */
    public LinkedList<Player> getPlayers(Team team) {
        Player player = null;

        LinkedList<Player> grabTeam= new LinkedList<>();


        try {
            // Connect to the database using the connect method
            Connection con = connect();

            String query = "SELECT playerID, teamID, playerName FROM hoopfuldb.players WHERE teamID = ?;";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, team.getTeamID());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String playerID = rs.getString("playerID");
                String playerName = rs.getString("playerName");
                String teamID = rs.getString("teamID");
                // Format each player row as a string and add to the list
                player = new Player(playerName, playerID, teamID);
                grabTeam.add(player);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Convert the list to an array and return
        return grabTeam;
    }


}