package com.example.hoopfulljava;

import java.util.ArrayList;
import java.util.LinkedList;

public class Team {

    private String teamName;

    private Captain captain;

    private String teamID;

    private LinkedList<Player> team;

    private DatabaseController dbController = new DatabaseController();

    public Team(String name,Captain captain, String teamID) {
        this.teamName = name;
        this.captain = captain;
        this.teamID = teamID;
        this.team = new LinkedList<>();
    }

    /**
     * Get the name of the team.
     *
     * @return - A string of the name of the team.
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * Get the Captain object of the team.
     *
     * @return - The captain object of the team
     */
    public Captain getCaptain() {
        return captain;
    }

    /**
     * Gets the ID of the team.
     * @return
     */
    public String getTeamID() {
        return teamID;
    }

    /**
     * Gets the linked list of players in the team.
     *
     * @return - A linked list of players that are on the team.
     */
    public LinkedList<Player> getTeam() {
        return team;
    }

    /**
     * Set the captain of the team.
     *
     * @param captain - A captain object of the chosen team captain
     */
    public void setCaptain(Captain captain) {
        this.captain = captain;
    }

    /**
     * Set the team ID.
     *
     * @param teamID - A string of the team id.
     */
    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }

    /**
     * Set the players in the team by passing in a linked list of
     * players.
     * @param team - A linked list of the chosen players in
     *             a team.
     */
    public void setTeam(LinkedList<Player> team) {
        this.team = team;
    }

    /**
     * Add a player to the team and database based on
     * their player id.
     *
     * @param player - the player object to be added to a team.
     */
    public void addPlayer(Player player){
        dbController.addPlayer(player.getPlayerID(), player.getTeamID(), player.getName());
        this.team.add(player);
    }

    /**
     * Remove a player from the team and database based on
     * their player id.
     *
     * @param playerID - the string id of the player being removed
     */
    public void removePlayer(String playerID){
        int count = 0; //Count the index of removed player
        int pIndex = 0;

        for(Player player : team){
            count++;
            if (player.getPlayerID().equals(playerID)){
                dbController.deletePlayer(playerID);
                pIndex = count; //Set the index of removed player
            }
        }

        team.remove(pIndex);//Remove player
    }

}
